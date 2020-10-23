package BulletHell;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.events.Key;

/**
 * A class that represents the player of Bullet Hell.
 * Primarily responsible for checking the player's immunity/active powerups.
 */
public class Player {
    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 80;
    public static final int HITBOX_RADIUS = 45; 
    public static final int PLAYER_SPEED = 4;
    public static final double MAX_IMMUNITY = 2;

    private CanvasWindow canvas;
    private Ellipse playerHitbox;
    private double leftX, topY;
    private double dx, dy;
    private double remainingImmunity;

    private boolean frozen;
    private boolean erasing;
    private boolean healing;


    public Player(CanvasWindow canvas) {
        this.canvas = canvas;
        playerHitbox = new Ellipse(
            canvas.getWidth() / 2 - HITBOX_RADIUS, 
            canvas.getHeight() / 2- HITBOX_RADIUS, 
            HITBOX_RADIUS,
            HITBOX_RADIUS);
        playerHitbox.setFilled(false);
        playerHitbox.setStroked(false);
        leftX = playerHitbox.getX();
        topY = playerHitbox.getY();
    }

    /**
     * Makes the player move forward in its current direction if it is not frozen.
     */
    public void move() {
        if (!frozen) {
            double newX = playerHitbox.getX() + dx;
            double newY = playerHitbox.getY() + dy;
            if (newX >= 0 && newX <= canvas.getWidth() - PLAYER_WIDTH
                && newY >= 40 && newY <= canvas.getHeight() - PLAYER_HEIGHT) {
                leftX = newX;
                topY = newY;
                playerHitbox.setPosition(leftX, topY);
            }
        }
    }

    /**
     * Stops all the movements of the player.
     */
    public void stop() {
        dx = 0;
        dy = 0;
    }

    /**
     * Turns the player in the same diraction as the key.
     * 
     * @param key The input direction key.
     */
    public void turn(Key key) {
        if (key == Key.LEFT_ARROW) {
            turnLeft();
        }
        if (key == Key.RIGHT_ARROW) {
            turnRight();
        }
        if (key == Key.UP_ARROW) {
            turnUp();
        }
        if (key == Key.DOWN_ARROW) {
            turnDown();
        }
    }

    /**
     * Makes the player start moving left.
     */
    private void turnLeft() {
        dx = -PLAYER_SPEED;
        dy = 0;
    }

    /**
     * Makes the player start moving right.
     */
    private void turnRight() {
        dx = PLAYER_SPEED;
        dy = 0;
    }

    /**
     * Makes the player start moving up.
     */
    private void turnUp() {
        dx = 0;
        dy = -PLAYER_SPEED;
    }

    /**
     * Makes the player start moving up.
     */
    private void turnDown() {
        dx = 0;
        dy = PLAYER_SPEED;
    }

    /**
     * Gets the shape of the player.
     */
    public Ellipse getPlayerHitbox() {
        return playerHitbox;
    }

    /**
     * Gets the x-center of the player.
     */
    public double getCenterX() {
        return playerHitbox.getCenter().getX();
    }

    /**
     * Gets the y-center of the player.
     */
    public double getCenterY() {
        return playerHitbox.getCenter().getY();
    }

    /**
     * Changes the color of the player.
     */
    private void changeColor(Color color) {
        playerHitbox.setFillColor(color);
    }

    /**
     * Starts the immunity timer of the player. 
     * The frames of immunity are indicated by the purple color.
     */
    public void startImmunity() {
        remainingImmunity = MAX_IMMUNITY;
        changeColor(Color.MAGENTA);
    }

    /**
     * Returns true if the player is still immune, 
     * and calculates the remaining time.
     */
    public boolean isImmune() {
        return (remainingImmunity > 0);
    }

    /**
     * Reduces the remaining time of the immunity. 
     * Ends immunity if the player is no longer immune.
     * 
     * @param dt The number of seconds that will be deducted from the remaining immunity.
     */
    public void reduceImmunity(double dt) {
        if (isImmune()) {
            remainingImmunity -= dt;
            if (!isImmune()) {
                endImmunity();
                canvas.setBackground(Color.WHITE);
            }
        }
    }
    
    /**
     * Changes the player's color back to blue to indicate the end of immunity (not if eraser is active).
     * Thaws out the player if they are frozen.
     */
    private void endImmunity() {
        thaw();
        if (!isErasing() && !isHealing()) {
            playerHitbox.setFilled(false);
        }
    }

    /**
     * Freezes the player.
     */
    public void freeze() {
        frozen = true;
        changeColor(Color.BLUE);
    }

    /**
     * Thaws the player.
     */
    public void thaw() {
        if (frozen) {
            frozen = false;
            playerHitbox.setFilled(false);
        }
    }

    /**
     * Returns true if the player is currently frozen.
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Changes the player's color to yellow and begins the eraser state.
     */
    public void startErasing() {
        erasing = true;
        changeColor(Color.YELLOW);
    }

    /**
     * Checks if the player is still in the eraser state.
     */
    public boolean isErasing() {
        return erasing;
    }

    /**
     * Ends the eraser state and returns the player's color back to blue.
     */
    public void endErasing() {
        erasing = false;
        if (!isHealing()) {
            playerHitbox.setFilled(false);
        }
    }

    /**
     * Changes the player's color to yellow and begins the eraser state.
     */
    public void startHealing() {
        healing = true;
        changeColor(Color.GREEN);
    }

    /**
     * Checks if the player is still in the eraser state.
     */
    public boolean isHealing() {
        return healing;
    }

    /**
     * Ends the eraser state and returns the player's color back to blue.
     */
    public void endHealing() {
        healing = false;
        if (!isErasing() && !isImmune()) {
            playerHitbox.setFilled(false);
        }
    }
}
