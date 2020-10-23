package BulletHell;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;

import java.awt.Color;

/**
 * A class to hold the tutorial text at the beginning of every game.
 */
public class GameDescription {
    private CanvasWindow canvas;
    private GraphicsText gameRules1;
    private GraphicsText gameRules2;
    private GraphicsText gameRules3;
    private GraphicsText gameRules4;
    private GraphicsText gameRules5;
    private GraphicsText gameRules6;

    private Image sprite;
    private Image background = new Image("BH_Background.png");
    private boolean animating = true;

    private BulletManager bulletManager;
    private Player player;
    private Terrain terrain;

    public GameDescription(CanvasWindow canvas){
        this.canvas = canvas;

        gameRules1 = new GraphicsText();
        gameRules1.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules1.setCenter(canvas.getWidth() / 2 - 350, 400);

        gameRules2 = new GraphicsText();
        gameRules2.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules2.setCenter(canvas.getWidth() / 2 - 350, 420);

        gameRules3 = new GraphicsText();
        gameRules3.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules3.setCenter(canvas.getWidth() / 2 - 350, 460);

        gameRules4 = new GraphicsText();
        gameRules4.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules4.setCenter(canvas.getWidth() / 2 - 350, 490);

        gameRules5 = new GraphicsText();
        gameRules5.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules5.setCenter(canvas.getWidth() / 2 - 350, 520);

        gameRules6 = new GraphicsText();
        gameRules6.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules6.setCenter(canvas.getWidth() / 2 - 350, 550);

        sprite = new Image("down0.png");
        sprite.setCenter(450, canvas.getHeight() / 2 + 50);
        sprite.setMaxHeight(100);

        background.setCenter(canvas.getCenter());

        bulletManager = new BulletManager(canvas);
        player = new Player(canvas);
        terrain = new Terrain(canvas);
    }

    /**
     * Adds the rules to the canvas.
     */
    public void addRules() {
        setGameRules();
        addToCanvas();
    }

    /**
     * Sets the text for the game rules.
     */
    private void setGameRules() {
        gameRules1.setText("Here are the rules: Move around with the arrow keys. Use power ups with q, w, and e.");
        gameRules2.setText("Q is a bullet slow, W is a pacman-esque eat bullets powerup, and E is a delayed heal (7 seconds)");
        gameRules3.setText("You have 3 lives, and will go invulnerable for some time upon being hit by bullets");
        gameRules4.setText("Once all bullets on the screen disappear, you have won the round and will proceed to the next!");
        gameRules5.setText("Blue bullets freeze you, yellow bullets do not give you immunity, and green bullets fly on after damaging you.");
        gameRules6.setText("Click your mouse to begin playing");
    }

    /**
     * Adds the game rules and game rule background to the screen. Adds bouncing bullets.
     */
    private void addToCanvas() {
        bulletManager.spawnBullets(7, player, terrain);

        canvas.setBackground(Color.GREEN);
        canvas.add(background);
        canvas.add(gameRules1);
        canvas.add(gameRules2);
        canvas.add(gameRules3);
        canvas.add(gameRules4);
        canvas.add(gameRules5);
        canvas.add(gameRules6);
        canvas.add(sprite);
        bulletManager.spawnBullets(7, player, terrain);

        canvas.animate((dt) -> {
            if (animating) {
                bulletManager.bulletsIntersect(player, terrain);
                if (!bulletManager.bulletsLeft()) {
                    bulletManager.spawnBullets(7, player, terrain);
                }
            }
        });
    }

    /**
     * Clears everything for the game to begin.
     */
    public void beginGame() {
        canvas.setBackground(Color.WHITE);
        animating = false;
        canvas.removeAll();
        canvas.pause(100);
    }
}
