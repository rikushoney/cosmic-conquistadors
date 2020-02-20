/*
    Written by Rikus Honey for cosmic-conquistadors
    https://github.com/rikushoney/cosmic-conquistadors
*/

import java.text.ParseException;

public class Invaders {
    public static void main(String[] args) {
        String configFilename = "settings.cfg";
        // StdDraw.setScale(-1, 1);

        try {
            Config cfg = new Config(configFilename);
            cfg.loadConfig();
            cfg.writeConfig();
        }
        catch (ParseException e) {
            StdOut.println("Error occured while parsing " + configFilename + " at line " +
                           (e.getErrorOffset() + 1) + ": " + e.getMessage());
        }
        catch (Exception e) {
            StdOut.println("Unhandled exception: " + e.getMessage());
        }
    }
}
