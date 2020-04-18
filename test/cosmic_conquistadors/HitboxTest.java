package cosmic_conquistadors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HitboxTest {
    @Test
    public void calculateLeft() {
        Hitbox h = new Hitbox(1, 1, 1, 1);

        assertEquals(0, h.getLeft(), 0);
    }

    @Test
    public void calculateTop() {
        Hitbox h = new Hitbox(1, 1, 1, 1);

        assertEquals(2, h.getTop(), 0);
    }

    @Test
    public void calculateRight() {
        Hitbox h = new Hitbox(1, 1, 1, 1);

        assertEquals(2, h.getRight(), 0);
    }

    @Test
    public void calculateBottom() {
        Hitbox h = new Hitbox(1, 1, 1, 1);

        assertEquals(0, h.getBottom(), 0);
    }

    @Test
    public void horizontalColliding() {
        Hitbox h1 = new Hitbox(-0.5, 0, 1, 0);
        Hitbox h2 = new Hitbox(0.5, 0, 1, 0);

        assertTrue(Hitbox.intersects(h1, h2));
    }

    @Test
    public void horizontalNotColliding() {
        Hitbox h1 = new Hitbox(-1, 0, 0.5, 0);
        Hitbox h2 = new Hitbox(1, 0, 0.5, 0);

        assertFalse(Hitbox.intersects(h1, h2));
    }

    @Test
    public void verticalColliding() {
        Hitbox h1 = new Hitbox(0, -0.5, 0.5, 1);
        Hitbox h2 = new Hitbox(0, 0.5, 0.5, 1);

        assertTrue(Hitbox.intersects(h1, h2));
    }

    @Test
    public void verticalNotColliding() {
        Hitbox h1 = new Hitbox(0, -1, 0.5, 0.5);
        Hitbox h2 = new Hitbox(0, 1, 0.5, 0.5);

        assertFalse(Hitbox.intersects(h1, h2));
    }
}
