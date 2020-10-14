package BulletHell;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;

public class Character {
    private static final double CHARACTER_WIDTH = 40, CHARACTER_HEIGHT = 50, CHARACTER_SPEED = 200;
    private Rectangle characterShape;
    private CanvasWindow canvas;
    private double leftX;
    private double topY;


    public Character(CanvasWindow canvas){
        this.canvas = canvas;
        characterShape = new Rectangle(canvas.getWidth() / 2, canvas.getHeight() / 2, CHARACTER_WIDTH, CHARACTER_HEIGHT);
        characterShape.setFillColor(Color.BLUE);
        leftX = characterShape.getX();
        topY = characterShape.getY();
    }

    /**
     * Tests for intersections between game ball and a specific paddle.
     *
     * @return true If the given ball intersects this bubble (even tangentially).
     */
    public void bulletHitsCharacter(Bullets bullet) {
        if (bullet.hit().contains(characterShape)) {
                canvas.remove(characterShape);
        }
    }

    /**
     * Moves the character left by a set amount when called
     */
    public void moveLeft(double dt) {
        if (characterShape.getX() > 0) {
            leftX -= CHARACTER_SPEED * dt;
            characterShape.setX(leftX);
        }
    }

    /**
     * Moves the character right by a set amount when called
     */
    public void moveRight(double dt) {
        if (characterShape.getX() < canvas.getWidth() - CHARACTER_WIDTH){
            leftX += CHARACTER_SPEED * dt;
            characterShape.setX(leftX);
        }
    }

    /**
     * Moves the character up by a set amount when called
     */
    public void moveUp(double dt) {
        if (characterShape.getY() > 0) {
            topY -= CHARACTER_SPEED * dt;
            characterShape.setY(topY);
        }
    }

    /**
     * Moves the character down by a set amount when called
     */
    public void moveDown(double dt) {
        if (characterShape.getY() < canvas.getHeight() - CHARACTER_HEIGHT){
            topY += CHARACTER_SPEED * dt;
            characterShape.setY(topY);
        }
    }

    /*
     * Gets the characterShape of the character
     */
    public Rectangle getcharacterShape() {
        return characterShape;
    }
}