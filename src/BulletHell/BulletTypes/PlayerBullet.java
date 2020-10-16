package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;
import java.util.*;

import BulletHell.Bullet;

public class PlayerBullet extends Bullet {

    public PlayerBullet(CanvasWindow canvas, double initialAngle) {
        super(canvas, Color.BLUE);
    }

    /**
     * Check if the ball collides with a brick, the paddle, or the walls, and 
     * move the ball to its next position.
     */
    public void updatePosition(List<Bullet> bullets) {
        setPoints();
        collideWalls();
        hitsBullet(bullets);
        xCenter += xSpeed;
        yCenter += ySpeed;
        setCenter(xCenter, yCenter);
    }

    /**
     * Prevents the ball from getting out of the side walls and the ceiling. 
     */
    private void collideWalls() {
        if ((top.getY() <= 0) || bottom.getY() >= canvas.getHeight()) {
            canvas.remove(shape);
        }
        if ((left.getX() <= 0) || (right.getX() >= canvas.getWidth())) {
            canvas.remove(shape);
        }
    }

    /*
     * Returns a GraphicsObject if the bullet hits something. 
     */
    public List<GraphicsObject> hit() {
        List<GraphicsObject> collidedElements = new ArrayList<>();
        for (Point point : List.of(top, bottom, left, right)) {
            if (canvas.getElementAt(point) != null) {
                collidedElements.add(canvas.getElementAt(point));
            }
        }
        return Collections.unmodifiableList(collidedElements);
    }

    /*
     * Removes true if this bullet hits any other bullets from the list and remove this bullet. 
     */
    private boolean hitsBullet(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            if (this.hit().contains(bullet.getShape())) {
                canvas.remove(shape);
                return true;
            }
        }
        return false;
    }
}
