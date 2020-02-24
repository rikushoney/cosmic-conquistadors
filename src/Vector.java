/**
 * The {@code Vector} class provides a way to represent values with both scale
 * and magnitude in two dimensions.
 */
public class Vector {
    double x, y;

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
     * Gets the x-value of the vector
     * @return  the current x-value
     */
    public double getX() { return this.x; }

    /**
     * Sets the x-value of the vector
     * @param x the new x-value
     */
    public void setX(double x) { this.x = x; }

    /**
     * Gets the y-value of the vector
     * @return  the current y-value
     */
    public double getY() { return this.y; }

    /**
     * Sets the y-value of the vector
     * @param y the new y-value
     */
    public void setY(double y) { this.y = y; }

    /**
     * Scales the vector with a factor
     * @param v         the vector to scale
     * @param factor    the factor to scale the vector with
     * @return          a new scaled version of the vector
     */
    public static Vector scale(Vector v, double factor) {
        return new Vector(v.getX() * factor, v.getY() * factor);
    }

    /**
     * Calculates the distance between 2 vectors
     * @param v1    the first vector
     * @param v2    the second vector
     * @return      the distance between the 2 vectors
     */
    public static double distance(Vector v1, Vector v2) {
        double dx = v1.getX() - v2.getX();
        double dy = v1.getX() - v2.getY();

        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }
}
