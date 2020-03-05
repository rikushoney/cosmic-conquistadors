package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;

public class Missile extends DefaultCritter {
    private double MISSILE_SPEED = 0.001;

    public Missile(Vector position, double angle) {
        this.setPosition(position);
        double horizontalVelocity = MISSILE_SPEED * Math.cos(angle);
        double verticalVelocity = MISSILE_SPEED * Math.sin(angle);
        this.setVelocity(new Vector(horizontalVelocity, verticalVelocity));
    }

    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        Vector position = this.getPosition();
        StdDraw.filledRectangle(position.getX(), position.getY(), 0.025, 0.025);
    }
}
