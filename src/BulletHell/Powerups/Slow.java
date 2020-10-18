package BulletHell.Powerups;

import BulletHell.Bullet;
import BulletHell.BulletManager;

public class Slow {
    // all bullets on the screen slow down/come to a halt where they are.
    public final static double MAX_IMMUNITY = 5; // The maximum number of seconds the player is immuned.

    public double remainingSlow;
    private boolean activated;

    public void startSlow(BulletManager manager) {
        remainingSlow = MAX_IMMUNITY;
        activated = true;
        slowBullets(manager);
    }

    /**
     * Returns true if the player is still immune, and calculates the remaining time.
     */
    public boolean isSlowed(BulletManager manager) {
        if (remainingSlow > 0) {
            return true;
        }
        if (activated) {
            activated = false;
            restoreBullets(manager);
        }
        return false;
    }

    /**
     * Reduces the remaining time of the immunity.
     * 
     * @param dt The number of seconds that will be deducted from the remaining immunity.
     */
    public void reduceSlow(double dt) {
        remainingSlow -= dt;
    }

    public void slowBullets(BulletManager manager) {
        for (Bullet bullet : manager.getAllBullets()) {
            if (bullet.isAlive()) {
                bullet.reduceSpeed();
            }
        }
    }

    public void restoreBullets(BulletManager manager) {
        for (Bullet bullet : manager.getAllBullets()) {
            if (bullet.isAlive()) {
                bullet.restoreSpeed();
            }
        }
    }
}
