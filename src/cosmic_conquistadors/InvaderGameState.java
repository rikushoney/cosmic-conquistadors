package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class InvaderGameState {
    private final int MISSILE_SPAWN_DELAY = 500;

    private Config config;
    private boolean debugMode;
    private boolean shouldQuit;
    private ArrayList<Critter> critters;
    private ArrayList<Enemy> enemies;
    private Shooter hero;
    private int moveLeftKeyCode;
    private int moveRightKeyCode;
    private int shootKeyCode;
    private int quitKeyCode;

    public InvaderGameState(Config config) {
        this.config = config;
        this.debugMode = this.config.getInt("debugMode") != 0;
        this.critters = new ArrayList<Critter>();
        this.enemies = new ArrayList<Enemy>();
        this.hero = new Shooter();

        this.moveLeftKeyCode = KeyEvent.getExtendedKeyCodeForChar(
            this.config.getString("moveLeft").charAt(0));
        this.moveRightKeyCode = KeyEvent.getExtendedKeyCodeForChar(
            this.config.getString("moveRight").charAt(0));
        this.shootKeyCode = KeyEvent.getExtendedKeyCodeForChar(
            this.config.getString("shootKey").charAt(0));
        this.quitKeyCode = KeyEvent.getExtendedKeyCodeForChar(
            this.config.getString("quitKey").charAt(0));
    }

    private void startGameLoop() {
        int targetFps = this.config.getInt("maxFps");
        long targetFrameTime = targetFps > 0 ? 1000 / targetFps : 0;
        long timeDelta = 0;
        long lastMissileTime = 0;
        long frameCount = 0;

        ArrayList<Critter> setForDeletion = new ArrayList<Critter>();

        while (!this.shouldQuit) {
            long frameStart = System.currentTimeMillis();

            StdDraw.clear(StdDraw.BLACK);

            // process hero

            // TODO: check that our hero does not go out of bounds!
            if (StdDraw.isKeyPressed(this.moveRightKeyCode)) {
                this.hero.setVelocity(0.001, 0);
            } else if (StdDraw.isKeyPressed(this.moveLeftKeyCode)) {
                this.hero.setVelocity(-0.001, 0);
            } else {
                this.hero.setVelocity(0, 0);
            }

            // This has potential for a power-up where the hero can shoot
            // faster or maybe multiple missiles at a time?
            if (StdDraw.isKeyPressed(this.shootKeyCode) &&
                System.currentTimeMillis() - lastMissileTime >
                    this.MISSILE_SPAWN_DELAY) {
                Vector heroPosition = this.hero.getPosition();
                Missile missile = new Missile(Math.PI / 2);
                missile.setPosition(heroPosition.getX(), heroPosition.getY());
                this.critters.add(missile);
                Utility.debugPrintLine("Spawned missile " +
                                       missile.getIdString());
                lastMissileTime = System.currentTimeMillis();
            }

            this.hero.advance(timeDelta);
            this.hero.draw();

            // process enemies

            // TODO: enemies should move down when they reach the end of the
            // screen and occationally shoot! maybe create a "Brigade" class
            // that manages all the enemies?
            for (Enemy enemy : this.enemies) {
                enemy.advance(timeDelta);
                enemy.draw();
            }

            // process all other critters
            for (Critter critter : this.critters) {
                critter.advance(timeDelta);
                critter.draw();

                // set critters out of bounds for deletion
                Vector position = critter.getPosition();
                if (position.getX() < -1 || position.getX() > 1 ||
                    position.getY() < -1 || position.getY() > 1) {
                    setForDeletion.add(critter);
                }
            }

            // print some debug info
            if (this.debugMode) {
                String frameTime = Long.toString(timeDelta) + "ms";
                String fps = Long.toString((long)(1000.0 / timeDelta)) + "fps";
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(0.8, 0.9, frameTime);
                StdDraw.text(0.8, 0.85, fps);
                StdDraw.text(0.8, 0.8, Long.toString(frameCount));
            }

            StdDraw.show();

            // remove all the "dirty" critters
            for (Critter dirty : setForDeletion) {
                this.critters.remove(dirty);
                Utility.debugPrintLine("Deleted dirty critter " +
                                       dirty.getIdString());
            }

            setForDeletion.clear();

            // calculate the time it took for us to render the frame then sleep
            // for the time we have left until we have to render again
            long frameDelta = System.currentTimeMillis() - frameStart;
            StdDraw.pause((int)Math.max(targetFrameTime - frameDelta, 0));

            // calculate the time elapsed so that we can advance our physics
            timeDelta = System.currentTimeMillis() - frameStart;
            frameCount++;

            // check if we should quit
            if (StdDraw.isKeyPressed(this.quitKeyCode)) {
                this.shouldQuit = true;
            }
        }
    }

    private void initGameState() {
        this.shouldQuit = false;

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(config.getInt("windowWidth"),
                              config.getInt("windowHeight"));
        StdDraw.setScale(-1, 1);

        this.hero.setPosition(0, -0.9);
        Utility.debugPrintLine("Spawned hero " + this.hero.getIdString() +
                               " at " + this.hero.getPosition().toString());

        // spawn the brigade of enemies
        double y = 0.8;
        for (int i = 0; i < 6; i++) {
            double x = -0.8;
            for (int j = 0; j < 6; j++) {
                Enemy enemy = new Enemy();
                enemy.setPosition(x, y);
                this.enemies.add(enemy);
                Utility.debugPrintLine("Spawned enemy " + enemy.getIdString() +
                                       " at " + enemy.getPosition().toString());
                x += Enemy.SIDE_LENGTH + 0.1;
            }
            y -= Enemy.SIDE_LENGTH + 0.1;
        }
    }

    public void start() {
        this.initGameState();
        this.startGameLoop();
    }
}
