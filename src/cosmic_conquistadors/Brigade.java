package cosmic_conquistadors;

import java.util.ArrayList;

public class Brigade extends DefaultCritter {
    private Vector size;
    private ArrayList<Enemy> enemies;

    public Brigade(int sizeX, int sizeY) {
        this.size = new Vector(sizeX, sizeY);
        this.enemies = new ArrayList<Enemy>(sizeX * sizeY);
        this.spawnEnemies();
    }

    public Brigade(Vector size) { this((int)size.getX(), (int)size.getY()); }

    public Vector getSize() { return this.size; }

    private void spawnEnemies() {
        double y = 0.8;
        for (int i = 0; i < size.getY(); i++) {
            double x = -0.8;
            for (int j = 0; j < size.getX(); j++) {
                Enemy enemy = new Enemy();
                enemy.setPosition(x, y);
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
        boolean result = false;
        for (Enemy enemy : this.enemies) {
            result |= enemy.hitTest(other);
        }

        return result;
    }
}
