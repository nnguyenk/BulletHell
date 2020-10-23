package BulletHell;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

/**
 * A group of rectangles that allow the player to pass through, but deflect any incoming bullets.
 */
public class Terrain {
    private CanvasWindow canvas;
    private List<Rectangle> allTerrain = new ArrayList<>();

    public Terrain(CanvasWindow canvas){
        this.canvas = canvas;
    }

    /**
     * Spawns a number of terrain objects onto the screen.
     * .
     * @param terrainNum The number of rectangles to be generated.
     */
    public void generateTerrain(int terrainNum) {
        for (int i = 0; i < terrainNum; i++) {
            int j = Utility.randomInt(1, 2);

            switch (j) {
                case 1:
                    horizontalRectangle();
                    break;
                case 2:
                    verticalRectangle();
                    break;
            }   
        }    
    }

    /**
     * @return a list of all the terrain objects.
     */
    public List<Rectangle> getTerrain() {
        return allTerrain;
    }

    /**
     * Clears all terrain rectangles from the screen and from the Terrain list.
     */
    public void clearList() {
        for (Rectangle rect : allTerrain){
            canvas.remove(rect);
        }
        allTerrain.clear();
    }

    /**
     * Randomly places a vertical rectangle on the screen.
     */
    private void verticalRectangle() {
        Rectangle rectangle = new Rectangle(Utility.randomX(canvas), Utility.randomY(canvas), 90, 30);
        rectangle.setFillColor(Color.BLACK);
        rectangle.setStrokeColor(null);
        canvas.add(rectangle);
        allTerrain.add(rectangle);
    }

    /**
     * Randomly places a horizontal rectangle on the screen.
     */
    private void horizontalRectangle(){
        Rectangle rectangle = new Rectangle(Utility.randomX(canvas), Utility.randomY(canvas), 30, 90);
        rectangle.setFillColor(Color.BLACK);
        rectangle.setStrokeColor(null);
        canvas.add(rectangle);
        allTerrain.add(rectangle);
    }
}
