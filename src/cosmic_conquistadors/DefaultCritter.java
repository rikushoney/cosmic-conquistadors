package cosmic_conquistadors;

/**
 * The {@code DefaultCritter} is the default implementation of the {@code
 * Critter} interface. It uses the equations of motion to calculate how the
 * {@code Critter} moves as time changes.
 */
public class DefaultCritter implements Critter {
    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private long id;
    private Hitbox hitbox;
    private static long critterCount;

    /**
     * Static contructor
     */
    static { critterCount = 0; }

    /**
     * Constructor
     * @param position      the initial position
     * @param velocity      the initial velocity
     * @param acceleration  the initial acceleration
     */
    public DefaultCritter(Vector position, Vector velocity,
                          Vector acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    /**
     * Constructor
     */
    public DefaultCritter() {
        this(new Vector(), new Vector(), new Vector());
        this.id = critterCount;
        critterCount++;
    }

    public Vector getPosition() { return this.position; }

    public void setPosition(Vector position) {
        this.setPosition(position.getX(), position.getY());
    }

    public void setPosition(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public Vector getVelocity() { return this.velocity; }

    public void setVelocity(Vector velocity) {
        this.setVelocity(velocity.getX(), velocity.getY());
    }

    public void setVelocity(double x, double y) {
        this.velocity.setX(x);
        this.velocity.setY(y);
    }

    public Vector getAcceleration() { return this.acceleration; }

    public void setAcceleration(Vector acceleration) {
        this.setAcceleration(acceleration.getX(), acceleration.getY());
    }

    public void setAcceleration(double x, double y) {
        this.acceleration.setX(x);
        this.acceleration.setY(y);
    }

    public void advance(double dt) {
        double xPosition = this.position.getX();
        double yPosition = this.position.getY();
        double xVelocity = this.velocity.getX();
        double yVelocity = this.velocity.getY();
        double xAcceleration = this.acceleration.getX();
        double yAcceleration = this.acceleration.getY();

        this.position.setX(xPosition + xVelocity * dt +
                           0.5 * xAcceleration * dt);
        this.position.setY(yPosition + yVelocity * dt +
                           0.5 * yAcceleration * dt);

        this.velocity.setX(xVelocity + xAcceleration * dt);
        this.velocity.setY(yVelocity + yAcceleration * dt);
    }

    public void draw() {}

    public final long getId() { return this.id; }

    public final String getIdString() {
        return "[" + String.format("0x%08X", this.id) + "]";
    }

    @Override
    public String toString() {
        return "Critter " + this.getIdString() + " at " +
            this.getPosition().toString();
    }

    public Hitbox getHitbox() { return this.hitbox; }
}
