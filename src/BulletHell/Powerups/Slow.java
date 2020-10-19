package BulletHell.Powerups;

import java.awt.Color;
import java.text.DecimalFormat;

import BulletHell.Bullet;
import BulletHell.BulletHell;
import BulletHell.BulletManager;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;

public class Slow implements Powerups {
    public static final double SIZE = 30;
    public static final int MAX_COOLDOWN = 3; // The number of seconds that this power is on cooldown.
    public static final int MAX_DURATION = 5; // The maximum number of seconds the power is active.

    private double remainingSlow;
    private double remainingCD;
    private BulletHell mainGame;

    private GraphicsGroup shape;
    private Rectangle box;
    private GraphicsText CD; // A text box that shows the remaining CD of this power.

    public Slow(BulletHell bulletHell) {
        mainGame = bulletHell;
        remainingCD = MAX_COOLDOWN;

        shape = new GraphicsGroup();
        box = new Rectangle(0, 0, SIZE, SIZE);
        CD = new GraphicsText(Integer.toString(MAX_COOLDOWN), 0, 0);

        shape.add(box);
        shape.add(CD, 5, 20);
    }

    /**
     * Reduces the cooldown of this power if while it is not in effect.
     * Once the CD is over, changes the color of the box to indicate that the power is ready.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceCooldown(double dt) {
        if (!inEffect()) {
            remainingCD -= dt;
            CD.setText(new DecimalFormat("#").format(remainingCD)); // Truncate all decimal points.
            if (!onCooldown()) {
                CD.setText("");
                box.setFillColor(Color.CYAN);
            }
        }
    }

    /**
     * Returns true if slow is on cooldown.
     */
    public boolean onCooldown() {
        return (remainingCD > 0);
    }
    
    /**
     * Activate the slow, reducing the speed of all bullets on the screen.
     */
    public void activate() {
        if (!onCooldown() && !inEffect()) {
            BulletManager manager = mainGame.getBulletManager();
            box.setFillColor(Color.WHITE);
            remainingSlow = MAX_DURATION;
            slowBullets(manager);
        }
    }

    /**
     * Returns true if slow is in effect.
     */
    public boolean inEffect() {
        return (remainingSlow > 0);
    }

    /**
     * Reduces the remaining time of the slow effect.
     * Ends the effect if the duration has expired, and puts the power on cooldown.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceDuration(double dt) {
        if (inEffect()) {
            BulletManager manager = mainGame.getBulletManager();
            remainingSlow -= dt;
            if (!inEffect()) {
                restoreBullets(manager);

                remainingCD = MAX_COOLDOWN;
                CD.setText(Integer.toString(MAX_COOLDOWN));
            }
        }
    }

    /**
     * Returns the shape of the power.
     */
    public GraphicsGroup getShape() {
        return shape;
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
