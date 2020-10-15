package BulletHell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BulletHell.BulletTypes.*;

import edu.macalester.graphics.CanvasWindow;

public class BulletManager {
    private CanvasWindow canvas;
    private List<Bullets> bullets = new ArrayList<>();
    private Map<Integer, Bullets> bulletTypes = new HashMap<>();

    public BulletManager(CanvasWindow canvas) {
        this.canvas = canvas;
        bulletTypes.put(1, new RedBullet())
    }

    public void spawnBullets(int bullets) {
        for (int i = 0; i < bullets; i++) {
            // spawm nullet at random interval for i
        }
    }
}
