package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;

import BulletHell.Bullet;

/**
 * A yellow bullet that prevents the player from gaining immunity.
 * It sure stings when your powers got erased.  
 */
public class YellowBullet extends Bullet {
    public YellowBullet(CanvasWindow canvas) {
        super(canvas, Color.YELLOW);
    }

    @Override
    public BulletType getType() {
        return BulletType.YELLOW;
    }
}