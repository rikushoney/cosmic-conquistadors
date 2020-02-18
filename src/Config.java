import java.util.Map;
import java.util.HashMap;

public class Config {
    private String configFilename;
    private Map<String, Object> configDictionary;

    public Config(String filename) {
        configFilename = filename;
        configDictionary = new HashMap<String, Object>();
    }

    public String getFilename() {
        return configFilename;
    }

    private void printError(int lineNum, String message) {
        StdOut.println("Error at line " + lineNum + ": " + message);
    }

    public boolean parseConfig(String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            // Skip comments and empty lines
            if (line.startsWith("#") || line.isBlank()) {
                continue;
            }

            // Divide the line into 2 pieces; the first part containing the
            // "identifier" and the second part containing the value
            String[] keyValues = line.split("=");
            if (keyValues.length != 2) {
                printError(i + 1, "statements can only have a single \"=\" sign.");
                return false;
            }

            String identifier = keyValues[0].strip();
            String value = keyValues[1].strip();

            // Check the type of value - strings are surrounded by quotes and
            // numbers only contain digits or a '.' for floating point values
            if (value.startsWith("\"") && value.endsWith("\"")) {
                // Remove the quotes from the string before we store it
                String stringValue = value.replace("\"", "");
                configDictionary.put(identifier, stringValue);
            }
            else if (Character.isDigit(value.charAt(0)) && Character.isDigit(value.charAt(value.length() - 1))) {
                // Build the number and parse it
                String digits = "";
                for (int j = 0; j < value.length(); j++) {
                    char c = value.charAt(j);
                    if (Character.isDigit(c) || c == '.') {
                        digits += c;
                    }
                    else {
                        printError(i + 1, "unexpected character \"" + c + "\" found while parsing number.");
                        return false;
                    }
                }

                if (digits.contains(".")) {
                    configDictionary.put(identifier, Double.valueOf(digits));
                }
                else {
                    configDictionary.put(identifier, Integer.valueOf(digits));
                }
            }
            else {
                printError(i + 1, "invalid value \"" + value + "\".");
                return false;
            }
        }

        return true;
    }

    public boolean loadConfig() {
        In configFile = new In(configFilename);
        return configFile.exists() && parseConfig(configFile.readAllLines());
    }

    public boolean writeConfig() {
        // TODO: search for values changed in memory and write to config file
        return true;
    }

    public Object getOption(String name) {
        return configDictionary.get(name);
    }

    public String getString(String name) {
        Object value = getOption(name);
        if (value instanceof String) {
            return String.class.cast(value);
        }
        else {
            return "";
        }
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

    public double getDouble(String name) {
        Object value = getOption(name);
        if (value instanceof Double) {
            return Double.class.cast(value).doubleValue();
        }
        else {
            return 0.0;
        }
    }
}
