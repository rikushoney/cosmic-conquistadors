/*
    Written by Rikus Honey for cosmic-conquestadors
    https://github.com/rikushoney/cosmic-conquistadors
*/

import java.io.File;
import java.text.ParseException;
import java.util.Map;
import java.util.HashMap;

public class Config {
    private String configFilename;
    private Map<String, Object> configDictionary;
    private int lineIndex;

    private enum ValueType {
        STRING, INTEGER, DOUBLE, INVALID
    }

    public Config(String filename) {
        configFilename = filename;
        configDictionary = new HashMap<String, Object>();
        lineIndex = -1;
    }

    public String getFilename() {
        return configFilename;
    }

    // Divide the line into 2 pieces; the first part containing the
    // "identifier" and the second part containing the value
    private String[] parseStatement(String statement) throws ParseException {
        String[] keyValues = statement.split("=");
        if (keyValues.length < 2) {
            throw new ParseException("Statements should have an \"=\" sign.", lineIndex);
        }
        else if (keyValues.length > 2) {
            throw new ParseException("Statements should only have a single \"=\" sing.", lineIndex);
        }

        for (int i = 0; i < keyValues.length; i++) {
            keyValues[i] = keyValues[i].strip();
        }

        return keyValues;
    }

    private ValueType guessType(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return ValueType.STRING;
        }
        else if (Character.isDigit(value.charAt(0)) && Character.isDigit(value.charAt(value.length() - 1))) {
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

            throw new ParseException("unexpected character \"" + offendingCharacter + "\"", lineIndex);
        }
    }

    private Double parseDoubleValue(String value) throws ParseException {
        try {
            return Double.valueOf(value);
        }
        catch (NumberFormatException e) {
            char offendingCharacter = 0;
            for (char c : value.toCharArray()) {
                if (!Character.isDigit(c) &&  c != '-' && c != '.') {
                    offendingCharacter = c;
                }
            }

            throw new ParseException("unexpected character \"" + offendingCharacter + "\"", lineIndex);
        }
    }

    private String parseStringValue(String value) {
        return value.replace("\"", "");
    }

    public void parseConfig(String[] lines) throws ParseException {
        for (lineIndex = 0; lineIndex < lines.length; lineIndex++) {
            String line = lines[lineIndex];

            if (shouldIgnoreLine(line)) {
                continue;
            }

            String[] statement = parseStatement(line);
            String identifier = statement[0];
            String value = statement[1];

            // Check the type of value - strings are surrounded by quotes
            // and numbers only contain digits or a single '.' for floating
            // point values
            switch(guessType(value)) {
                case STRING:
                    configDictionary.put(identifier, parseStringValue(value));
                    break;
                case INTEGER:
                    configDictionary.put(identifier, parseIntegerValue(value));
                    break;
                case DOUBLE:
                    configDictionary.put(identifier, parseDoubleValue(value));
                    break;
                default:
                    throw new ParseException("invalid value \"" + value + "\".", lineIndex);
            }
        }
    }

    public void flushConfig(String[] lines) throws ParseException {
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (shouldIgnoreLine(line)) {
                continue;
            }

            String[] statement = parseStatement(line);
            String identifier = statement[0];
            String value = statement[1];

            Object newValue = getOption(identifier);
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

    public boolean loadConfig() throws ParseException {
        In configFile = new In(configFilename);
        if (!configFile.exists()) {
            return false;
        }

        parseConfig(configFile.readAllLines());
        configFile.close();

        return true;
    }

    public boolean writeConfig() throws ParseException {
        In configFile = new In(configFilename);
        if (!configFile.exists()) {
            return false;
        }

        String[] lines = configFile.readAllLines();
        configFile.close();
        new File(configFilename).delete();

        Out configOut = new Out(configFilename);
        flushConfig(lines);

        for (String line : lines) {
            configOut.println(line);
        }

        configOut.println();
        configOut.close();

        return true;
    }

    public Object getOption(String name) {
        return configDictionary.get(name);
    }

    public void setOption(String name, Object value) {
        if (getOption(name) != null) {
            configDictionary.replace(name, value);
        }
        else {
            configDictionary.put(name, value);
        }
    }

    public String getString(String name) {
        Object value = getOption(name);
        if (value != null && value instanceof String) {
            return String.class.cast(value);
        }
        else {
            return "";
        }
    }

    public void setString(String name, String value) {
        setOption(name, value);
    }

    public int getInt(String name) {
        Object value = getOption(name);
        if (value instanceof Integer) {
            return Integer.class.cast(value).intValue();
        }
        else {
            return 0;
        }
    }

    public void setInt(String name, int value) {
        setOption(name, Integer.valueOf(value));
    }

    public double getDouble(String name) {
        Object value = getOption(name);
        if (value instanceof Double) {
            return Double.class.cast(value).doubleValue();
        }
        else {
            return 0.0;
        }
    }

    public void setDouble(String name, double value) {
        setOption(name, Double.valueOf(value));
    }
}
