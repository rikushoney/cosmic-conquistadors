package cosmic_conquistadors;

import java.util.ArrayList;

/**
 * The {@code Brigade} class represents the grid of {@link Enemy enemies} that
 * descend down to Earth.
 */
public class Brigade extends DefaultCritter {
    private Vector size;
    private ArrayList<Enemy> enemies;
    private Enemy injured;

    /**
     * Constructor
     * @param sizeX the number of {@link Enemy enemies} in the x-direction
     * @param sizeY the number of {@link Enemy enemies} in the y-direction
     */
    public Brigade(int sizeX, int sizeY) {
        super();
        this.size = new Vector(sizeX, sizeY);
        this.enemies = new ArrayList<Enemy>(sizeX * sizeY);
        // this.setVelocity(0.0001, 0);
        this.spawnEnemies();
    }

    /**
     * Constructor
     * @param size  the size of the brigade
     */
    public Brigade(Vector size) { this((int)size.getX(), (int)size.getY()); }

    /**
     * Gets the size of the brigade
     * @return  the size of the brigade (width x height)
     */
    public Vector getSize() { return this.size; }

    private void spawnEnemies() {
        double y = 0.8;
        for (int i = 0; i < size.getY(); i++) {
            double x = -0.8;
            for (int j = 0; j < size.getX(); j++) {
                Enemy enemy = new Enemy();
                Vector velocity = this.getVelocity();
                enemy.setPosition(x, y);
                enemy.setVelocity(velocity.getX(), velocity.getY());
                this.enemies.add(enemy);
                Utility.debugPrintLine("Spawned enemy " + enemy.getIdString() +
                                       " at " + enemy.getPosition().toString());
                x += Enemy.SIDE_LENGTH + 0.1;
            }
            y -= Enemy.SIDE_LENGTH + 0.1;
        }
    }

    @Override
    public void advance(double dt) {
        for (Enemy enemy : this.enemies) {
            enemy.advance(dt);
        }
    }

    @Override
    public void draw() {
        for (Enemy enemy : this.enemies) {
            enemy.draw();
        }
    }

    @Override
    public boolean hitTest(Critter other) {
        // NOTE: we can do some optimization here and check the large brigade
        // rect for intersection before we check each individual enemy
        for (Enemy enemy : this.enemies) {
            if (enemy.hitTest(other)) {
                this.injured = enemy;
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the injured {@link Enemy} and removes it from the {@code Brigade}
     * @return  the {@link Enemy} that was hit by a {@link Missile} if any, else
     *          null
     */
    public Enemy popInjured() {
        Enemy result = this.injured;
        if (result != null) {
            this.enemies.remove(this.injured);
            this.injured = null;
        }

        return result;
    }
}
