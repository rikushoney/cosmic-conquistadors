import java.text.ParseException;

/**
 * The {@code Invaders} class provides the entry point of the game where it
 * loads the game settings and starts the game loop. It is also responsible for
 * catching exceptions and providing the user with usefull error messages.
 */
public class Invaders {
    /**
     * The entry point of the game
     * @param args  command line arguments the game was started with
     */
    public static void main(String[] args) {
        String configFilename = "settings.cfg";

        try {
            Config cfg = new Config(configFilename);
            cfg.loadConfig();
            cfg.writeConfig();
        }
        catch (ParseException e) {
            StdOut.println("Error occured while parsing " + configFilename +
                           " at line " + (e.getErrorOffset() + 1) + ": " +
                           e.getMessage());
        }
        catch (Exception e) {
            StdOut.println("Unhandled exception: " + e.getMessage());
        }
    }
}
