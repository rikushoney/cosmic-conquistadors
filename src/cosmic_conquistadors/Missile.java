package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;

/**
 * The {@code Missile} class is used to represent missiles fired by either the
 * {@link Shooter hero} or {@link Enemy enemies}
 */
public class Missile extends DefaultCritter {
    public static final double MISSILE_SPEED = 0.001;
    public static final double SIDE_LENGTH = 0.025;

    private Critter source;

    private Missile() {
        super();
        this.getHitbox().setSize(Missile.SIDE_LENGTH, Missile.SIDE_LENGTH);
    }

    /**
     * Constructor
     * @param source    the {@link Critter} that fired the missile
     */
    public Missile(Critter source) {
        this();

        this.setPosition(source.getPosition());
        if (source instanceof Shooter) {
            Shooter shooter = (Shooter)source;
            // NOTE: should the missile's velocity depend on the velocity of the
            // shooter?
            Vector aimVector = new Vector(Math.cos(shooter.getAim()),
                                          Math.sin(shooter.getAim()));
            aimVector.scale(Missile.MISSILE_SPEED);
            this.setVelocity(aimVector);
        } else if (source instanceof Enemy) {
            this.setVelocity(0, -Missile.MISSILE_SPEED);
        } else {
            throw new IllegalArgumentException(
                "Only Shooter and Enemy classes can spawn missiles");
        }

        this.source = source;
    }

    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.WHITE);
        Vector position = this.getPosition();
        StdDraw.filledSquare(position.getX(), position.getY(), SIDE_LENGTH);

        super.draw();
    }

    /**
     * Gets the source of the missile
     * @return  the {@link Critter} that fired the {@code Missile}
     */
    public Critter getSource() { return this.source; }
}
