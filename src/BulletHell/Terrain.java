package BulletHell;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

public class Terrain {
    private CanvasWindow canvas;
    private Rectangle rectangle1 = new Rectangle(700, 720, 90, 30);
    private Rectangle rectangle2 = new Rectangle(120, 500, 30, 80);
    private List<Rectangle> allTerrain = new ArrayList<>();

    public Terrain(CanvasWindow canvas){
        this.canvas = canvas;
    }

    public void SummonTerrain() {
        rectangle1.setCenter(Utility.randomInt(100, 700), Utility.randomInt(100, 700));
        rectangle1.setFillColor(Color.ORANGE);
        rectangle1.setStrokeColor(null);

        rectangle2.setCenter(Utility.randomInt(100, 700), Utility.randomInt(100, 700));
        rectangle2.setFillColor(Color.ORANGE);
        rectangle2.setStrokeColor(null);
        
        canvas.add(rectangle1);
        canvas.add(rectangle2);

        allTerrain.add(rectangle1);
        allTerrain.add(rectangle2);
    }

    public List<Rectangle> getTerrain() {
        return allTerrain;
    }
}
