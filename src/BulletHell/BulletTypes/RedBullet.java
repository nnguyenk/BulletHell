package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;

import BulletHell.Bullets;

public class RedBullet extends Bullets {
    public RedBullet(CanvasWindow canvas, double initialSpeed) {
        super(canvas, initialSpeed, Color.RED);
    }
}
