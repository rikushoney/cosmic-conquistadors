package cosmic_conquistadors;

/**
 * The {@code Critter} interface defines what methods on-screen objects should
 * have. All on-screen objects should have a position, velocity and
 * acceleration. These are used to calculate how the object moves.
 */
public interface Critter {
    /**
     * Gets the position of the {@code Critter}
     * @return  the current position of the {@code Critter}
     */
    public Vector getPosition();

    /**
     * Sets the position of the of the {@code Critter}
     * @param position  the new position of the {@code Critter}
     */
    public void setPosition(Vector position);

    public void setPosition(double x, double y);

    /**
     * Gets the velocity of the {@code Critter}
     * @return  the current velocity of the {@code Critter}
     */
    public Vector getVelocity();

    /**
     * Sets the velocity of the {@code Critter}
     * @param velocity  the new velocity of the {@code Critter}
     */
    public void setVelocity(Vector velocity);

    public void setVelocity(double x, double y);

    /**
     * Gets the acceleration of the {@code Critter}
     * @return  the current acceleration of the {@code Critter}
     */
    public Vector getAcceleration();

    /**
     * Sets the acceleration of the {@code Critter}
     * @param acceleration  the new acceleration of the {@code Critter}
     */
    public void setAcceleration(Vector acceleration);

    public void setAcceleration(double x, double y);

    /**
     * Advance the {@code Critter} according to the equations of motion
     * @param dt    the change in time (delta time)
     */
    public void advance(double dt);

    /**
     * Draw the {@code Critter} at its current position
     */
    public void draw();

    /**
     * Gets the unique ID of the {@code Critter}
     * @return  the unique ID of the {@code Critter}
     */
    public long getId();

    /**
     * Gets the string unique ID of the {@code Critter}
     * @return  the unique ID of {@code Critter} as a hex string
     */
    public String getIdString();

    /**
     * Gets the {@code Hitbox} of the {@code Critter}
     * @return  the {@link cosmic_conquistadors.Hitbox} of the {@code Critter}
     */
    public Hitbox getHitbox();

    /**
     * Calculates whether two {@code Critter}s are colliding
     * @param first     the first {@code Critter}
     * @param second    the second {@code Critter}
     * @return          {@code true} if the {@code Critter}s are colliding, else
     *     {@code false}
     */
    public static boolean collides(Critter first, Critter second) {
        return Hitbox.intersects(first.getHitbox(), second.getHitbox());
    }
}
