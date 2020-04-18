package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * The {@code InvaderGameState} class is where the state of the game is managed
 * along with the game loop and game logic.
 */
public class InvaderGameState {
    private static final int MISSILE_DELAY = 500; // in ms
    private static final Vector ENEMY_BRIGADE_SIZE = new Vector(6, 6);
    private static final double HERO_DEFAULT_SPEED = 0.001;
    // private static final double HERO_DEFAULT_ANGULAR_SPEED = 0.005;

    private ArrayList<Critter> critters;
    private ArrayList<Critter> setForDeletion;
    private Brigade enemies;
    private boolean debugMode;
    private boolean shouldQuit;
    private Config config;
    private int moveLeftKeyCode;
    private int moveRightKeyCode;
    private int shootKeyCode;
    private int quitKeyCode;
    private long lastMissileTime;
    private long frameCount;
    private long targetFrameTime;
    private Shooter hero;
    private Vector mousePosition;

    /**
     * Constructor
     * @param config    the {@link Config config} to use for the gamestate
     */
    public InvaderGameState(Config config) {
        this.config = config;
        this.debugMode = this.config.getInt("debugMode") != 0;
        this.critters = new ArrayList<Critter>();
        this.setForDeletion = new ArrayList<Critter>();
        this.enemies = new Brigade(InvaderGameState.ENEMY_BRIGADE_SIZE);
        this.hero = new Shooter();
        this.mousePosition = new Vector();

        this.moveLeftKeyCode =
            Utility.getKeyCode(this.config.getString("moveLeft"));
        Utility.debugPrintLine("Move left: <" +
                               KeyEvent.getKeyText(this.moveLeftKeyCode) + ">");
        this.moveRightKeyCode =
            Utility.getKeyCode(this.config.getString("moveRight"));
        Utility.debugPrintLine(
            "Move right: <" + KeyEvent.getKeyText(this.moveRightKeyCode) + ">");
        this.shootKeyCode =
            Utility.getKeyCode(this.config.getString("shootKey"));
        Utility.debugPrintLine("Shoot: <" +
                               KeyEvent.getKeyText(this.shootKeyCode) + ">");
        this.quitKeyCode = Utility.getKeyCode(this.config.getString("quitKey"));
        Utility.debugPrintLine("Quit: <" +
                               KeyEvent.getKeyText(this.quitKeyCode) + ">");
    }

    /**
     * Starts the game loop
     */
    public void start() {
        this.initGameState();
        this.doGameLoop();
    }

    private void initGameState() {
        this.shouldQuit = false;

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(config.getInt("windowWidth"),
                              config.getInt("windowHeight"));
        StdDraw.setScale(-1.1, 1.1);

        this.hero.setPosition(0, -1);
        Utility.debugPrintLine("Spawned hero " + this.hero.getIdString() +
                               " at " + this.hero.getPosition().toString());
    }

    private void doGameLoop() {
        int targetFps = this.config.getInt("maxFps");
        this.targetFrameTime = targetFps > 0 ? 1000 / targetFps : 0;
        this.lastMissileTime = 0;
        this.frameCount = 0;
        long timeDelta = 0;
        long frameStart = 0;

        while (!this.shouldQuit) {
            frameStart = this.startFrame();

            this.processInput();

            this.hero.advance(timeDelta);
            this.hero.draw();

            this.enemies.advance(timeDelta);
            this.enemies.draw();

            this.processCritters(timeDelta);

            this.drawDebugInfo(timeDelta);

            timeDelta = this.endFrame(frameStart);

            this.cleanCritters();
        }
    }

