package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;

import BulletHell.Bullet;

/**
 * A green bullet that keeps flying even after colliding with the player.
 * A smart bullet does not die a martyr. And it sure does not need healing to stay alive. 
 */
public class GreenBullet extends Bullet {
    public GreenBullet(CanvasWindow canvas) {
        super(canvas, Color.GREEN);
    }

    @Override
    public BulletType getType() {
        return BulletType.GREEN;
    }
}