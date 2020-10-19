package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;

import BulletHell.Bullet;

public class YellowBullet extends Bullet {
    public YellowBullet(CanvasWindow canvas) {
        super(canvas, Color.YELLOW);
    }

    @Override
    public String getType() {
        return "Yellow";
    }
}