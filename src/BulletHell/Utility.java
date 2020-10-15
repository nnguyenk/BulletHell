package BulletHell;

import java.util.Random;

import edu.macalester.graphics.CanvasWindow;

public class Utility {
    private static Random rand = new Random();

    public static int randomX(CanvasWindow canvas){
        int x;
        do {
           x = randomInt(100, canvas.getWidth() - 100);
        }
        while (x <= canvas.getCenter().getX() + 100 && x >= canvas.getCenter().getX() - 100);
        return x;
    }

    public static int randomY(CanvasWindow canvas){
        int y;
        do {
           y = randomInt(100, canvas.getHeight() - 100);
        }
        while (y <= canvas.getCenter().getY() + 100 && y >= canvas.getCenter().getY() - 100);
        return y;
    }


    public static int randomInt(int min, int max) {
        return rand.nextInt(max - min) + min;
    }
}
