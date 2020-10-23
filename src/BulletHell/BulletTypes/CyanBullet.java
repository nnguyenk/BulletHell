package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;

import BulletHell.Bullet;

/**
 * A cyan bullet that freezes the player and prevents them from moving.
 * Who needs a slow when you can just hit them with a deep freeze?
 */
public class CyanBullet extends Bullet {
    public CyanBullet(CanvasWindow canvas) {
        super(canvas, Color.CYAN);
    }

    @Override
    public BulletType getType() {
        return BulletType.CYAN;
    }
}
