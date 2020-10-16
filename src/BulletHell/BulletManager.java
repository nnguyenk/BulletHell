package BulletHell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import BulletHell.BulletTypes.*;

import edu.macalester.graphics.CanvasWindow;

public class BulletManager {
    private CanvasWindow canvas;
    private List<Bullet> bullets = new ArrayList<>();
    private List<Bullet> bulletsToRemove = new ArrayList<>();

    public BulletManager(CanvasWindow canvas) {
        this.canvas = canvas;
    }

    /**
     * Adds all bullets to the canvas.
     */
    public void spawnBullets(int bulletsNumber) {
        for (int i = 0; i < bulletsNumber; i++) {
            Bullet bullet = createRandomBullet();
            canvas.add(bullet.getShape());
            bullets.add(bullet);
        }
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
     * Deletes any bullet that collids with the player or the player's bullet.
     */
    public void updateBulletState(Player player, PlayerBullet playerBullet) {
        for (Bullet bullet : bullets) {
            bullet.updatePosition();
            // if (bullet.collidePlayer(player) 
            //     || (playerBullet != null && bullet.collidePlayerBullet(playerBullet))) {
            //     bulletsToRemove.add(bullet);
            // }
        }
    }

    /**
     * Removes the bullet from tha canvas and the list
     */
    public void removeBullets() {
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
