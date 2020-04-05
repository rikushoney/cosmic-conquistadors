package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;

/**
 * The {@code Shooter} class is used to represent our so-called hero that sits
 * at the bottom of the screen and shoots {@link Missile missiles} at oncoming
 * {@link Enemy enemies}.
 */
public class Shooter extends DefaultCritter {
    public static final double SIDE_LENGTH = 0.05;
    public static final double MUZZLE_LENGTH = 0.1;
    public static final Vector[] BOUNDS = { new Vector(-1, -1),
                                            new Vector(1, 1) };

    private Angle aim;

    public Shooter() { this.aim = new Angle(Math.PI / 2, 0, 0); }

    public Angle getAim() { return this.aim; }

    @Override
    public void advance(double dt) {
        super.advance(dt);
        this.getPosition().clamp(Shooter.BOUNDS[0], Shooter.BOUNDS[1]);
        this.aim.advance(dt);
        this.aim.clamp(0, Math.PI);
    }

    @Override
    public void draw() {
        Vector position = this.getPosition();
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledSquare(position.getX(), position.getY(), SIDE_LENGTH);
        StdDraw.setPenColor(StdDraw.ORANGE);
        double muzzleX =
            position.getX() +
            Shooter.MUZZLE_LENGTH * Math.cos(this.getAim().getAngle());
        double muzzleY =
            position.getY() +
            Shooter.MUZZLE_LENGTH * Math.sin(this.getAim().getAngle());
        StdDraw.setPenRadius(0.01);
        StdDraw.line(position.getX(), position.getY(), muzzleX, muzzleY);
        StdDraw.setPenRadius();
    }
}
