package BulletHell;

import java.util.List;

import java.awt.Color;

import edu.macalester.graphics.*;

public abstract class Bullet {
    public static final double RADIUS = 20;
    public static final int SPEED = 3;
    
    private int maxLives = 4; 

    private Point top, left, bottom, right;  // Reference points, slightly outside of the bullet
    private CanvasWindow canvas;
    private Color color;
    private Ellipse shape;
    private double xCenter, yCenter, xSpeed, ySpeed;
    private int currentLife;

    public Bullet(CanvasWindow canvas, Color color) {
        shape = new Ellipse(Utility.randomX(canvas), Utility.randomY(canvas), RADIUS, RADIUS);
        shape.setFillColor(color);
        shape.setStroked(false);

        xCenter = shape.getCenter().getX();
        yCenter = shape.getCenter().getY();
        setPoints();

        double angleToRadians = Math.toRadians(Utility.randomInt(0, 360));
        xSpeed = Math.cos(angleToRadians) * SPEED;
        ySpeed = -Math.sin(angleToRadians) * SPEED;

        this.canvas = canvas;
        this.color = color;

        currentLife = maxLives;
    }

    /*
     * Gets the shape of the bullet.
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
     * Returns a string for the type of this bullet.
     */
    public abstract String getType();

    /**
     * Check if the ball collides with a brick, the paddle, or the walls, and 
     * move the ball to its next position.
     */
    public void updatePosition(Terrain terrain) {
        setPoints();
        collideWalls();
        collideTerrain(terrain);
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
     * Prevents the bullet from getting out of the side walls and the ceiling.
     * Subtracts one life from it every time it bounces. 
     */
    private void collideWalls() {
        if ((top.getY() <= 31) || bottom.getY() >= canvas.getHeight()) {
            loseLife();
            if (isAlive()) {
                deflectHorizontal();
                ySpeed *= 1.75;
            }
        }
        
        if ((left.getX() <= 0) || (right.getX() >= canvas.getWidth())) {
            loseLife();
            if (isAlive()) {
                deflectVertical();
                xSpeed *= 1.75;
            }
        }
    }

    /**
     * Returns true if the bullet hits the player.
     */
    public boolean collidePlayer(Player player) {
        setPoints();
        for (Point point : List.of(top, bottom, left, right)) {
            if (canvas.getElementAt(point) == player.getPlayerShape()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Upon collision with terrain, bullets bounce without losing a life or gaining speed.
     * 
     * @param terrain
     */
    private void collideTerrain(Terrain terrain) {
        setPoints();
        for (Point point : List.of(top, bottom, left, right)) {
            if (terrain.getTerrain().contains(canvas.getElementAt(point))) {
                
                if (point == top || point == bottom) {
                    deflectHorizontal();
                }

                else {
                    deflectVertical();
                }
            }
        }
    }

    /**
     * Returns true if the bullet is still alive.
     */
    public boolean isAlive() {
        return (currentLife > 0);
    }

    /**
     * Subtracts the current number of lives by 1.
     * Indicates the remaining lives with a change in color.
     */
    private void loseLife() {
        currentLife--;
        if (isAlive()) {
            shape.setFillColor(new Color(
                color.getRed() * currentLife / maxLives,
                color.getGreen() * currentLife / maxLives, 
                color.getBlue() * currentLife / maxLives));
        }
    }

    public void reduceSpeed() {
        xSpeed *= .5;
        ySpeed *= .5;
    }

    public void restoreSpeed() {
        xSpeed *= 2;
        ySpeed *= 2;
    }
}

// Comb through code for dead logic/etc