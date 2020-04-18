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
    public static final Vector[] BOUNDS = {
        new Vector(-1 + Shooter.SIDE_LENGTH, -1 + Shooter.SIDE_LENGTH),
        new Vector(1 - Shooter.SIDE_LENGTH, 1 - Shooter.SIDE_LENGTH)
    };

    private double aim;

    /**
     * Constructor
     */
    public Shooter() {
        super();
        this.aim = Math.PI / 2;
        this.getHitbox().setSize(Shooter.SIDE_LENGTH, Shooter.SIDE_LENGTH);
    }

    /**
     * Gets the angle the {@code Shooter} is aiming at
     * @return  the angle the {@code Shooter} is aiming at relative to the
     *          horizontal axis
     */
    public double getAim() { return this.aim; }

    /**
     * Sets the angle the {@code Shooter} is aiming at
     * @param aim   the new angle relative to the horizontal
     */
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
        double muzzleX = Shooter.MUZZLE_LENGTH * Math.cos(this.getAim());
        double muzzleTipX = position.getX() + muzzleX;
        double muzzleY = Shooter.MUZZLE_LENGTH * Math.sin(this.getAim());
        double muzzleTipY = position.getY() + muzzleY;
        StdDraw.setPenRadius(0.01);
        StdDraw.line(position.getX(), position.getY(), muzzleTipX, muzzleTipY);

        super.draw();
    }
}
