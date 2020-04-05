package cosmic_conquistadors;

import java.io.FileNotFoundException;

/**
 * The {@code ConfigFileNotFoundException} is signaled when there was an attempt
 * to read/write from/to a {@link Config} file, but failed because the file
 * does not exist.
 */
@SuppressWarnings("serial")
public class ConfigFileNotFoundException extends FileNotFoundException {
    private String filename;

    /**
     * Constructor
     * @param filename  the name of the {@link Config} file that does not
     *                  exist
     */
    public ConfigFileNotFoundException(String filename) {
        super("Config file \"" + filename + "\" not found.");
        this.filename = filename;
    }

    /**
     * Gets the filename of the {@link Config} file that does not exist
     * @return  the {@link Config} filename
     */
    public String getFilename() { return this.filename; }
}
