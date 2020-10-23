package BulletHell.Powerups;

import java.awt.Color;
import java.text.DecimalFormat;

import BulletHell.BulletHell;
import BulletHell.Player;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.events.Key;

/**
 * A powerup that can be activated by pressing W.
 * Allows the player to erase any incoming bullets without taking damage.
 */
public class Eraser implements Powerups {
    public static final double BOX_SIZE = 30;
    public static final double MAX_COOLDOWN = 10;
    public static final double MAX_DURATION = 6;

    private double remainingEraser;
    private double cooldown;
    private BulletHell mainGame;

    private GraphicsGroup shape;
    private Rectangle border;
    private Rectangle energy;
    private GraphicsText remainingText;

    public Eraser(BulletHell bulletHell) {
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
     * Fills the rectangle with different shades of yellow depending on the cooldown. 
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
            255,
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
     * Indicates that the cooldown is over with the letter 'W'.
     */
    private void endCooldown() {
        remainingText.setText("W");
        shape.add(remainingText);
    }

    /**
     * Returns true if eraser is on cooldown.
     */
    private boolean onCooldown() {
        return (cooldown > 0);
    }

    /**
     * Activate the eraserl.
     */
    public void activate() {
        if (!onCooldown() && !inEffect()) {
            Player player = mainGame.getPlayer();
            energy.setFillColor(Color.WHITE);
            remainingEraser = MAX_DURATION;
            player.startErasing();
        }
    }

    /**
     * Returns true if eraser is in effect.
     */
    private boolean inEffect() {
        return (remainingEraser > 0);
    }

    /**
     * Reduces the remaining time of the eraser effect.
     * Ends the effect if the duration has expired.
     * 
     * @param dt The number of seconds that will be deducted from the remaining immunity.
     */
    public void reduceDuration(double dt) {
        if (inEffect()) {
            remainingEraser -= dt;
            remainingText.setText(new DecimalFormat("#").format(remainingEraser)); // Truncate all decimal points.
            shape.add(remainingText);
            if (!inEffect()) {
                deactivate();
            }
        }
    }

    /**
     * Makes the player vulnerable again, and puts the power on cooldown.
     */
    private void deactivate() {
        Player player = mainGame.getPlayer();
        player.endErasing();
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
        return Key.W;
    }
}
