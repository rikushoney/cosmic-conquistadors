package cosmic_conquistadors;

import edu.princeton.cs.introcs.StdDraw;

/**
 * The {@code Enemy} class is used to represent the evil aliens that are firing
 * {@link Missile missiles} at our {@link Shooter hero} as they ascend down to
 * Earth.
 */
public class Enemy extends DefaultCritter {
    public static final double SIDE_LENGTH = 0.05;

    private int bounty;

    /**
     * Constructor
     */
    public Enemy() {
        super();
        this.getHitbox().setSize(Enemy.SIDE_LENGTH, Enemy.SIDE_LENGTH);
    }

    /**
     * Gets the "bounty" on the enemy
     * @return  the points the enemy is worth
     */
    public int getBounty() { return this.bounty; }

    /**
     * Sets the "bounty" on the enemy
     * @param bounty    the new bounty value
     */
    public void setBounty(int bounty) { this.bounty = bounty; }

    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        Vector position = this.getPosition();
        StdDraw.filledSquare(position.getX(), position.getY(),
                             Enemy.SIDE_LENGTH);

        super.draw();
    }
}
