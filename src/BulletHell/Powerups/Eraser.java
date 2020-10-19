package BulletHell.Powerups;

import BulletHell.Player;
import edu.macalester.graphics.GraphicsGroup;
import BulletHell.BulletHell;

public class Eraser implements Powerups {
    public static final double MAX_DURATION = 5; // The maximum number of seconds the power is active.

    private double remainingEraser;
    private double remainingCD;
    private BulletHell mainGame;

    private GraphicsGroup shape;

    public Eraser(BulletHell bulletHell) {
        mainGame = bulletHell;
    }

    /**
     * Reduces the cooldown of this power if while it is not in effect.
     * Once the CD is over, changes the color of the box to indicate that the power is ready.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceCooldown(double dt) {

    }

    /**
     * Returns true if eraser is on cooldown.
     */
    public boolean onCooldown() {
        return (remainingCD > 0);
    }

    /**
     * Activate the eraser, allowing the player to absorb bullets at will.
     */
    public void activate() {
        Player player = mainGame.getPlayer();
        remainingEraser = MAX_DURATION;
        player.startErasing();
    }

     /**
     * Returns true if eraser is in effect.
     */
    public boolean inEffect() {
        return (remainingEraser > 0);
    }

    /**
     * Reduces the remaining time of the eraser effect.
     * Ends the effect if the duration has expired.
     * 
     * @param dt The number of seconds that will be deducted from the remaining immunity.
     */
    public void reduceDuration(double dt) {
        Player player = mainGame.getPlayer();
        remainingEraser -= dt;
        if (!inEffect()) {
            player.endErasing();
        }
    }

    /**
     * Returns the shape of the power.
     */
    public GraphicsGroup getShape() {
        return shape;
    }
}
