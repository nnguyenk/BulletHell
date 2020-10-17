package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;

import BulletHell.Bullet;

public class BlueBullet extends Bullet {
    public BlueBullet(CanvasWindow canvas) {
        super(canvas, Color.CYAN);
    }

    @Override
    public String getType() {
        return "Cyan";
    }
}
