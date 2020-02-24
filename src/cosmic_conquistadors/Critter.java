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
     * Advance the {@code Critter} according to the equations of motion
     * @param dt    the change in time (delta time)
     */
    public void advance(double dt);

    /**
     * Draw the {@code Critter} at its current position
     */
    public void draw();
}
