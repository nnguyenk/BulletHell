package BulletHell.Powerups;

import BulletHell.Player;

public class Eraser {
    public static final double MAX_DURATION = 5; // The maximum number of seconds the power is active.

    private double remainingEraser;

    /**
     * Activate the eraser, allowing the player to absorb bullets at will.
     */
    public void startErasing(Player player) {
        remainingEraser = MAX_DURATION;
        player.startErasing();
    }

     /**
     * Returns true if eraser is in effect.
     */
    public boolean isErasing() {
        return (remainingEraser > 0);
    }

    /**
     * Reduces the remaining time of the eraser effect.
     * Ends the effect if the duration has expired.
     * 
     * @param dt The number of seconds that will be deducted from the remaining immunity.
     */
    public void reduceErasing(double dt, Player player) {
        remainingEraser -= dt;
        if (!isErasing()) {
            player.endErasing();
        }
    }
}
