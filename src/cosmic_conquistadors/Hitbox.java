package cosmic_conquistadors;

/**
 * The {@code Hitbox} class represents a box which is used for collision
 * testing.
 */
public class Hitbox {
    private Vector position;
    private Vector halfSize;

    /**
     * Constructor
     * @param x             the x position of the {@code Hitbox}
     * @param y             the y position of the {@code Hitbox}
     * @param halfWidth     the half width of the {@code Hitbox}
     * @param halfHeight    the half height of the {@code Hitbox}
     */
    public Hitbox(double x, double y, double halfWidth, double halfHeight) {
        this.position = new Vector(x, y);
        this.halfSize = new Vector(halfWidth, halfHeight);
    }

    /**
     * Constructor
     * @param position  the {@link Vector} position of the {@code Hitbox}
     * @param halfSize  the {@link Vector} half size of the {@code Hitbox}
     */
    public Hitbox(Vector position, Vector halfSize) {
        this(position.getX(), position.getY(), halfSize.getX(),
             halfSize.getY());
    }

    /**
     * Constructor
     */
    public Hitbox() { this(0.0, 0.0, 0.0, 0.0); }

    /**
     * Gets the position of the {@code Hitbox}
     * @return  the position of the {@code Hitbox}
     */
    public Vector getPosition() { return this.position; }

    /**
     * Gets the size of the {@code Hitbox}
     * @return  the half size of the {@code Hitbox}
     */
    public Vector getSize() { return this.halfSize; }

    /**
     * Sets the position of the {@code Hitbox}
     * @param x the new x-position
     * @param y the new y-position
     */
    public void setPosition(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    /**
     * Sets the position of the {@code Hitbox}
     * @param position  the new position
     */
    public void setPosition(Vector position) {
        this.setPosition(position.getX(), position.getY());
    }

    /**
     * Sets the size of the {@code Hitbox}
     * @param halfWidth     the new half width
     * @param halfHeight    the new half height
     */
    public void setSize(double halfWidth, double halfHeight) {
        this.halfSize.setX(halfWidth);
        this.halfSize.setY(halfHeight);
    }

    /**
     * Sets the size of the {@code Hitbox}
     * @param halfSize  the new {@link Vector} half size of the {@code Hitbox}
     */
    public void setSize(Vector halfSize) {
        this.setSize(halfSize.getX(), halfSize.getY());
    }

    /**
     * Gets the left edge of the {@code Hitbox}
     * @return  the x-coordinate of the left edge
     */
    public double getLeft() {
        return this.getPosition().getX() - this.getSize().getX();
    }

    /**
     * Gets the top edge of the {@code Hitbox}
     * @return  the y-coordinate of the top edge
     */
    public double getTop() {
        return this.getPosition().getY() + this.getSize().getY();
    }

    /**
     * Gets the right edge of the {@code Hitbox}
     * @return  the x-coordinate of the right edge
     */
    public double getRight() {
        return this.getPosition().getX() + this.getSize().getX();
    }

    /**
     * Gets the bottom of the {@code Hitbox}
     * @return  the y-coordinate of the bottom edge
     */
    public double getBottom() {
        return this.getPosition().getY() - this.getSize().getY();
    }

    /**
     * Calculates whether two {@code Hitbox}es are intersecting
     * @param first     the first {@code Hitbox}
     * @param second    the second {@code Hitbox}
     * @return          {@code true} if the two {@code Hitbox}es are
     *                  intersecting, else {@code false}
     */
    public static boolean intersects(Hitbox first, Hitbox second) {
        return (first.getLeft() <= second.getRight() &&
                second.getLeft() <= first.getRight() &&
                first.getBottom() <= second.getTop() &&
                second.getBottom() <= first.getTop());
    }
}
