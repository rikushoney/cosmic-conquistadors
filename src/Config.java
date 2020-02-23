import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Config} class provides a way to save user settings to the hard
 * drive and read it again when the program is restarted. It uses very simple
 * syntax and supports strings, integers and decimal numbers as well as
 * comments.
 *
 * <p>
 * An example of a config file could be:
 * <pre>{@code
 * # settings.cfg
 * myString = "This is a string value"
 * myInt = 10
 * myDouble = 3.14
 * }</pre>
 *
 * <p>
 * To read the config file:
 * <pre>{@code
 * Config myConfig = Config("settings.cfg");
 *
 * String myString = myConfig.getString("myString");
 * // myString = "This is a string value"
 *
 * int myInt = myConfig.getInt("myInt");
 * // myInt = 10
 *
 * double myDouble = myConfig.getDouble("myDouble");
 * // myDouble = 3.14
 * }</pre>
 *
 * @author Rikus Honey
 * @see <a href="https://github.com/rikushoney/cosmic-conquistadors">Cosmic
 *      Conquistadors</a>
 */
public class Config {
    private String filename;
    private Map<String, Object> dictionary;
    private int lineIndex;

    private enum ValueType { STRING, INTEGER, DOUBLE, INVALID }

    /**
     * Constructor
     * @param filename the name of the file to read/write the config from/to
     */
    public Config(String filename) {
        this.filename = filename;
        this.dictionary = new HashMap<String, Object>();
        this.lineIndex = -1;
    }

    /**
     * Get the name of the config file
     * @return the name of the config file that is being read/written from/to
     */
    public String getFilename() { return this.filename; }

