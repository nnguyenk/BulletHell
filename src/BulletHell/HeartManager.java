package BulletHell;

import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;

/**
 * A class that adds or removes hearts (lives) for the game Bullet Hell.
 */
public class HeartManager {
    private CanvasWindow canvas;
    private List<Image> allHearts = new ArrayList<>();

    public HeartManager(CanvasWindow canvas) {
        this.canvas = canvas;
    }

    /**
     * Adds a number of heart(s) to the canvas. Does nothing if the parameter is 0.
     * 
     * @param heartNumber The number of hearts to be added. 
     */
    public void generateHearts(int heartNumber) {
        if (heartNumber <= 0) {
            return;
        }
        for (int i = 0; i < Math.min(3, heartNumber); i++) {
            Image heart = new Image("Heart-1.png");
            heart.setMaxHeight(80);
            heart.setMaxWidth(80);
            heart.setCenter((canvas.getWidth() - 30) - allHearts.size() * 50, 10);

            canvas.add(heart);
            allHearts.add(heart);
        }
    }

    /**
     * Removes the leftmost heart on the canvas.
     */
    public void removeHeart() {
        Image heartToRemove = allHearts.get(allHearts.size() - 1);
        canvas.remove(heartToRemove);
        allHearts.remove(heartToRemove);
    }

    /**
     * Returns the number of remaining hearts.
     */
    public int heartsLeft() {
        return allHearts.size();
    }
}
