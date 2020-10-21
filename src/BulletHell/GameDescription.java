package BulletHell;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;

import java.awt.Color;

public class GameDescription {
    private CanvasWindow canvas;
    private GraphicsText welcome;
    private GraphicsText gameRules1;
    private GraphicsText gameRules2;
    private GraphicsText gameRules3;
    private GraphicsText gameRules4;
    private GraphicsText gameRules5;

    private Image sprite;

    private BulletManager bulletManager;
    private Player player;
    private Terrain terrain;

    public GameDescription(CanvasWindow canvas){
        this.canvas = canvas;

        welcome = new GraphicsText();
        welcome.setFont("Times New Roman", FontStyle.BOLD_ITALIC, 30);
        welcome.setCenter(canvas.getWidth() / 2 - 100, 100);

        gameRules1 = new GraphicsText();
        gameRules1.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules1.setCenter(canvas.getWidth() / 2 - 250, 400);

        gameRules2 = new GraphicsText();
        gameRules2.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules2.setCenter(canvas.getWidth() / 2 - 250, 420);

        gameRules3 = new GraphicsText();
        gameRules3.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules3.setCenter(canvas.getWidth() / 2 - 250, 460);

        gameRules4 = new GraphicsText();
        gameRules4.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules4.setCenter(canvas.getWidth() / 2 - 250, 490);

        gameRules5 = new GraphicsText();
        gameRules5.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules5.setCenter(canvas.getWidth() / 2 - 250, 520);

        sprite = new Image("down0.png");
        sprite.setCenter(canvas.getWidth() / 2 - 80, 240);
        sprite.setMaxHeight(100);

        bulletManager = new BulletManager(canvas);
        player = new Player(canvas);
        terrain = new Terrain(canvas);
    }

    public void addRules(){
        welcome.setText("Welcome to Bullet Hell!");
        setGameRules();
        addToCanvas();
    }

    private void setGameRules(){
        gameRules1.setText("Here are the rules: Move around with the arrow keys. Use power ups with q and e.");
        gameRules2.setText("You have 3 lives, and will go invulnerable for some time upon being hit by bullets");
        gameRules3.setText("Once all bullets on the screen disappear, you have won the round and will proceed to the next!");
        gameRules4.setText("Beware, blue bullets freeze you.");
        gameRules5.setText("Click your mouse to begin playing");
    }

    private void addToCanvas(){
        bulletManager.spawnBullets(7, player, terrain);

        canvas.setBackground(Color.GREEN);
        canvas.add(welcome);
        canvas.add(gameRules1);
        canvas.add(gameRules2);
        canvas.add(gameRules3);
        canvas.add(gameRules4);
        canvas.add(gameRules5);
        canvas.add(sprite);

        canvas.animate((dt) -> {
                bulletManager.bulletsIntersect(player, terrain);
                if (!bulletManager.bulletsLeft()){
                    bulletManager.spawnBullets(7, player, terrain);
                }
        });
    }

    public void beginGame(){
        canvas.setBackground(Color.WHITE);
        canvas.remove(welcome);
        canvas.remove(gameRules1);
        canvas.remove(gameRules2);
        canvas.remove(gameRules3);
        canvas.remove(gameRules4);
        canvas.remove(gameRules5);
        canvas.remove(sprite);
        canvas.pause(100);
    }
}
