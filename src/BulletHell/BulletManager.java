package BulletHell;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import BulletHell.BulletTypes.*;
import edu.macalester.graphics.CanvasWindow;

public class BulletManager {
    private CanvasWindow canvas;
    private List<Bullet> bullets = new ArrayList<>();
    private List<Bullet> bulletsToRemove = new ArrayList<>();

    private boolean hitPlayer; // A boolean to determine whether any bullet has hit the player.

    public BulletManager(CanvasWindow canvas) {
        this.canvas = canvas;
    }

    /**
     * Adds all bullets to the canvas, makes sure that the bullets are not spawned on top of the player or terrains.
     */
    public void spawnBullets(int bulletsNumber, Player player, Terrain terrain) {
        for (int i = 0; i < bulletsNumber; i++) {
            Bullet newBullet;
            do {
                newBullet = createRandomBullet();
            }
            while (newBullet.collidePlayer(player) || newBullet.collideTerrain(terrain));

            canvas.add(newBullet.getShape());
            bullets.add(newBullet);
        }
    }

    /**
     * Generates a random number and creates the corresponding bullet.
     */
    private Bullet createRandomBullet() {
        Bullet randomBullet;
        int i = Utility.randomInt(1, 4);

        switch (i) {
            case 1: 
                randomBullet = new RedBullet(canvas);
                break;
            case 2:
                randomBullet = new CyanBullet(canvas);
                break;
            case 3:
                randomBullet = new YellowBullet(canvas);
                break;
            case 4:
                randomBullet = new GreenBullet(canvas);
                break;
            default:
                randomBullet = null;
                break;
        }
        return randomBullet;
    }

    /**
     * Updates the position of all bullets.
     * Deletes any bullet that collides with the player when immunity is inactive.
     * 
     * @return true if the player was damaged by any bullets, without eraser activated.
     */
    public boolean bulletsIntersect(Player player, Terrain terrain) {
        hitPlayer = false;
        for (Bullet bullet : bullets) {
            if (bullet.isAlive()) {
                bullet.updatePosition(terrain);
                bulletCollidePlayer(bullet, player);
            }
            else {
                bulletsToRemove.add(bullet);
            }
        }
        removeBullets();
        return hitPlayer;
    }

    /**
     * When a bullet collides with the player:
     *    - If eraser IS activated, the bullet is removed, and the player is unscathed.
     *    - If not, then if the player IS NOT immune, then they absorb the bullet and take damage.
     *    - If the player IS immune, then the bullet passes straight through them. 
     */
    private void bulletCollidePlayer(Bullet bullet, Player player) {
        if (bullet.collidePlayer(player)) {
            if (player.isErasing()) {
                bulletsToRemove.add(bullet);
            }
            else {
                bulletDamagePlayer(bullet, player);
            }
        }
    }

    /**
     * Changes the value of hitPlayer to indicate that the user is damaged.
     * Freezes the player if the bullet is cyan, or prevent immunity if it's yellow.
     * If the bullet is green, the screen changes color during invulnerability.
     * The bullets also end any healing prematurely.
     */
    private void bulletDamagePlayer(Bullet bullet, Player player) {
        if (!player.isImmune()) {
            hitPlayer = true;
            bulletsToRemove.add(bullet);
            if (bullet.getType() != BulletType.YELLOW) {
                player.startImmunity();
            }
            if (bullet.getType() == BulletType.CYAN) {
                player.freeze();
            }
            if (bullet.getType() == BulletType.GREEN) {
                canvas.setBackground(new Color(35, 125, 35));
            }
            if (player.isHealing()) {
                player.endHealing();
            }
        }
    }

    /**
     * Removes the bullet from tha canvas and the list.
     */
    private void removeBullets() {
        for (Bullet bulletToRemove : bulletsToRemove) {
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
     * Returns an unmodifable list of bullets currently on the screen.
     */
    public List<Bullet> getAllBullets() {
        return Collections.unmodifiableList(bullets);
    }
}






/*

public enum CompassDirection {
    NORTH, EAST, SOUTH, WEST;
}

void goInDirection(CompassDirection direction) {
    if (direction == CompassDirection.NORTH) {

    }
}

*/