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

    private double aim;

    public Shooter() { this.aim = Math.PI / 2; }

    public double getAim() { return this.aim; }

    public void setAim(double aim) { this.aim = aim; }

    @Override
    public void advance(double dt) {
        super.advance(dt);
        this.getPosition().clamp(Shooter.BOUNDS[0], Shooter.BOUNDS[1]);
        // NOTE: this is handled in InvaderGameState
        // this.aim.clamp(0, Math.PI);
    }

    @Override
    public void draw() {
        Vector position = this.getPosition();
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledSquare(position.getX(), position.getY(), SIDE_LENGTH);
        StdDraw.setPenColor(StdDraw.ORANGE);
        double muzzleX =
            position.getX() + Shooter.MUZZLE_LENGTH * Math.cos(this.getAim());
        double muzzleY =
            position.getY() + Shooter.MUZZLE_LENGTH * Math.sin(this.getAim());
        StdDraw.setPenRadius(0.01);
        StdDraw.line(position.getX(), position.getY(), muzzleX, muzzleY);
        StdDraw.setPenRadius();
    }
}
