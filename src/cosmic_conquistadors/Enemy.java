package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;

public class Enemy extends DefaultCritter {
    public static final double SIDE_LENGTH = 0.05;

    private int bounty;

    public Enemy() {}

    public int getBounty() { return this.bounty; }

    public void setBounty(int bounty) { this.bounty = bounty; }

    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        Vector position = this.getPosition();
        StdDraw.filledSquare(position.getX(), position.getY(), SIDE_LENGTH);
    }
}
