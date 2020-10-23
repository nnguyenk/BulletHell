package BulletHell.Powerups;

import java.awt.Color;
import java.text.DecimalFormat;

import BulletHell.Bullet;
import BulletHell.BulletHell;
import BulletHell.BulletManager;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.events.Key;

/**
 * A powerup that can be activated by pressing Q.
 * Slows all bullets currently on the screen by half.
 */
public class Slow implements Powerups {
    public static final double BOX_SIZE = 30;
    public static final double MAX_COOLDOWN = 10;
    public static final double MAX_DURATION = 5;

    private double remainingSlow;
    private double cooldown;
    private BulletHell mainGame;

    private GraphicsGroup shape;
    private Rectangle border;
    private Rectangle energy;
    private GraphicsText remainingText;

    public Slow(BulletHell bulletHell) {
        mainGame = bulletHell;
        cooldown = MAX_COOLDOWN;

        shape = new GraphicsGroup();
        border = new Rectangle(0, 0, BOX_SIZE, BOX_SIZE);
        remainingText = new GraphicsText("", 0, 0);
        remainingText.setFontSize(20);

        shape.add(border);
        shape.add(remainingText, 6, 22);
    }

    /**
     * Creates a new rectangle with a different height to represent the fill value.
     * Fills the rectangle with different shades of cyan depending on the cooldown. 
     * 
     * @param remainingCD The remaining cooldown to help calculate the height of the rectangle.
     */
    private void fill(double remainingCD) {
        if (remainingCD < 0) {
            remainingCD = 0;
        }
        double cooldownRatio = remainingCD / MAX_COOLDOWN;
        energy = new Rectangle(0, BOX_SIZE * cooldownRatio, BOX_SIZE, BOX_SIZE * (1 - cooldownRatio));
        energy.setStroked(false);
        energy.setFillColor(new Color(
            (int) (255 * cooldownRatio),
            255,
            255
        ));
        shape.add(energy);
    }

    /**
     * Reduces the cooldown of this power if it is not in effect.
     * Slowly fills up the box to indicate how much cooldown remains.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceCooldown(double dt) {
        if (!inEffect() && onCooldown()) {
            cooldown -= dt;
            fill(cooldown);
            if (!onCooldown()) {
                endCooldown();
            }
        }
    }

    /**
     * Indicates that the cooldown is over with the letter 'Q'.
     */
    private void endCooldown() {
        remainingText.setText("Q");
        shape.add(remainingText);
    }

    /**
     * Returns true if slow is on cooldown.
     */
    private boolean onCooldown() {
        return (cooldown > 0);
    }
    
    /**
     * Activate the slow.
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
     * Ends the effect if the duration has expired.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceDuration(double dt) {
        if (inEffect()) {
            remainingSlow -= dt;
            remainingText.setText(new DecimalFormat("#").format(remainingSlow)); // Truncate all decimal points.
            shape.add(remainingText);
            if (!inEffect()) {
                deactivate();
            }
        }
    }

    /**
     * Restores the speed of the bullets, and puts the power on cooldown.
     */
    private void deactivate() {
        BulletManager manager = mainGame.getBulletManager();
        restoreBullets(manager);
        cooldown = MAX_COOLDOWN;
        remainingText.setText("");
    }

    /**
     * Returns the display box of the power.
     */
    public GraphicsGroup getShape() {
        return shape;
    }

    /**
     * Returns the key used to activate this power.
     */
    public Key getKey() {
        return Key.Q;
    }

    /**
     * Slows all bullets in the manager by half of their current speed.
     */
    private void slowBullets(BulletManager manager) {
        for (Bullet bullet : manager.getAllBullets()) {
            if (bullet.isAlive()) {
                bullet.reduceSpeed(0.5);
            }
        }
    }

    /**
     * Restores the speed of all bullets in the manager by doubling them.
     */
    private void restoreBullets(BulletManager manager) {
        for (Bullet bullet : manager.getAllBullets()) {
            if (bullet.isAlive()) {
                bullet.increaseSpeed(2);
            }
        }
    }
}
