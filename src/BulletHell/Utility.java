package BulletHell;

import java.util.Random;

import edu.macalester.graphics.CanvasWindow;

/**
 * A helper class that provides some random generators.
 */
public class Utility {
    private static Random rand = new Random();

    /**
     * @return A random x-coordinate within the parameter canvas.
     */
    public static int randomX(CanvasWindow canvas) {
        return randomInt(50, canvas.getWidth() - 50);
    }

    /**
     * @return A random x-coordinate within the parameter canvas.
     */
    public static int randomY(CanvasWindow canvas){
        return randomInt(50, canvas.getHeight() - 50);
    }

    /**
     * Helper method to get a random integer. min <= x <= max
     */
    public static int randomInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
}
