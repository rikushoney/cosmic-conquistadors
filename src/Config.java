/*
    Written by Rikus Honey for cosmic-conquistadors
    https://github.com/rikushoney/cosmic-conquistadors
*/

import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private String filename;
    private Map<String, Object> dictionary;
    private int lineIndex;

    private enum ValueType { STRING, INTEGER, DOUBLE, INVALID }

    public Config(String filename) {
        this.filename = filename;
        this.dictionary = new HashMap<String, Object>();
        this.lineIndex = -1;
    }

    public String getFilename() {
        return this.filename;
    }

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

            throw new ParseException("unexpected character \"" + offendingCharacter + "\".",
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

            throw new ParseException("unexpected character \"" + offendingCharacter + "\".",
                                     this.lineIndex);
        }
    }

    private String parseStringValue(String value) {
        return value.replace("\"", "");
    }

    private String[] parseStatement(String statement) throws ParseException {
        String[] keyValues = statement.split("=");
        if (keyValues.length < 2) {
            throw new ParseException("Statements should have an \"=\" sign.", this.lineIndex);
        }
        else if (keyValues.length > 2) {
            throw new ParseException("Statements should only have a single \"=\" sing.",
                                     this.lineIndex);
        }

        for (int i = 0; i < keyValues.length; i++) {
            keyValues[i] = keyValues[i].strip();
        }

        return keyValues;
    }

    public void parseConfig(String[] lines) throws ParseException {
        for (this.lineIndex = 0; this.lineIndex < lines.length; this.lineIndex++) {
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
                throw new ParseException("invalid value \"" + value + "\".", this.lineIndex);
            }
        }
    }

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

    public boolean loadConfig() throws ParseException {
        In configFile = new In(this.filename);
        if (!configFile.exists()) {
            return false;
        }

        this.parseConfig(configFile.readAllLines());
        configFile.close();

        return true;
    }

    public boolean writeConfig() throws ParseException {
        In configFile = new In(this.filename);
        if (!configFile.exists()) {
            return false;
        }

        String[] lines = configFile.readAllLines();
        configFile.close();
        new File(this.filename).delete();

        Out configOut = new Out(this.filename);
        this.flushConfig(lines);

        for (String line : lines) {
            configOut.println(line);
        }

        configOut.println();
        configOut.close();

        return true;
    }

    public Object getOption(String name) {
        return this.dictionary.get(name);
    }

    public void setOption(String name, Object value) {
        if (this.getOption(name) != null) {
            this.dictionary.replace(name, value);
        }
        else {
            this.dictionary.put(name, value);
        }
    }

    public String getString(String name) {
        Object value = this.getOption(name);
        if (value != null && value instanceof String) {
            return String.class.cast(value);
        }
        else {
            return "";
        }
    }

    public void setString(String name, String value) {
        this.setOption(name, value);
    }

    public int getInt(String name) {
        Object value = this.getOption(name);
        if (value instanceof Integer) {
            return Integer.class.cast(value).intValue();
        }
        else {
            return 0;
        }
    }

    public void setInt(String name, int value) {
        this.setOption(name, Integer.valueOf(value));
    }

    public double getDouble(String name) {
        Object value = this.getOption(name);
        if (value instanceof Double) {
            return Double.class.cast(value).doubleValue();
        }
        else {
            return 0.0;
        }
    }

    public void setDouble(String name, double value) {
        this.setOption(name, Double.valueOf(value));
    }
}
