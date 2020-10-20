package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;

import BulletHell.Bullet;

public class GreenBullet extends Bullet {
    public GreenBullet(CanvasWindow canvas) {
        super(canvas, Color.GREEN);
    }

    @Override
    public String getType() {
        return "Green";
    }
}