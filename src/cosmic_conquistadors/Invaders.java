package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdOut;

/**
 * The {@code Invaders} class provides the entry point of the game where it
 * loads the game settings and starts the game loop. It is also responsible for
 * catching exceptions and providing the user with usefull error messages.
 */
public final class Invaders {
    /**
     * The entry point of the game
     * @param args  command line arguments the game was started with
     */
    public static void main(String[] args) {
        try {
            Config cfg = new Config(Config.DEFAULT_CONFIG_FILE);
            cfg.loadConfig();

            InvaderGameState gameState = new InvaderGameState(cfg);
            gameState.start();
        } catch (ConfigParseException e) {
            StdOut.println(e.getMessage());
        } catch (ConfigFileNotFoundException e) {
            Config.createDefaultConfig(e.getFilename());
            main(args);
        } catch (Exception e) {
            StdOut.println("Unhandled exception: " + e.getMessage());
            if (Config.getGlobalConfig().getInt("debugMode") != 0) {
                e.printStackTrace();
            }
        } finally {
            try {
                Config.getGlobalConfig().writeConfig();
            } catch (Exception e) {
            }

            System.exit(0);
        }
    }
}
