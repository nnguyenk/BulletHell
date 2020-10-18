package BulletHell;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

public class Terrain {
    CanvasWindow canvas;
    Rectangle rectangle1 = new Rectangle(700, 720, 90, 30);
    Rectangle rectangle2 = new Rectangle(120, 500, 30, 80);

    public Terrain(CanvasWindow canvas){
        this.canvas = canvas;
    }

    public void SummonTerrain() {
        rectangle1.setFillColor(Color.ORANGE);
        rectangle1.setStrokeColor(null);

        rectangle2.setFillColor(Color.ORANGE);
        rectangle2.setStrokeColor(null);
        
        canvas.add(rectangle1);
        canvas.add(rectangle2);
    }

    public Rectangle getTerrain1(){
        return rectangle1;
    }

    public Rectangle getTerrain2(){
        return rectangle2;
    }

}
