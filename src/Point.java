/*
    Written by Rikus Honey for cosmic-conquistadors
    https://github.com/rikushoney/cosmic-conquistadors
*/

public class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(0.0, 0.0);
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPosition(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public static double distanceTo(Point p1, Point p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getX() - p2.getY();

        return Math.sqrt(dx * dx + dy * dy);
    }
}
