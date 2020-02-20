/*
    Written by Rikus Honey for cosmic-conquistadors
    https://github.com/rikushoney/cosmic-conquistadors
*/

public abstract class Widget {
    private Point position;

    public Widget(Point position) {
        this.position = position;
    }

    public Widget() {
        this(new Point(0.0, 0.0));
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public abstract void doPaint();
}
