package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;

/**
 * The {@code DefaultCritter} class is the default implementation of the {@link
 * Critter} interface. It uses the equations of motion to calculate how the
 * {@link Critter} moves as time changes.
 */
public class DefaultCritter implements Critter {
    private static final double DEFAULT_HITBOX_HALFSIZE = 0.05;

    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private long id;
    private Hitbox hitbox;
    private static long critterCount;

    static { critterCount = 0; }

    /**
     * Constructor
     * @param position      the initial position
     * @param velocity      the initial velocity
     * @param acceleration  the initial acceleration
     */
    protected DefaultCritter(Vector position, Vector velocity,
                             Vector acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.hitbox = new Hitbox(
            position, new Vector(DefaultCritter.DEFAULT_HITBOX_HALFSIZE,
                                 DefaultCritter.DEFAULT_HITBOX_HALFSIZE));
        this.id = critterCount;
        DefaultCritter.critterCount++;
    }

    /**
     * Constructor
     */
    protected DefaultCritter() {
        this(new Vector(), new Vector(), new Vector());
    }

    @Override
    public Vector getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Vector position) {
        this.setPosition(position.getX(), position.getY());
        this.hitbox.setPosition(position);
    }

    @Override
    public void setPosition(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
        this.hitbox.setPosition(x, y);
    }

    @Override
    public Vector getVelocity() {
        return this.velocity;
    }

    @Override
    public void setVelocity(Vector velocity) {
        this.setVelocity(velocity.getX(), velocity.getY());
    }

    @Override
    public void setVelocity(double x, double y) {
        this.velocity.setX(x);
        this.velocity.setY(y);
    }

    @Override
    public Vector getAcceleration() {
        return this.acceleration;
    }

    @Override
    public void setAcceleration(Vector acceleration) {
        this.setAcceleration(acceleration.getX(), acceleration.getY());
    }

    @Override
    public void setAcceleration(double x, double y) {
        this.acceleration.setX(x);
        this.acceleration.setY(y);
    }

    @Override
    public void advance(double dt) {
        double sx = this.position.getX();
        double sy = this.position.getY();
        double vx = this.velocity.getX();
        double vy = this.velocity.getY();
        double ax = this.acceleration.getX();
        double ay = this.acceleration.getY();

        sx += vx * dt + 0.5 * ax * Math.pow(dt, 2);
        sy += vy * dt + 0.5 * ay * Math.pow(dt, 2);
        this.setPosition(sx, sy);

        vx += ax * dt;
        vy += ay * dt;
        this.setVelocity(vx, vy);
    }

    @Override
    public void draw() {
        if (Utility.isInDebugMode()) {
            Hitbox hitbox = this.getHitbox();

            StdDraw.setPenRadius(0.005);
            // top
            StdDraw.setPenColor(StdDraw.PINK);
            StdDraw.line(hitbox.getLeft(), hitbox.getTop(), hitbox.getRight(),
                         hitbox.getTop());
            // right
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.line(hitbox.getRight(), hitbox.getTop(), hitbox.getRight(),
                         hitbox.getBottom());
            // bottom
            StdDraw.setPenColor(StdDraw.CYAN);
            StdDraw.line(hitbox.getRight(), hitbox.getBottom(),
                         hitbox.getLeft(), hitbox.getBottom());
            // left
            StdDraw.setPenColor(StdDraw.GRAY);
            StdDraw.line(hitbox.getLeft(), hitbox.getBottom(), hitbox.getLeft(),
                         hitbox.getTop());
        }
    }

    @Override
    public final long getId() {
        return this.id;
    }

    @Override
    public final String getIdString() {
        return "[" + String.format("0x%08X", this.id) + "]";
    }

    @Override
    public String toString() {
        return "Critter " + this.getIdString() + " at " +
            this.getPosition().toString();
    }

    @Override
    public Hitbox getHitbox() {
        return this.hitbox;
    }

    @Override
    public boolean hitTest(Critter other) {
        return Critter.collides(this, other);
    }
}
