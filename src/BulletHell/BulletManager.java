package BulletHell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BulletHell.BulletTypes.*;

import edu.macalester.graphics.CanvasWindow;

public class BulletManager {
    private CanvasWindow canvas;
    private List<Bullet> bullets = new ArrayList<>();
    private Map<Integer, Bullet> bulletTypes = new HashMap<>();

    public BulletManager(CanvasWindow canvas) {
        this.canvas = canvas;
        bulletTypes.put(1, new RedBullet(canvas));
        bulletTypes.put(2, new BlueBullet(canvas));
    }

    /**
     * Adds all bullets to the canvas.
     */
    public void spawnBullets(int bulletsNumber) {
        for (int i = 0; i < bulletsNumber; i++) {
            Bullet bullet = bulletTypes.get(Utility.randomInt(1, bulletTypes.size()));
            canvas.add(bullet.getShape());
            bullets.add(bullet);
        }
    }

    /**
     * Updates the position of all bullets.
     * Deletes any bullet that collids with the player or the player's bullet.
     */
    public void updateBulletState(Player player) {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.updatePosition();
            if (bullet.collidePlayer(player)) { // Add collide with player bullet
                bulletsToRemove.add(bullet);
            }
        }
        for (Bullet bulletToRemove : bulletsToRemove) {
            removeBullet(bulletToRemove);
        }
    }

    /**
     * Removes the bullet from tha canvas and the list
     */
    private void removeBullet(Bullet bullet) {
        canvas.remove(bullet.getShape());
        bullets.remove(bullet);
    }
    
    /**
     * Returns true if there's still bullets.
     */
    public boolean bulletsLeft() {
        return (bullets.size() > 0);
    }

    /**
     * Returns the list of bullets currently on the screen.
     */
    public List<Bullet> getAllBullets() {
        return Collections.unmodifiableList(bullets);
    }
}
