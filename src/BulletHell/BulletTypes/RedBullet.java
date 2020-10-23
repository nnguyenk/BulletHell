package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;

import BulletHell.Bullet;

/**
 * An ordinary, boring, but not harmless, red bullet.
 */
public class RedBullet extends Bullet {
    public RedBullet(CanvasWindow canvas) {
        super(canvas, Color.RED);
    }

    @Override
    public BulletType getType() {
        return BulletType.RED;
    }
}
