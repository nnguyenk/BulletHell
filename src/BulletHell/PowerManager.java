package BulletHell;

import java.util.ArrayList;
import java.util.List;

import BulletHell.Powerups.*;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;

/**
 * A class responsible for adding the power boxes to the canvas, and binding the powers with their respective keys.
 */
public class PowerManager {
    public static final int GAP = 50;

    private List<Powerups> allPowerups = new ArrayList<>();
    private List<Key> activateKeys = new ArrayList<>();
    private CanvasWindow canvas;

    /**
     * Creates a manager for every game window. 
     */
    public PowerManager(CanvasWindow canvas, BulletHell bulletHell) {
        this.canvas = canvas;

        Slow slow = new Slow(bulletHell);
        allPowerups.add(slow);
        activateKeys.add(slow.getKey());

        Eraser eraser = new Eraser(bulletHell);
        allPowerups.add(eraser);
        activateKeys.add(eraser.getKey());

        Heal heal = new Heal(bulletHell);
        allPowerups.add(heal);
        activateKeys.add(heal.getKey());

    }

    /**
     * Adds all shapes of the powerups to the canvas. 
     */
    public void generatePowerups() {
        double coorX = 20;
        for (Powerups powerups : allPowerups) {
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
        for (Powerups powerups : allPowerups) {
            powerups.reduceCooldown(dt);
        }
        
    }

    /**
     * Reduces the durations of all active powerups.
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceDuration(double dt) {
        for (Powerups powerups : allPowerups) {
            powerups.reduceDuration(dt);
        }
    }

    /**
     * Activates the power at the point if the power is off cooldown and not active.
     * 
     * @param key The input key.
     */
    public void activatePowerups(Key key) {
        if (activateKeys.contains(key)) {
            int index = activateKeys.indexOf(key);
            allPowerups.get(index).activate();
        }
    }
}
