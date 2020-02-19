import java.text.ParseException;

public class Invaders {
    public static void main(String[] args) {
        try {
            Config cfg = new Config("settings.cfg");
            cfg.loadConfig();
            cfg.writeConfig();
        }
        catch (ParseException e) {
            StdOut.println("Error occured while parsing at line " + (e.getErrorOffset() + 1) + ": " + e.getMessage());
        }
        catch (Exception e) {
            StdOut.println("Unhandled exception: " + e.getMessage());
        }
    }
}
