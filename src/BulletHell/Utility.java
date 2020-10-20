package BulletHell;

import java.util.Random;

import edu.macalester.graphics.CanvasWindow;

public class Utility {
    private static Random rand = new Random();

    public static int randomX(CanvasWindow canvas) {
        return randomInt(50, canvas.getWidth() - 50);
    }

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
