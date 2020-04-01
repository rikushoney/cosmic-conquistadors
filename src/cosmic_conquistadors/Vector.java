package cosmic_conquistadors;

/**
 * The {@code Vector} class provides a way to represent values with both scale
 * and magnitude in two dimensions.
 */
public class Vector {
    private double x, y;

    /**
     * Constructor
     * @param x the initial x-value
     * @param y the initial y-value
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor
     */
    public Vector() { this(0.0, 0.0); }

    /**
     * Gets the x-value of the {@code Vector}
     * @return  the current x-value
     */
    public double getX() { return this.x; }

    /**
     * Sets the x-value of the {@code Vector}
     * @param x the new x-value
     */
    public void setX(double x) { this.x = x; }

    /**
     * Gets the y-value of the {@code Vector}
     * @return  the current y-value
     */
    public double getY() { return this.y; }

    /**
     * Sets the y-value of the {@code Vector}
     * @param y the new y-value
     */
    public void setY(double y) { this.y = y; }

    /**
     * Scales the {@code Vector} with a factor
     * @param v         the {@code Vector} to scale
     * @param factor    the factor to scale the {@code Vector} with
     */
    public static void scale(Vector v, double factor) {
        v.setX(v.getX() * factor);
        v.setY(v.getY() * factor);
    }

    /**
     * Calculates the distance between 2 {@code Vector}s
     * @param v1    the first {@code Vector}
     * @param v2    the second {@code Vector}
     * @return      the distance between the 2 {@code Vector}s
     */
    public static double distance(Vector v1, Vector v2) {
        double dx = v1.getX() - v2.getX();
        double dy = v1.getX() - v2.getY();

        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    /**
     * Clamps the {@code Vector} to the bounds {@code
     * lower} and {@code upper}. Performs {@link Utility#clamp(double, double,
     * double) clamp} on each element in the {@code Vector}
     * @param lower the {@code Vector lower} bounds to clamp to
     * @param upper the {@code Vector upper} bounds to clamp to
     */
    public void clamp(Vector lower, Vector upper) {
        this.setX(Utility.clamp(this.getX(), lower.getX(), upper.getX()));
        this.setY(Utility.clamp(this.getY(), lower.getY(), upper.getY()));
    }

    @Override
    public String toString() {
        return "[" + Double.toString(this.x) + ";" + Double.toString(y) + "]";
    }
}
