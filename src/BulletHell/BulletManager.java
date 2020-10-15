package BulletHell;

import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;

public class BulletManager {
    CanvasWindow canvas;
    List<Bullets> bullets = new ArrayList<>();

    public BulletManager(CanvasWindow canvas) {
        this.canvas = canvas;
    }

    public void spawnBullets(int bullets){
        for (int i = 0; i < bullets; i++) {
            // spawm nullet at random interval for i
        }
    }
}
