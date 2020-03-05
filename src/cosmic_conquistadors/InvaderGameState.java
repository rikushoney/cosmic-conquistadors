package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;
import java.util.ArrayList;

public class InvaderGameState {
    private Config config;
    private boolean shouldQuit;
    private ArrayList<Critter> critters;

    public InvaderGameState(Config config) {
        this.config = config;
        this.critters = new ArrayList<Critter>();
        this.critters.add(new Missile(new Vector(-0.9, -1), Math.PI / 6.0));
    }

    private void startRenderLoop() {
        int msPerFrame = (int)Math.round(1000.0 / this.config.getInt("maxFps"));
        long timeDelta = 0;

        while (!this.shouldQuit) {
            long frameStart = System.currentTimeMillis();

            StdDraw.clear(StdDraw.BLACK);

            for (Critter critter : this.critters) {
                critter.advance(timeDelta);
                critter.draw();
            }

            String frameTime = Long.toString(timeDelta) + "ms";
            String fps = Long.toString((long)(1000.0 / timeDelta)) + "fps";
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(0.8, 0.9, frameTime);
            StdDraw.text(0.8, 0.85, fps);

            StdDraw.show();

            long frameDelta = System.currentTimeMillis() - frameStart;
            StdDraw.pause((int)Math.max(msPerFrame - frameDelta, 0));

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
