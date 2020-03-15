package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;

public class Missile extends DefaultCritter {
    public static final double MISSILE_SPEED = 0.001;
    public static final double SIDE_LENGTH = 0.025;

    public Missile(double angle) {
        double horizontalVelocity = MISSILE_SPEED * Math.cos(angle);
        double verticalVelocity = MISSILE_SPEED * Math.sin(angle);
        this.setVelocity(new Vector(horizontalVelocity, verticalVelocity));
    }

    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.WHITE);
        Vector position = this.getPosition();
        StdDraw.filledSquare(position.getX(), position.getY(), SIDE_LENGTH);
    }
}
