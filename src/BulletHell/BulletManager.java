package BulletHell;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import BulletHell.BulletTypes.*;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

public class BulletManager {
    private CanvasWindow canvas;
    private List<Bullet> bullets = new ArrayList<>();
    private List<Bullet> bulletsToRemove = new ArrayList<>();
    private boolean hitPlayer;

    public BulletManager(CanvasWindow canvas) {
        this.canvas = canvas;
    }

    /**
     * Adds all bullets to the canvas.
     * Adds the line demarcating the cieling and player info above
     */
    public void spawnBullets(int bulletsNumber) {
        for (int i = 0; i < bulletsNumber; i++) {
            Bullet bullet = createRandomBullet();
            canvas.add(bullet.getShape());
            bullets.add(bullet);
        }

        canvas.add(new Rectangle(0, 40, 800, 1));
    }

    /**
     * Generates a random number and creates the corresponding bullet.
     */
    private Bullet createRandomBullet() {
        Bullet randomBullet;
        int i = Utility.randomInt(1, 2);

        switch (i) {
            case 1: 
                randomBullet = new RedBullet(canvas);
                break;
            case 2:
                randomBullet = new BlueBullet(canvas);
                break;
            default:
                randomBullet = null;
                break;
        }
        return randomBullet;
    }

    /**
     * Updates the position of all bullets.
     * Deletes any bullet that collides with the player.
     * 
     * @return true if the player was hit with any bullets
     */
    public boolean bulletsIntersect(Player player) {
        hitPlayer = false;
        for (Bullet bullet : bullets) {
            if (bullet.isAlive()) {
                bullet.updatePosition();

                if (bullet.collidePlayer(player) && !player.stillImmune()) {
                    hitPlayer = true;
                    bulletsToRemove.add(bullet);

                }
            }
            else {
                bulletsToRemove.add(bullet);
            }
        }
        removeBullets();
        return hitPlayer;
    }

    /**
     * Removes the bullet from tha canvas and the list.
     */
    private void removeBullets() {
        for (Bullet bulletToRemove : bulletsToRemove){
            canvas.remove(bulletToRemove.getShape());
            bullets.remove(bulletToRemove);
        }
        bulletsToRemove.clear();
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
