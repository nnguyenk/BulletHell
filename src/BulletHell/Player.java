package BulletHell;

import java.awt.Color;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

public class Player {
    private static final double PLAYER_WIDTH = 40, PLAYER_HEIGHT = 50, PLAYER_SPEED = 200;

    private Rectangle playerShape;
    private CanvasWindow canvas;
    private double leftX;
    private double topY;


    public Player(CanvasWindow canvas){
        this.canvas = canvas;
        playerShape = new Rectangle(canvas.getWidth() / 2, canvas.getHeight() / 2, PLAYER_WIDTH, PLAYER_HEIGHT);
        playerShape.setFillColor(Color.BLUE);
        leftX = playerShape.getX();
        topY = playerShape.getY();
    }

    /**
     * Returns true if the bullet hits player.
     * @param bullets The list of bullets that will kill the player.
     */
    public boolean isHit(List<Bullet> bullets) {
        for (Bullet bullet : bullets)
            if (bullet.collidePlayer(this)) {
                return true;
            }
        return false;
    }
    

    /**
     * Moves the player left by a set amount when called.
     */
    public void moveLeft(double dt) {
        if (playerShape.getX() > 0) {
            leftX -= PLAYER_SPEED * dt;
            playerShape.setX(leftX);
        }
    }

    /**
     * Moves the player right by a set amount when called.
     */
    public void moveRight(double dt) {
        if (playerShape.getX() < canvas.getWidth() - PLAYER_WIDTH){
            leftX += PLAYER_SPEED * dt;
            playerShape.setX(leftX);
        }
    }

    /**
     * Moves the player up by a set amount when called.
     */
    public void moveUp(double dt) {
        if (playerShape.getY() > 0) {
            topY -= PLAYER_SPEED * dt;
            playerShape.setY(topY);
        }
    }

    /**
     * Moves the player down by a set amount when called.
     */
    public void moveDown(double dt) {
        if (playerShape.getY() < canvas.getHeight() - PLAYER_HEIGHT){
            topY += PLAYER_SPEED * dt;
            playerShape.setY(topY);
        }
    }

    /*
     * Gets the playerShape of the player.
     */
    public Rectangle getPlayerShape() {
        return playerShape;
    }
}