    private ValueType guessType(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return ValueType.STRING;
        }
        else if (Character.isDigit(value.charAt(0)) &&
                 Character.isDigit(value.charAt(value.length() - 1))) {
            if (value.contains(".")) {
                return ValueType.DOUBLE;
            }
            else {
                return ValueType.INTEGER;
            }
        }
        else {
            return ValueType.INVALID;
        }
    }

    private boolean shouldIgnoreLine(String line) {
        return line.startsWith("#") || line.isBlank();
    }

    private Integer parseIntegerValue(String value) throws ParseException {
        try {
            return Integer.valueOf(value);
        }
        catch (NumberFormatException e) {
            char offendingCharacter = 0;
            for (char c : value.toCharArray()) {
                if (!Character.isDigit(c) && c != '-') {
                    offendingCharacter = c;
                }
            }

            throw new ParseException("unexpected character \"" +
                                         offendingCharacter + "\".",
                                     this.lineIndex);
        }
    }

    private Double parseDoubleValue(String value) throws ParseException {
        try {
            return Double.valueOf(value);
        }
        catch (NumberFormatException e) {
            char offendingCharacter = 0;
            boolean hasDot = false;
            for (char c : value.toCharArray()) {
                if (c == '.') {
                    if (hasDot) {
                        offendingCharacter = c;
                        break;
                    }
                    else {
                        hasDot = true;
                        continue;
                    }
                }

                if (!Character.isDigit(c) && c != '-') {
                    offendingCharacter = c;
                }
            }

            throw new ParseException("unexpected character \"" +
                                         offendingCharacter + "\".",
                                     this.lineIndex);
        }
    }

    private String parseStringValue(String value) {
        return value.replace("\"", "");
    }

    private String[] parseStatement(String statement) throws ParseException {
        String[] keyValues = statement.split("=");
        if (keyValues.length < 2) {
            throw new ParseException("Statements should have an \"=\" sign.",
                                     this.lineIndex);
        }
        else if (keyValues.length > 2) {
            throw new ParseException(
                "Statements should only have a single \"=\" sing.",
                this.lineIndex);
        }

        for (int i = 0; i < keyValues.length; i++) {
            keyValues[i] = keyValues[i].strip();
        }

        return keyValues;
    }

    /**
     * Parses the config given by {@code lines} and stores the values in memory
     * @param lines             an array of lines with valid syntax
     * @throws ParseException   when a line in {@code lines} has invalid syntax
     */
    public void parseConfig(String[] lines) throws ParseException {
        for (this.lineIndex = 0; this.lineIndex < lines.length;
             this.lineIndex++) {
            String line = lines[this.lineIndex];

            if (this.shouldIgnoreLine(line)) {
                continue;
            }

            String[] statement = this.parseStatement(line);
            String identifier = statement[0];
            String value = statement[1];

            switch (this.guessType(value)) {
            case STRING: {
                this.dictionary.put(identifier, this.parseStringValue(value));
                break;
            }
            case INTEGER: {
                this.dictionary.put(identifier, this.parseIntegerValue(value));
                break;
            }
            case DOUBLE: {
                this.dictionary.put(identifier, this.parseDoubleValue(value));
                break;
            }
            default:
                throw new ParseException("invalid value \"" + value + "\".",
                                         this.lineIndex);
            }
        }
    }

    /**
     * Modifies {@code lines} with updated/new values from the config in memory
     * @param lines             an array of lines with valid syntax
     * @throws ParseException   when a line in {@code lines} has invalid syntax
     */
    public void flushConfig(String[] lines) throws ParseException {
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (this.shouldIgnoreLine(line)) {
                continue;
            }

            String[] statement = this.parseStatement(line);
            String identifier = statement[0];
            String value = statement[1];

            Object newValue = this.getOption(identifier);
            if (newValue != null) {
                if (newValue instanceof String) {
                    value = "\"" + newValue.toString() + "\"";
                }
                else {
                    value = newValue.toString();
                }
            }

            line = identifier + " = " + value;
            lines[i] = line;
        }
    }

    /**
     * Load the config from the file given by {@link #getFilename()} into memory
     * @throws ParseException           when the config file contains invalid
     *                                  syntax
     * @throws FileNotFoundException    when the config file does not exist
     */
    public void loadConfig() throws ParseException, FileNotFoundException {
        In configFile = new In(this.filename);
        if (!configFile.exists()) {
            throw new FileNotFoundException("Config file \"" + this.filename +
                                            "\" not found.");
        }

        this.parseConfig(configFile.readAllLines());
        configFile.close();
    }

    /**
     * Write the config in memory to the file given by {@link #getFilename()}
     * @throws ParseException   when the config file has invalid syntax
     */
    public void writeConfig() throws ParseException {
        String[] lines = {};

        In configFile = new In(this.filename);
        if (configFile.exists()) {
            lines = configFile.readAllLines();
            new File(this.filename).delete();
        }

        Out configOut = new Out(this.filename);
        this.flushConfig(lines);

        for (String line : lines) {
            configOut.println(line);
        }

        configOut.println();
        configOut.close();
    }

    /**
     * Gets an option in the config as a generic object. Rather use one of the
     * type-specific "get" methods when possible.
     * @param name  the name of the value to get
     * @return      the value associated with {@code name} if it exists, else
     *              null
     */
    public Object getOption(String name) { return this.dictionary.get(name); }

    /**
     * Sets an option in the config as a generic object. Rather use one of the
     * type-specific "set" methods when possible.
     * @param name  the name of the value to set
     * @param value the value to set {@code name} to
     */
    public void setOption(String name, Object value) {
        if (this.getOption(name) != null) {
            this.dictionary.replace(name, value);
        }
        else {
            this.dictionary.put(name, value);
        }
    }

    /**
     * Gets an option in the config as a string
     * @param name  the name of the value to get
     * @return      the value of {@code name} if it exists and is a string
     *              value, else an empty ("") string
     */
    public String getString(String name) {
        Object value = this.getOption(name);
        if (value != null && value instanceof String) {
            return String.class.cast(value);
        }
        else {
            return "";
        }
    }

    /**
     * Sets an option in the config to a string
     * @param name  the name of the value to set
     * @param value the value to set {@code name} to
     */
    public void setString(String name, String value) {
        this.setOption(name, value);
    }

    /**
     * Gets an option in the config as an int
     * @param name  the name of the value to get
     * @return      the value of {@code name} if it exists and is an int
     *              value, else 0
     */
    public int getInt(String name) {
        Object value = this.getOption(name);
        if (value instanceof Integer) {
            return Integer.class.cast(value).intValue();
        }
        else {
            return 0;
        }
    }

    /**
     * Sets an option in the config to an int
     * @param name  the name of the value to set
     * @param value the value to set {@code name} to
     */
    public void setInt(String name, int value) {
        this.setOption(name, Integer.valueOf(value));
    }

    /**
     * Gets an option in the config as a double
     * @param name  the name of the value to get
     * @return      the value of {@code name} if it exists and is a double
     *              value, else 0
     */
    public double getDouble(String name) {
        Object value = this.getOption(name);
        if (value instanceof Double) {
            return Double.class.cast(value).doubleValue();
        }
        else {
            return 0.0;
        }
    }

    /**
     * Sets an option in the config to a double
     * @param name  the name of the value to set
     * @param value the value to set {@code name} to
     */
    public void setDouble(String name, double value) {
        this.setOption(name, Double.valueOf(value));
    }
}
