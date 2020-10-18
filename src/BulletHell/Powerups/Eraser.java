package BulletHell.Powerups;

import BulletHell.Player;
import BulletHell.BulletHell;

public class Eraser implements Powerups {
    public static final double MAX_DURATION = 5; // The maximum number of seconds the power is active.

    private double remainingEraser;
    private BulletHell mainGame;

    public Eraser(BulletHell bulletHell) {
        mainGame = bulletHell;
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
}
