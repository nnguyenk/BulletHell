package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;

import BulletHell.Bullets;

public class BlueBullet extends Bullets {
    public BlueBullet(CanvasWindow canvas, double initialSpeed) {
        super(canvas, initialSpeed, Color.CYAN);
    }
}
