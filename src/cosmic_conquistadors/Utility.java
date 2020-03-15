package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdOut;

/**
 * The {@code Utility} class provides as the name suggests utility methods which
 * can be useful anywhere in the game.
 */
public class Utility {
    private Utility() {}

    /**
     * Prints the {@code message} only if debug mode is enabled
     * @param message   the message to print to standard out
     */
    public static void debugPrintLine(String message) {
        if (Config.getGlobalConfig().getInt("debugMode") != 0) {
            StdOut.println(message);
        }
    }
}
