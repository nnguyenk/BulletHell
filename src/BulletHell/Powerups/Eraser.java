package BulletHell.Powerups;

import BulletHell.Player;

public class Eraser {
    private double remainingEraser;
    public static final double MAX_IMMUNITY = 7; // The maximum number of seconds the player is immuned.

    public void StartErasing() {
        remainingEraser = MAX_IMMUNITY;
    }

    /**
     * Returns true if the player is still immune, 
     * and calculates the remaining time.
     */
    public boolean isErasing(Player player) {
        if (remainingEraser <= 0) {
           return false;
        }
        return true;
    }

    /**
     * Reduces the remaining time of the immunity.
     * 
     * @param dt The number of seconds that will be deducted from the remaining immunity.
     */
    public void reduceErasing(double dt) {
        remainingEraser-= dt;
    }    
}