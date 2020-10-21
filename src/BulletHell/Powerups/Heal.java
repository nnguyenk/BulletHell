package BulletHell.Powerups;

import java.awt.Color;
import java.text.DecimalFormat;

import BulletHell.BulletHell;
import BulletHell.HeartManager;
import BulletHell.Player;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.events.Key;

public class Heal implements Powerups {
    public static final double SIZE = 30;
    public static final int MAX_COOLDOWN = 15; // The number of seconds that this power is on cooldown.
    public static final int MAX_DURATION = 7; // The maximum number of seconds the power is active.

    private double remainingHeal;
    private double cooldown;
    private BulletHell mainGame;

    private GraphicsGroup shape;
    private Rectangle border;
    private Rectangle energy;
    private GraphicsText remainingText; // A text box that shows how many seconds left until the slow expires.

    public Heal(BulletHell bulletHell) {
        mainGame = bulletHell;
        cooldown = MAX_COOLDOWN;

        shape = new GraphicsGroup();
        border = new Rectangle(0, 0, SIZE, SIZE);
        remainingText = new GraphicsText("", 0, 0);
        remainingText.setFontSize(20);

        shape.add(border);
        shape.add(remainingText, 6, 22);
    }

    /**
     * Creates a new rectangle with a different height to represent the fill value.
     * Fills the rectangle with different shades of green depending on the cooldown. 
     * 
     * @param remainingCD The remaining cooldown to help calculate the height of the rectangle.
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
            (int) (255 * cooldownRatio)
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
     * Indicates that the cooldown is over with the letter 'E'.
     */
    private void endCooldown() {
        remainingText.setText("E");
        shape.add(remainingText);
    }

    /**
     * Returns true if slow is on cooldown.
     */
    private boolean onCooldown() {
        return (cooldown > 0);
    }
    
    /**
     * Activate the slow, reducing the speed of all bullets on the screen.
     */
    public void activate() {
        if (!onCooldown() && !inEffect()) {
            Player player = mainGame.getPlayer();
            energy.setFillColor(Color.WHITE);
            remainingHeal = MAX_DURATION;
            player.startHealing();
        }
    }

    /**
     * Returns true if slow is in effect.
     */
    private boolean inEffect() {
        return (remainingHeal > 0);
    }

    /**
     * Reduces the remaining time of the heal effect.
     * Ends the effect if the duration has expired, or the healing is interrupted.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceDuration(double dt) {
        if (inEffect()) {
            remainingHeal -= dt;
            remainingText.setText(new DecimalFormat("#").format(remainingHeal)); // Truncate all decimal points.
            shape.add(remainingText);
            if (!inEffect()) {
                deactivate();
            }
        }
    }

    /**
     * If the heal finishes successfully and the player has less than 3 lives, heals them. 
     * Puts the power on cooldown regardless of its success.
     */
    private void deactivate() {
        HeartManager heartManager = mainGame.getHeartManager();
        Player player = mainGame.getPlayer();
        if (!inEffect() && heartManager.heartsLeft() < 3) {
            heartManager.generateHearts(1);
        }
        player.endHealing();
        cooldown = MAX_COOLDOWN;
        remainingText.setText("");
        remainingHeal = 0;
    }

    /**
     * Returns the shape of the power.
     */
    public GraphicsGroup getShape() {
        return shape;
    }

    /**
     * Returns the key used to activate this power, which is E.
     */
    public Key getKey() {
        return Key.E;
    }
}
