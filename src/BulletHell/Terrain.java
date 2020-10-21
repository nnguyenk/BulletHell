package BulletHell;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

public class Terrain {
    private CanvasWindow canvas;
    private List<Rectangle> allTerrain = new ArrayList<>();

    public Terrain(CanvasWindow canvas){
        this.canvas = canvas;
    }


    /**
     * Spawns a number of terrain objects onto the screen corresponding to the integer parameter
     * @param numberOfTerrainObjects
     */
    public void SummonTerrain(int numberOfTerrainObjects) {
        for (int i = 0; i < numberOfTerrainObjects; i++) {
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
     * @return a list of all the terrain objects
     */
    public List<Rectangle> getTerrain() {
        return allTerrain;
    }

    /**
     * @return a vertical rectangle placed randomly on the screen
     */
    private Rectangle verticalRectangle(){
        Rectangle rectangle = new Rectangle(Utility.randomX(canvas), Utility.randomY(canvas), 90, 30);
        rectangle.setFillColor(Color.BLACK);
        rectangle.setStrokeColor(null);
        canvas.add(rectangle);
        allTerrain.add(rectangle);
        return rectangle;
    }

    /**
     * @return a horizontal rectangle placed randomly on the screen
     */
    private Rectangle horizontalRectangle(){
        Rectangle rectangle = new Rectangle(Utility.randomX(canvas), Utility.randomY(canvas), 30, 90);
        rectangle.setFillColor(Color.BLACK);
        rectangle.setStrokeColor(null);
        canvas.add(rectangle);
        allTerrain.add(rectangle);
        return rectangle;
    }
}
