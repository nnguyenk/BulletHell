package BulletHell.Powerups;

import BulletHell.Bullet;
import BulletHell.BulletManager;

public class Slow {
    // all bullets on the screen slow down/come to a halt where they are.
    public final static double MAX_IMMUNITY = 5; // The maximum number of seconds the player is immuned.

    private double remainingSlow;
    private boolean activated;

    /**
     * Activate the slow, reducing the speed of all bullets on the screen;
     */
    public void startSlow(BulletManager manager) {
        remainingSlow = MAX_IMMUNITY;
        slowBullets(manager);
    }

    /**
     * Returns true if slow is still in effect.
     */
    public boolean isSlowed() {
        return (remainingSlow > 0);
    }

    /**
     * Reduces the remaining time of the slow effect.
     * Ends the effect if the duration has expired.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceSlow(double dt, BulletManager manager) {
        remainingSlow -= dt;
        if (!isSlowed()) {
            endSlow(manager);
        }
    }

    /**
     * Ends the slow and returns the speed of the bullets to normal.
     */
    private void endSlow(BulletManager manager) {
        restoreBullets(manager);
    }

    private void slowBullets(BulletManager manager) {
        for (Bullet bullet : manager.getAllBullets()) {
            if (bullet.isAlive()) {
                bullet.reduceSpeed();
            }
        }
    }

    private void restoreBullets(BulletManager manager) {
        for (Bullet bullet : manager.getAllBullets()) {
            if (bullet.isAlive()) {
                bullet.restoreSpeed();
            }
        }
    }
}
