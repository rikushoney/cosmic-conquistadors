package cosmic_conquistadors;

/**
 * The {@code Hitbox class represents a box which is used for collision
 * testing.}
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
     * @param position  the {@code Vector} position of the {@code Hitbox}
     * @param halfSize  the {@code Vector} half size of the {@code Hitbox}
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
     * @param x the new x position
     * @param y the new y position
     */
    public void setPosition(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    /**
     * Sets the size of the {@code Hitbox}
     * @param halfWidth
     * @param halfHeight
     */
    public void setSize(double halfWidth, double halfHeight) {
        this.halfSize.setX(halfWidth);
        this.halfSize.setY(halfHeight);
    }

    /**
     * Sets the size of the {@code Hitbox}
     * @param halfSize  the new half size of the {@code Hitbox}
     */
    public void setSize(Vector halfSize) {
        this.halfSize.setX(halfSize.getX());
        this.halfSize.setY(halfSize.getY());
    }

    /**
     * Calculates the x- and y-coordinates of the {@code Hitbox}
     * @return  the x- and y-coordinates of the {@code Hitbox} in a clockwise
     *     rotation starting from the left edge (left->top->right->bottom)
     */
    public double[] edges() {
        return new double[] { this.position.getX() - this.halfSize.getX(),
                              this.position.getY() + this.halfSize.getY(),
                              this.position.getX() + this.halfSize.getX(),
                              this.position.getY() - this.halfSize.getY() };
    }

    /**
     * Calculates whether two {@code Hitbox}es are intersecting
     * @param first     the first {@code Hitbox}
     * @param second    the second {@code Hitbox}
     * @return          {@code true} if the two {@code Hitbox}es are
     *     intersecting, else {@code false}
     */
    public static boolean intersects(Hitbox first, Hitbox second) {
        double[] firstEdges = first.edges();
        double[] secondEdges = second.edges();
        return firstEdges[0] < secondEdges[2] &&
            firstEdges[2] > secondEdges[0] && firstEdges[1] > secondEdges[3] &&
            firstEdges[3] < secondEdges[0];
    }
}
