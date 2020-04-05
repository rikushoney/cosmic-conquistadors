package cosmic_conquistadors;

/**
 * The {@code Angle} class represents an angle measured from the horizontal. It
 * also has angular velocity and angular acceleration which causes it to rotate.
 */
public class Angle {
    private double angle;
    private double velocity;
    private double acceleration;

    /**
     * Constructor
     * @param angle         the initial angle
     * @param velocity      the angular velocity
     * @param acceleration  the angular acceleration
     */
    public Angle(double angle, double velocity, double acceleration) {
        this.angle = angle;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    /**
     * Constructor
     */
    public Angle() { this(0, 0, 0); }

    /**
     * Sets the angle
     * @param angle the new angle
     */
    public void setAngle(double angle) { this.angle = angle; }

    /**
     * Gets the angle
     * @return  the current angle
     */
    public double getAngle() { return this.angle; }

    /**
     * Sets the angular velocity
     * @param velocity  the new angular velocity
     */
    public void setVelocity(double velocity) { this.velocity = velocity; }

    /**
     * Gets the angular velocity
     * @return  the current angular velocity
     */
    public double getVelocity() { return this.velocity; }

    /**
     * Sets the angular acceleration
     * @param acceleration  the new angular acceleration
     */
    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Gets the angular acceleration
     * @return  the current angular acceleration
     */
    public double getAcceleration() { return this.acceleration; }

    /**
     * Advances the {@code Angle} according to the equations of motion
     * @param dt    the change in time (delta time)
     */
    public void advance(double dt) {
        this.setAngle(this.getAngle() + this.getVelocity() * dt +
                      0.5 * this.getAcceleration() * Math.pow(dt, 2));
        this.setVelocity(this.getVelocity() + this.getAcceleration() * dt);
    }

    /**
     * Clamps the {@code Angle} to the bounds {@code upper} and {@code lower}
     * @param lower the lower bound
     * @param upper the upper bound
     */
    public void clamp(double lower, double upper) {
        this.angle = Utility.clamp(this.angle, lower, upper);
    }

    @Override
    public String toString() {
        return this.angle == 0 ? "0"
                               : Double.toString(this.angle / Math.PI) + "pi";
    }
}
