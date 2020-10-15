package BulletHell.BulletTypes;

import edu.macalester.graphics.*;

import java.awt.Color;
import java.util.*;

import BulletHell.Bullets;


public class PlayerBullet implements Bullets {
    private Ellipse shape;
    private static final double RADIUS = 40;
    private double xCenter, yCenter, xSpeed, ySpeed;
    // Reference points, slightly outside of the bullet
    private Point top, left, bottom, right;
    private CanvasWindow canvas;
    

    public PlayerBullet(CanvasWindow canvas, double initialAngle, double initialSpeed) {
        shape = new Ellipse(150, 275, RADIUS, RADIUS);
        shape.setFillColor(Color.BLUE);
        shape.setStrokeColor(null);
        canvas.add(shape);

        xCenter = shape.getCenter().getX();
        yCenter = shape.getCenter().getY();
        setPoints();

        double angleToRadians = Math.toRadians(initialAngle);
        xSpeed = Math.cos(angleToRadians) * initialSpeed;
        ySpeed = -Math.sin(angleToRadians) * initialSpeed;

        this.canvas = canvas;
    }

    /*
     * Gets the shape of the bullet
     */
    public Ellipse getShape() {
        return shape;
    }

    private void setCenter(double x, double y) {
        shape.setCenter(x, y);
    }

    private double getCenterX() {
        return xCenter;
    }

    private double getCenterY() {
        return yCenter;
    }

    /**
     * Check if the ball collides with a brick, the paddle, or the walls, and 
     * move the ball to its next position.
     */
    public void updatePosition() {
        setPoints();
        collideWalls();
        xCenter += xSpeed;
        yCenter += ySpeed;
        setCenter(xCenter, yCenter);
    }

    /**
     * Set the reference points based on the current coordinates of the ball.
     */
    private void setPoints() {
        top = new Point(getCenterX(), getCenterY() - RADIUS - 0.1);
        left = new Point(getCenterX() - RADIUS - 0.1, getCenterY());
        bottom = new Point(getCenterX(), getCenterY() + RADIUS + 0.1);
        right = new Point(getCenterX() + RADIUS + 0.1, getCenterY());
    }

    /**
     * Changes the direction if the ball collides with a horizontal surface.
     */
    private void deflectHorizontal() {
        ySpeed = -ySpeed;
    }

    /**
     * Changes the direction if the ball collides with a vertical surface.
     */
    private void deflectVertical() {
        xSpeed = -xSpeed;
    }

    /**
     * Prevents the ball from getting out of the side walls and the ceiling. 
     */
    private void collideWalls() {
        if ((top.getY() <= 0) || bottom.getY() >= canvas.getHeight()) {
            deflectHorizontal();
        }
        if ((left.getX() <= 0) || (right.getX() >= canvas.getWidth())) {
            deflectVertical();
        }
    }

    /*
     * Returns a GraphicsObject if the bullet hits something 
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
     * Removes any other bullet if this bullet hits something 
     */
    public void bulletHitsBullet(Bullets bullet) {
        if (bullet.hit().contains(shape)) {
                canvas.remove(bullet.getShape());
        }
    }
}
