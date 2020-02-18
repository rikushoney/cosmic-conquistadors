public class Invaders {
    public static void main(String[] args) {
        Config cfg = new Config("settings.cfg");
        cfg.loadConfig();
        StdDraw.setCanvasSize(cfg.getInt("windowWidth"), cfg.getInt("windowHeight"));
        StdDraw.text(0.5, 0.5, "Hello World!");
    }
}
