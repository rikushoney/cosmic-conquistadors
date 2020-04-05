package cosmic_conquistadors;

import java.text.ParseException;

/**
 * The {@code ConfigParseException} is signaled when the parsing of a {@link
 * Config} file failed because its syntax is ill-formed.
 */
@SuppressWarnings("serial")
public class ConfigParseException extends ParseException {
    private String filename;

    /**
     * Constructor
     * @param message       a short description of why parsing failed
     * @param filename      the filename of the {@link Config} file that was not
     *                      able to be parsed
     * @param errorOffset   the line number on which parsing failed
     */
    public ConfigParseException(String message, String filename,
                                int errorOffset) {
        super("Error occured while parsing " + filename + " at line " +
                  errorOffset + message,
              errorOffset);
        this.filename = filename;
    }

    /**
     * Gets the filename of the {@link Config} file with invalid syntax
     * @return  the filename of the {@link Config} file
     */
    public String getFilename() { return this.filename; }
}
