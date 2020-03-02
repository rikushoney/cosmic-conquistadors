package cosmic_conquistadors;

import java.text.ParseException;

@SuppressWarnings("serial")
public class ConfigParseException extends ParseException {
    private String filename;

    public ConfigParseException(String message, String filename,
                                int errorOffset) {
        super("Error occured while parsing " + filename + " at line " +
                  errorOffset + message,
              errorOffset);
        this.filename = filename;
    }

    public String getFilename() { return this.filename; }
}
