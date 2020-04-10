package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;

/**
 * The {@code Missile} class is used to represent missiles fired by either the
 * {@link Shooter hero} or {@link Enemy enemies}
 */
public class Missile extends DefaultCritter {
    public static final double MISSILE_SPEED = 0.001;
    public static final double SIDE_LENGTH = 0.025;

    public Missile(Critter source) {
        this.setPosition(source.getPosition());
        if (source instanceof Shooter) {
            Shooter shooter = (Shooter)source;
            // NOTE: should the missile's velocity depend on the velocity of the
            // shooter?
            this.setVelocity(
                Vector.add(new Vector(Missile.MISSILE_SPEED, shooter.getAim()),
                           shooter.getVelocity()));
        } else if (source instanceof Enemy) {
            this.setVelocity(0, -Missile.MISSILE_SPEED);
        } else {
            throw new IllegalArgumentException(
                "Only Shooter and Enemy classes can spawn missiles");
        }
    }

    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.WHITE);
        Vector position = this.getPosition();
        StdDraw.filledSquare(position.getX(), position.getY(), SIDE_LENGTH);
    }
}
