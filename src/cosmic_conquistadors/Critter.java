package cosmic_conquistadors;

/**
 * The {@code Critter} interface defines what methods on-screen objects should
 * have. All on-screen objects should have a position, velocity and
 * acceleration. These are used to calculate how the object moves, how the
 * object should be displayed and how the object interacts with other on-screen
 * objects.
 */
public interface Critter {
    /**
     * Gets the position of the {@code Critter}
     * @return  the current position of the {@code Critter}
     */
    public Vector getPosition();

    /**
     * Sets the position of the {@code Critter}
     * @param position  the new position of the {@code Critter}
     */
    public void setPosition(Vector position);

    /**
     * Sets the position of the {@code Critter} on the x- and y-axis
     * individually
     * @param x the new x-position
     * @param y the new y-position
     */
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

    /**
     * Sets the velocity of the {@code Critter} on the x- and y-axis
     * individually
     * @param x the new x-velocity
     * @param y the new y-velocity
     */
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

    /**
     * Sets the acceleration of the {@code Critter} on the x- and y-axis
     * individually
     * @param x the new x-acceleration
     * @param y the new y-acceleration
     */
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
     * Gets the {@link Hitbox} of the {@code Critter}
     * @return  the {@link Hitbox} of the {@code Critter}
     */
    public Hitbox getHitbox();

    /**
     * Tests whether {@code other} is hitting us
     * @param other the {@code Critter} to test against
     * @return      {@code true} if {@code other} is hitting us
     */
    public boolean hitTest(Critter other);

    /**
     * Calculates whether two {@code Critters} are colliding
     * @param first     the first {@code Critter}
     * @param second    the second {@code Critter}
     * @return          {@code true} if the {@code Critters} are colliding, else
     *                  {@code false}
     */
    public static boolean collides(Critter first, Critter second) {
        return Hitbox.intersects(first.getHitbox(), second.getHitbox());
    }
}
