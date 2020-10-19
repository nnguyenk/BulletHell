package BulletHell;

import java.util.HashMap;
import java.util.Map;

import BulletHell.Powerups.*;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;

public class PowerManager {
    public static final int GAP = 10; // The gap between the boxes.

    private Map<Key, Powerups> allPowers = new HashMap<>();
    private CanvasWindow canvas;

    public PowerManager(CanvasWindow canvas, BulletHell bulletHell) {
        this.canvas = canvas;

        Slow slow = new Slow(bulletHell);
        allPowers.put(Key.Q, slow);
        // allPowers.add(new Eraser(bulletHell));
    }

    /**
     * Adds all shapes of the powerups to the canvas. 
     */
    public void createPowers() {
        for (Powerups powerups : allPowers.values()) {
            canvas.add(powerups.getShape(), 20, 5);
        }
    }

    /**
     * Reduces the cooldowns of all powerups.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceCooldown(double dt) {
        for (Powerups powerups : allPowers.values()) {
            powerups.reduceCooldown(dt);
        }
    }

    /**
     * Reduces the durations of all active powerups.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceDuration(double dt) {
        for (Powerups powerups : allPowers.values()) {
            powerups.reduceDuration(dt);
        }
    }

    /**
     * Activates the power at the point if the power is off cooldown and not active.
     */
    public void activatePower(Key key) {
        if (allPowers.keySet().contains(key)) {
            allPowers.get(key).activate();
        }
    }
}