package cosmic_conquistadors;

import java.io.FileNotFoundException;

@SuppressWarnings("serial")
public class ConfigFileNotFoundException extends FileNotFoundException {
    private String filename;

    public ConfigFileNotFoundException(String filename) {
        super("Config file \"" + filename + "\" not found.");
        this.filename = filename;
    }

    public String getFilename() { return this.filename; }
}
