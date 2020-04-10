package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * The {@code InvaderGameState} class is where the state of the game is managed
 * along with the game loop and game logic.
 */
public class InvaderGameState {
    private static final int MISSILE_SPAWN_DELAY = 500; // in ms
    private static final Vector ENEMY_BRIGADE_SIZE = new Vector(6, 6);
    private static final double HERO_DEFAULT_SPEED = 0.001;
    // private static final double HERO_DEFAULT_ANGULAR_SPEED = 0.005;

    private Config config;
    private boolean debugMode;
    private boolean shouldQuit;
    private ArrayList<Critter> critters;
    private Brigade enemyBrigade;
    private Shooter hero;
    private int moveLeftKeyCode;
    private int moveRightKeyCode;
    private int shootKeyCode;
    private int quitKeyCode;
    private long lastMissileTime;
    private Vector mousePosition;

    public InvaderGameState(Config config) {
        this.config = config;
        this.debugMode = this.config.getInt("debugMode") != 0;
        this.critters = new ArrayList<Critter>();
        this.enemyBrigade = new Brigade(InvaderGameState.ENEMY_BRIGADE_SIZE);
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

    private void processInput() {
        // check if we should quit
        if (StdDraw.isKeyPressed(this.quitKeyCode)) {
            this.shouldQuit = true;
        }

        // process hero movement
        if (StdDraw.isKeyPressed(this.moveRightKeyCode)) {
            this.hero.setVelocity(InvaderGameState.HERO_DEFAULT_SPEED, 0);
        } else if (StdDraw.isKeyPressed(this.moveLeftKeyCode)) {
            this.hero.setVelocity(-InvaderGameState.HERO_DEFAULT_SPEED, 0);
        } else {
            this.hero.setVelocity(0, 0);
        }

        Vector mousePos = getMousePosition();
        Vector direction = Vector.subtract(mousePos, this.hero.getPosition());
        this.hero.getAim().setAngle(direction.getAngle());
        this.hero.getAim().clamp(0, Math.PI);

        // This has potential for a power-up where the hero can shoot
        // faster or maybe multiple missiles at a time?
        if (StdDraw.isKeyPressed(this.shootKeyCode) &&
            System.currentTimeMillis() - this.lastMissileTime >
                InvaderGameState.MISSILE_SPAWN_DELAY) {
            Missile missile = new Missile(this.hero);
            this.critters.add(missile);
            Utility.debugPrintLine(
                "Spawned missile " + missile.getIdString() + " at " +
                missile.getPosition().toString() + " in the direction " +
                Vector.normalize(missile.getVelocity()).toString());
            this.lastMissileTime = System.currentTimeMillis();
        }
    }

    private void doGameLoop() {
        int targetFps = this.config.getInt("maxFps");
        long targetFrameTime = targetFps > 0 ? 1000 / targetFps : 0;
        long timeDelta = 0;
        this.lastMissileTime = 0;
        long frameCount = 0;

        ArrayList<Critter> setForDeletion = new ArrayList<Critter>();

        while (!this.shouldQuit) {
            long frameStart = System.currentTimeMillis();

            this.processInput();

            StdDraw.clear(StdDraw.BLACK);

            this.hero.advance(timeDelta);
            this.hero.draw();

            // TODO: enemies should move down when they reach the end of the
            // screen and occationally shoot!
            this.enemyBrigade.advance(timeDelta);
            this.enemyBrigade.draw();

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
    }

    /**
     * Starts the game loop
     */
    public void start() {
        this.initGameState();
        this.doGameLoop();
    }

    private Vector getMousePosition() {
        this.mousePosition.setX(StdDraw.mouseX());
        this.mousePosition.setY(StdDraw.mouseY());

        return this.mousePosition;
    }
}
