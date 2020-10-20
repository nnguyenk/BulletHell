package BulletHell;

import java.util.ArrayList;
import java.util.List;

import BulletHell.Powerups.*;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;

public class PowerManager {
    public static final int GAP = 50; // The gap between the boxes + the width of the boxes.

    private List<Powerups> allPowers = new ArrayList<>();
    private List<Key> activateKeys = new ArrayList<>();
    private CanvasWindow canvas;

    public PowerManager(CanvasWindow canvas, BulletHell bulletHell) {
        this.canvas = canvas;

        Slow slow = new Slow(bulletHell);
        allPowers.add(slow);
        activateKeys.add(slow.getKey());

        Eraser eraser = new Eraser(bulletHell);
        allPowers.add(eraser);
        activateKeys.add(eraser.getKey());
    }

    /**
     * Adds all shapes of the powerups to the canvas. 
     */
    public void createPowers() {
        double coorX = 20;
        for (Powerups powerups : allPowers) {
            canvas.add(powerups.getShape(), coorX, 5);
            coorX += GAP; 
        }
    }

    /**
     * Reduces the cooldowns of all powerups.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceCooldown(double dt) {
        for (Powerups powerups : allPowers) {
            powerups.reduceCooldown(dt);
        }
    }

    /**
     * Reduces the durations of all active powerups.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceDuration(double dt) {
        for (Powerups powerups : allPowers) {
            powerups.reduceDuration(dt);
        }
    }

    /**
     * Activates the power at the point if the power is off cooldown and not active.
     */
    public void activatePower(Key key) {
        if (activateKeys.contains(key)) {
            int index = activateKeys.indexOf(key);
            allPowers.get(index).activate();
        }
    }
}