package BulletHell;

import java.util.List;

import BulletHell.BulletTypes.BulletType;

import java.awt.Color;

import edu.macalester.graphics.*;

/**
 * A bullet that can bounce between walls and terrains, but lose one life in the process.
 * Each bullet has a type, which can give it additional effects.
 */
public abstract class Bullet {
    public static final double RADIUS = 20;
    public static final int SPEED = 4;
    
    private int maxLives = 4; 

    private Point top, left, bottom, right;
    private CanvasWindow canvas;
    private Color color;
    private Ellipse shape;
    private double xCenter, yCenter, xSpeed, ySpeed;
    private int currentLives;

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

        currentLives = maxLives;
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
    public abstract BulletType getType();

    /**
     * Check if the bullet collides with walls or terrains. 
     * Moves the bullet to its next position.
     * 
     * @param terrain The terrain that can hinder the bullet's movement.
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
     * Reduces the life of the bullet when it collides with a horizontal surface.
     * Then, if it's still alive, change its direction and increases its speed.
     */
    private void deflectHorizontal() {
        loseLife();
        if (isAlive()) {
            ySpeed = -ySpeed;
            increaseSpeed(1.25);
        }
    }

    /**
     * Reduces the life of the bullet when it collides with a vertical surface.
     * Then, if it's still alive, change its direction and increases its speed.
     */
    private void deflectVertical() {
        loseLife();
        if (isAlive()) {
            xSpeed = -xSpeed;
            increaseSpeed(1.25);
        }
    }

    /**
     * Prevents the bullet from getting out of the side walls and the ceiling.
     * Subtracts one life from it every time it bounces. 
     */
    private void collideWalls() {
        if ((top.getY() <= 31) || bottom.getY() >= canvas.getHeight()) {
            deflectHorizontal();
        }
        
        if ((left.getX() <= 0) || (right.getX() >= canvas.getWidth())) {
            deflectVertical();
        }
    }

    /**
     * Returns true if the bullet hits the player.
     * 
     * @param player The player currently on the canvas.
     */
    public boolean collidePlayer(Player player) {
        double distance = Math.hypot(xCenter - player.getCenterX(), yCenter - player.getCenterY());
        return (distance < RADIUS + Player.HITBOX_RADIUS);
    }

    /**
     * Returns true if the bullet is close to the player (a 200 x 200 square around the player).
     * Useful to make sure that the player can't get hit instantly every round start.
     */
    public boolean closeToPlayer(Player player) {
        return (
            xCenter < player.getCenterX() + 200
            && xCenter > player.getCenterX() - 200
            && yCenter < player.getCenterY() + 200
            && yCenter > player.getCenterY() - 200);
    }

    /**
     * Returns true if the bullet collides with a terrain.
     * 
     * @param terrain The terrain that can hinder the bullet's movement.
     */
    public boolean collideTerrain(Terrain terrain) {
        for (Point point : List.of(top, bottom, left, right)) {
            if (terrain.getTerrain().contains(canvas.getElementAt(point))) {
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

    /**
     * Returns true if the bullet is still alive.
     */
    public boolean isAlive() {
        return (currentLives > 0);
    }

    /**
     * Subtracts the current number of lives by 1.
     * Indicates the remaining lives with a change in color.
     */
    private void loseLife() {
        currentLives--;
        if (isAlive()) {
            shape.setFillColor(new Color(
                color.getRed() * currentLives / maxLives,
                color.getGreen() * currentLives / maxLives, 
                color.getBlue() * currentLives / maxLives));
        }
    }

    /**
     * Reduces the speed of this bullet.
     * 
     * @param ratio The ratio between the changed speed and the old one.
     */
    public void reduceSpeed(double ratio) {
        xSpeed *= ratio;
        ySpeed *= ratio;
    }

    /**
     * Increases the speed of the bullet.
     * 
     * @param ratio The ratio between the changed speed and the old one.
     */
    public void increaseSpeed(double ratio) {
        xSpeed *= ratio;
        ySpeed *= ratio;
    }
}