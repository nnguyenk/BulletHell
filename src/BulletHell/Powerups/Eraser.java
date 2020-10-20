package BulletHell.Powerups;

import java.awt.Color;
import java.text.DecimalFormat;

import BulletHell.BulletHell;
import BulletHell.Player;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.events.Key;

public class Eraser implements Powerups {
    public static final double SIZE = 30;
    public static final int MAX_COOLDOWN = 10; // The number of seconds that this power is on cooldown.
    public static final int MAX_DURATION = 5; // The maximum number of seconds the power is active.

    private double remainingEraser;
    private double remainingCD;
    private BulletHell mainGame;

    private GraphicsGroup shape;
    private Rectangle border;
    private Rectangle energy;
    private GraphicsText remainingText; // A text box that shows how many seconds left until the eraser expires.

    public Eraser(BulletHell bulletHell) {
        mainGame = bulletHell;
        remainingCD = MAX_COOLDOWN;

        shape = new GraphicsGroup();
        border = new Rectangle(0, 0, SIZE, SIZE);
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
        energy = new Rectangle(0, SIZE * cooldownRatio, SIZE, SIZE * (1 - cooldownRatio));
        energy.setStroked(false);
        energy.setFillColor(new Color(
            255,
            255,
            (int) (255 * cooldownRatio)
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
                remainingText.setText("W");
                shape.add(remainingText);
            }
        }
    }

    /**
     * Returns true if eraser is on cooldown.
     */
    private boolean onCooldown() {
        return (remainingCD > 0);
    }

    /**
     * Activate the eraser, allowing the player to absorb bullets at will.
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
            Player player = mainGame.getPlayer();
            remainingEraser -= dt;
            remainingText.setText(new DecimalFormat("#").format(remainingEraser)); // Truncate all decimal points.
            shape.add(remainingText);
            if (!inEffect()) {
                player.endErasing();
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
     * Returns the key used to activate this power, which is W.
     */
    public Key getKey() {
        return Key.W;
    }
}
