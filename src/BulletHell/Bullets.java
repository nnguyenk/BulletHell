package BulletHell;

import java.util.List;

import java.awt.Color;

import edu.macalester.graphics.*;

public abstract class Bullets {
    public static final double RADIUS = 20;

    protected Ellipse shape;
    protected double xCenter, yCenter, xSpeed, ySpeed;
    protected Point top, left, bottom, right;  // Reference points, slightly outside of the bullet
    protected CanvasWindow canvas;

    public Bullets(CanvasWindow canvas, double initialSpeed, Color color) {
        shape = new Ellipse(Utility.randomX(canvas), Utility.randomY(canvas), RADIUS, RADIUS);
        shape.setFillColor(color);
        shape.setStroked(false);

        xCenter = shape.getCenter().getX();
        yCenter = shape.getCenter().getY();
        setPoints();

        double angleToRadians = Math.toRadians(Utility.randomInt(0, 360));
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

    protected void setCenter(double x, double y) {
        shape.setCenter(x, y);
    }

    protected double getCenterX() {
        return xCenter;
    }

    protected double getCenterY() {
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
     * Set the reference points based on the current coordinates of the bullet.
     */
    private void setPoints() {
        top = new Point(getCenterX(), getCenterY() - RADIUS - 0.001);
        left = new Point(getCenterX() - RADIUS - 0.001, getCenterY());
        bottom = new Point(getCenterX(), getCenterY() + RADIUS + 0.001);
        right = new Point(getCenterX() + RADIUS + 0.001, getCenterY());
    }

    /**
     * Changes the direction if the bullet collides with a horizontal surface.
     */
    private void deflectHorizontal() {
        ySpeed = -ySpeed;
    }

    /**
     * Changes the direction if the bullet collides with a vertical surface.
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
     * Returns true if the bullet hits it, and bounces the bullet
     */
    public boolean collidePlayer(Player player) {
        setPoints();
        for (Point point : List.of(top, bottom, left, right)) {
            if (canvas.getElementAt(point) == player.getPlayerShape()) {
                if (point == top || point == bottom) {
                    deflectHorizontal();
                }
                else {
                    deflectVertical();
                }
            return true;
            }
        }
        return false;
    }
}


// GraphicsGroup to contain all the bullet objects
// BulletManager
// Implement lives
// Implement shoot?