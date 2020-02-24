package cosmic_conquistadors;

/**
 * The {@code DefaultCritter} is the default implementation of the {@code
 * Critter} interface. It uses the equations of motion to calculate how the
 * {@code Critter} moves as time changes.
 */
public class DefaultCritter implements Critter {
    private Vector position, velocity, acceleration;

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
    public DefaultCritter() { this(new Vector(), new Vector(), new Vector()); }

    public Vector getPosition() { return this.position; }

    public void setPosition(Vector position) { this.position = position; }

    public Vector getVelocity() { return this.velocity; }

    public void setVelocity(Vector velocity) { this.velocity = velocity; }

    public Vector getAcceleration() { return this.acceleration; }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
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
}
