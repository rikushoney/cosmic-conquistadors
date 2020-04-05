package cosmic_conquistadors;

/**
 * The {@code DefaultCritter} is the default implementation of the {@link
 * Critter} interface. It uses the equations of motion to calculate how the
 * {@link Critter} moves as time changes.
 */
public class DefaultCritter implements Critter {
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
        this.id = critterCount;
        critterCount++;
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
    }

    @Override
    public void setPosition(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
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
        double xPosition = this.position.getX();
        double yPosition = this.position.getY();
        double xVelocity = this.velocity.getX();
        double yVelocity = this.velocity.getY();
        double xAcceleration = this.acceleration.getX();
        double yAcceleration = this.acceleration.getY();

        this.position.setX(xPosition + xVelocity * dt +
                           0.5 * xAcceleration * Math.pow(dt, 2));
        this.position.setY(yPosition + yVelocity * dt +
                           0.5 * yAcceleration * Math.pow(dt, 2));

        this.velocity.setX(xVelocity + xAcceleration * dt);
        this.velocity.setY(yVelocity + yAcceleration * dt);
    }

    @Override
    public void draw() {}

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
