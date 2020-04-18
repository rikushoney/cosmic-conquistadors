package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdOut;

/**
 * The {@code Invaders} class provides the entry point of the game where it
 * loads the game settings and starts the game loop. It is also responsible for
 * catching exceptions and providing the user with usefull error messages.
 */
public final class Invaders {
    private Invaders() {}

    /**
     * The "driver" that creates a new {@link InvaderGameState GameState} and
     * starts the game loop
     * @throws ConfigFileNotFoundException  if the {@link
     *                                      Config#DEFAULT_CONFIG_FILE
     *                                      DEFAULT_CONFIG_FILE} does not exist
     * @throws ConfigParseException         if it is unable to parse the {@link
     *                                      Config#DEFAULT_CONFIG_FILE
     *                                      DEFAULT_CONFIG_FILE}
     */
    public static void driver()
        throws ConfigFileNotFoundException, ConfigParseException {
        Config cfg = new Config(Config.DEFAULT_CONFIG_FILE);
        cfg.loadConfig();

        InvaderGameState gameState = new InvaderGameState(cfg);
        gameState.start();
    }

    /**
     * The entry point of the game
     * @param args  command line arguments the game was started with
     */
    public static void main(String[] args) {
        try {
            Invaders.driver();
        } catch (ConfigParseException e) {
            StdOut.println(e.getMessage());
        } catch (ConfigFileNotFoundException e) {
            Invaders.createConfigAndRestart(e.getFilename());
        } catch (Exception e) {
            StdOut.println("Unhandled exception: " + e.getMessage());
            if (Utility.isInDebugMode()) {
                e.printStackTrace();
            }
        } finally {
            Invaders.writeConfigToDisk();
            System.exit(0);
        }
    }

    private static void createConfigAndRestart(String filename) {
        StdOut.println(
            "Config file " + filename +
            " does not exist. Attempting to create and restart the game.");
        Config.createDefaultConfig(filename);
        try {
            Invaders.driver();
        } catch (Exception ex) {
            StdOut.println("Unable to create config file " + filename +
                           ". Exiting...");
            System.exit(0);
        }
    }

    private static void writeConfigToDisk() {
        try {
            Config.getGlobalConfig().writeConfig();
        } catch (ConfigParseException e) {
            StdOut.println("Unable to write config to file " + e.getFilename() +
                           ". Config file unmodified.");
        }
    }
}
