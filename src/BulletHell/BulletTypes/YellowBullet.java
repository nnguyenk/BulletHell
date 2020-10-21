package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;

import BulletHell.Bullet;

public class YellowBullet extends Bullet {
    public YellowBullet(CanvasWindow canvas) {
        super(canvas, Color.YELLOW);
    }

    @Override
    public BulletType getType() {
        return BulletType.YELLOW;
    }
}