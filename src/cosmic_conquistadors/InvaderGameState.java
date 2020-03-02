package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;

public class InvaderGameState {
    private Config config;
    private boolean shouldQuit;

    private void startRenderLoop() {
        int msPerFrame = (int)Math.round(1000.0 / this.config.getInt("maxFps"));
        long timeNow = System.currentTimeMillis();

        while (!this.shouldQuit) {
            StdDraw.clear(StdDraw.BLACK);
            timeNow = System.currentTimeMillis();
            StdDraw.show();
            long timeDelta = System.currentTimeMillis() - timeNow;
            StdDraw.pause((int)Math.max(msPerFrame - timeDelta, 0));
        }
    }

    private void initGameState() {
        this.shouldQuit = false;

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(config.getInt("windowWidth"),
                              config.getInt("windowHeight"));
    }

    public InvaderGameState(Config config) { this.config = config; }

    public void start() {
        this.initGameState();
        this.startRenderLoop();
    }
}