    private void processInput() {
        if (StdDraw.isKeyPressed(this.quitKeyCode)) {
            this.shouldQuit = true;
            // NOTE: maybe we should return here since we want to quit anyway
            // return;
        }

        if (StdDraw.isKeyPressed(this.moveRightKeyCode)) {
            this.hero.setVelocity(InvaderGameState.HERO_DEFAULT_SPEED, 0);
        } else if (StdDraw.isKeyPressed(this.moveLeftKeyCode)) {
            this.hero.setVelocity(-InvaderGameState.HERO_DEFAULT_SPEED, 0);
        } else {
            this.hero.setVelocity(0, 0);
        }

        Vector mousePos = this.getMousePosition();
        Vector direction = Vector.subtract(mousePos, this.hero.getPosition());
        this.hero.setAim(Utility.clamp(direction.getAngle(), 0, Math.PI));

        boolean canFireMissile =
            System.currentTimeMillis() - this.lastMissileTime >
            InvaderGameState.MISSILE_DELAY;

        // NOTE: This has potential for a power-up where the hero can shoot
        // faster or maybe multiple missiles at a time?
        if (StdDraw.isKeyPressed(this.shootKeyCode) && canFireMissile) {
            Missile missile = new Missile(this.hero);
            this.critters.add(missile);
            Utility.debugPrintLine(
                "Spawned missile " + missile.getIdString() + " at " +
                missile.getPosition().toString() + " in the direction " +
                Vector.normalize(missile.getVelocity()).toString());
            this.lastMissileTime = System.currentTimeMillis();
        }
    }

    private void processMissile(Missile missile) {
        Critter source = missile.getSource();
        if (source instanceof Shooter) {
            if (this.enemies.hitTest(missile)) {
                Enemy enemy = this.enemies.popInjured();
                Utility.debugPrintLine(
                    "Enemy " + enemy.getIdString() + " hit by shooter " +
                    this.hero.getIdString() + " with missile " +
                    missile.getIdString());
                // NOTE: we should do something with the bounty for score!
                deleteCritter(missile);
            }
        } else if (source instanceof Enemy) {
            if (this.hero.hitTest(missile)) {
                Utility.debugPrintLine("Hero " + this.hero.getIdString() +
                                       " hit by enemy " + source.getIdString() +
                                       " with missile " +
                                       missile.getIdString());
                // NOTE: we should keep track of our hero's lives and subtract
                // here
                deleteCritter(missile);
            }
        } else {
            throw new IllegalArgumentException("Invalid source for missile");
        }
    }

    private void processCritters(double timeDelta) {
        for (Critter critter : this.critters) {
            critter.advance(timeDelta);
            critter.draw();

            // set critters out of bounds for deletion
            Vector position = critter.getPosition();
            if (position.getX() < -1.1 || position.getX() > 1.1 ||
                position.getY() < -1.1 || position.getY() > 1.1) {
                this.deleteCritter(critter);
            }

            if (critter instanceof Missile) {
                this.processMissile((Missile)critter);
            }
        }
    }

    private long startFrame() {
        StdDraw.clear(StdDraw.BLACK);
        return System.currentTimeMillis();
    }

    private long endFrame(long frameStart) {
        StdDraw.show();

        // calculate the time it took for us to render the frame then sleep
        // for the time we have left until we have to render again
        long frameDelta = System.currentTimeMillis() - frameStart;
        StdDraw.pause((int)Math.max(this.targetFrameTime - frameDelta, 0));

        this.frameCount++;

        // return the time delta
        return System.currentTimeMillis() - frameStart;
    }

    private void drawDebugInfo(long timeDelta) {
        if (this.debugMode) {
            String frameTime = Long.toString(timeDelta) + "ms";
            String fps = Long.toString((long)(1000.0 / timeDelta)) + "fps";
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(0.8, 0.9, frameTime);
            StdDraw.text(0.8, 0.85, fps);
            StdDraw.text(0.8, 0.8, Long.toString(frameCount));
        }
    }

    private Vector getMousePosition() {
        this.mousePosition.setX(StdDraw.mouseX());
        this.mousePosition.setY(StdDraw.mouseY());

        return this.mousePosition;
    }

    private void deleteCritter(Critter critter) {
        this.setForDeletion.add(critter);
    }

    private void cleanCritters() {
        for (Critter dirty : this.setForDeletion) {
            this.critters.remove(dirty);
            Utility.debugPrintLine("Deleted dirty critter " +
                                   dirty.getIdString());
        }

        this.setForDeletion.clear();
    }
}
