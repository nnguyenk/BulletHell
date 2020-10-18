package BulletHell.Powerups;

import BulletHell.Bullet;
import BulletHell.BulletHell;
import BulletHell.BulletManager;

public class Slow implements Powerups {
    public final static double MAX_DURATION = 5; // The maximum number of seconds the power is active.

    private double remainingSlow;
    private BulletHell mainGame;

    public Slow(BulletHell bulletHell) {
        mainGame = bulletHell;
    }
    
    /**
     * Activate the slow, reducing the speed of all bullets on the screen.
     */
    public void activate() {
        BulletManager manager = mainGame.getBulletManager();
        remainingSlow = MAX_DURATION;
        slowBullets(manager);
    }

    /**
     * Returns true if slow is in effect.
     */
    public boolean inEffect() {
        return (remainingSlow > 0);
    }

    /**
     * Reduces the remaining time of the slow effect.
     * Ends the effect if the duration has expired.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceDuration(double dt) {
        BulletManager manager = mainGame.getBulletManager();
        remainingSlow -= dt;
        if (!inEffect()) {
            restoreBullets(manager);
        }
    }

    /**
     * Slows all bullets in the manager.
     */
    private void slowBullets(BulletManager manager) {
        for (Bullet bullet : manager.getAllBullets()) {
            if (bullet.isAlive()) {
                bullet.reduceSpeed();
            }
        }
    }

    /**
     * Restores the speed of all bullets in the manager.
     */
    private void restoreBullets(BulletManager manager) {
        for (Bullet bullet : manager.getAllBullets()) {
            if (bullet.isAlive()) {
                bullet.restoreSpeed();
            }
        }
    }
}
