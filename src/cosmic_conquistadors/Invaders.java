package cosmic_conquistadors;

import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdOut;

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
        String CONFIG_FILENAME = "settings.cfg";

        try {
            Config cfg = new Config(CONFIG_FILENAME);
            cfg.loadConfig();

            InvaderGameState gameState = new InvaderGameState(cfg);
            gameState.start();

            cfg.writeConfig();
        }
        catch (ConfigParseException e) {
            StdOut.println(e.getMessage());
        }
        catch (ConfigFileNotFoundException e) {
            String[] DEFAULT_CONFIG = {
                "###################################",
                "# Settings for Cosmic Conquestadors",
                "###################################",
                "",
                "# Display",
                "windowWidth = 1024",
                "windowHeight = 1024",
                "maxFps = 60",
                "",
                "# Development",
                "debugMode = 1",
                "",
                "# Player",
                "playerName = \"Player 1\"",
            };

            Out configOut = new Out(e.getFilename());

            for (String line : DEFAULT_CONFIG) {
                configOut.println(line);
            }

            configOut.close();
            main(args);
        }
        catch (Exception e) {
            StdOut.println("Unhandled exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
