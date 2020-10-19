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
    public static final int MAX_COOLDOWN = 10; // The number of seconds that this power is on cooldown.
    public static final int MAX_DURATION = 5; // The maximum number of seconds the power is active.

    private double remainingSlow;
    private double remainingCD;
    private BulletHell mainGame;

    private GraphicsGroup shape;
    private Rectangle border;
    private Rectangle energy;
    private GraphicsText remainingText; // A text box that shows how many seconds left until the slow expires.

    public Slow(BulletHell bulletHell) {
        mainGame = bulletHell;
        remainingCD = MAX_COOLDOWN;

        shape = new GraphicsGroup();
        border = new Rectangle(0, 0, SIZE, SIZE);
        remainingText = new GraphicsText("", 0, 0);

        shape.add(border);
    }

    /**
     * Creates a new rectangle with a different height to represent the fill value.
     * Fills the rectangle with different shades of cyan depending on the cooldown. 
     * 
     * @param remainingCD The remaining cooldown to help calculate the width of the rectangle.
     */
    private void fill(double remainingCD) {
        if (remainingCD < 0) {
            remainingCD = 0;
        }
        double cooldownRatio = remainingCD / MAX_COOLDOWN;
        energy = new Rectangle(0, SIZE * cooldownRatio, SIZE, SIZE * (1 - cooldownRatio));
        energy.setStroked(false);
        energy.setFillColor(new Color(
            (int) (255 * cooldownRatio),
            255,
            255
        ));
        shape.add(energy);
    }

    /**
     * Reduces the cooldown of this power if while it is not in effect.
     * Once the CD is over, changes the color of the box to indicate that the power is ready.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceCooldown(double dt) {
        if (!inEffect() && onCooldown()) {
            remainingCD -= dt;
            fill(remainingCD);
            if (!onCooldown()) {
                remainingText.setText("Q");
                shape.add(remainingText, 5, 20);
            }
        }
    }

    /**
     * Returns true if slow is on cooldown.
     */
    private boolean onCooldown() {
        return (remainingCD > 0);
    }
    
    /**
     * Activate the slow, reducing the speed of all bullets on the screen.
     */
    public void activate() {
        if (!onCooldown() && !inEffect()) {
            BulletManager manager = mainGame.getBulletManager();
            energy.setFillColor(Color.WHITE);
            remainingSlow = MAX_DURATION;
            slowBullets(manager);
        }
    }

    /**
     * Returns true if slow is in effect.
     */
    private boolean inEffect() {
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
            remainingText.setText(new DecimalFormat("#").format(remainingSlow)); // Truncate all decimal points.
            shape.add(remainingText);
            if (!inEffect()) {
                restoreBullets(manager);
                remainingCD = MAX_COOLDOWN;
                remainingText.setText("");
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
