package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;

import BulletHell.Bullet;

public class CyanBullet extends Bullet {
    public CyanBullet(CanvasWindow canvas) {
        super(canvas, Color.CYAN);
    }

    @Override
    public String getType() {
        return "Cyan";
    }
}
