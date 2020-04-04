package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;

/**
 * The {@code Shooter} class is used to represent our so-called hero that sits
 * at the bottom of the screen and shoots {@link Missile missiles} at oncoming
 * {@link Enemy enemies}.
 */
public class Shooter extends DefaultCritter {
    public static final double SIDE_LENGTH = 0.05;
    public static final Vector[] BOUNDS = {
        new Vector(-1, -1),
        new Vector(1, 1)
    };

    public Shooter() {}

    @Override
    public void advance(double dt) {
        super.advance(dt);
        this.getPosition().clamp(this.BOUNDS[0], this.BOUNDS[1]);
    }

    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.GREEN);
        Vector position = this.getPosition();
        StdDraw.filledSquare(position.getX(), position.getY(), SIDE_LENGTH);
    }
}
