package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;
import java.util.ArrayList;

public class InvaderGameState {
    private Config config;
    private boolean debugMode;
    private boolean shouldQuit;
    private ArrayList<Critter> critters;

    public InvaderGameState(Config config) {
        this.config = config;
        this.debugMode = this.config.getInt("debugMode") != 0;
        this.critters = new ArrayList<Critter>();
    }

    private void startRenderLoop() {
        long targetFrameTime =
            Math.round(1000.0 / this.config.getInt("maxFps"));
        long timeDelta = 0;

        while (!this.shouldQuit) {
            long frameStart = System.currentTimeMillis();

            StdDraw.clear(StdDraw.BLACK);

            for (Critter critter : this.critters) {
                critter.advance(timeDelta);
                critter.draw();
            }

            if (this.debugMode) {
                String frameTime = Long.toString(timeDelta) + "ms";
                String fps = Long.toString((long)(1000.0 / timeDelta)) + "fps";
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(0.8, 0.9, frameTime);
                StdDraw.text(0.8, 0.85, fps);
            }

            StdDraw.show();

            // calculate the time it took for us to render the frame then sleep
            // for the time we have left until we have to render again
            long frameDelta = System.currentTimeMillis() - frameStart;
            StdDraw.pause((int)Math.max(targetFrameTime - frameDelta, 0));

            // calculate the time elapsed so that we can advance our physics
            timeDelta = System.currentTimeMillis() - frameStart;
        }
    }

    private void initGameState() {
        this.shouldQuit = false;

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(config.getInt("windowWidth"),
                              config.getInt("windowHeight"));
        StdDraw.setScale(-1, 1);
    }

    public void start() {
        this.initGameState();
        this.startRenderLoop();
    }
}
