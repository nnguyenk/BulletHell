package BulletHell;

import BulletHell.Powerups.Slow;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.Image;

/**
 * The game of Bullet Hell
 */
public class BulletHell {
    private CanvasWindow canvas;
    private Player player;
    private int currentLife;
    private BulletManager manager;
    private Image heart = new Image("Heart-1.png");
    private Image heart2 = new Image("Heart-1.png");
    private Image heart3 = new Image("Heart-1.png");
    private Slow slow = new Slow();

    public static final int MAX_LIFE = 3;

    public BulletHell(){
        canvas = new CanvasWindow("Stage 1", 800, 800);
        manager = new BulletManager(canvas);
        
        heart.setCenter(750, 130);
        heart.setMaxHeight(80);
        heart.setMaxWidth(80);
        heart2.setCenter(790, 130);
        heart2.setMaxHeight(80);
        heart2.setMaxWidth(80);
        heart3.setCenter(830, 130);
        heart3.setMaxHeight(80);
        heart3.setMaxWidth(80);
    }

    public static void main(String[] args) {
        BulletHell bulletHell = new BulletHell();
        bulletHell.start();
    }

    public void start(){
        canvas.add(heart);
        canvas.add(heart2);
        canvas.add(heart3);
        createPlayer(0.1);
        manager.spawnBullets(10);

        currentLife = MAX_LIFE;

        canvas.animate(dt -> {
            if (currentLife > 0) {            
                player.reduceImmunity(dt);

                if (manager.bulletsIntersect(player)) {
                    removeHeart();
                    currentLife -= 1;
                    player.startImmunity();
                    slow.startSlow(manager);
                }

                if (!manager.bulletsLeft()) {
                    System.out.println("Congratulations! You have WON!");
                    canvas.closeWindow();
                    //go to next room?
                }
                if(slow.isSlowed(manager)){
                    slow.reduceSlow(dt);
                }
            }
            else { // breaks out of the animation loop
                canvas.removeAll();
                System.out.println("You have LOST!");
                canvas.closeWindow();
            }
        });
    }

    /**
     * Adds the player to the canvas and enables control with the mouse
     * 
     * @param dt The movement rate of the player
     */
    public void createPlayer(double dt){
        player = new Player(canvas);
        canvas.add(player.getPlayerShape());
        canvas.onKeyDown(event -> {
            if (event.getKey() == Key.LEFT_ARROW) {
                player.moveLeft(dt);
            }
            if (event.getKey() == Key.RIGHT_ARROW) {
                player.moveRight(dt);
            }
            if (event.getKey() == Key.UP_ARROW) {
                player.moveUp(dt);
            }
            if (event.getKey() == Key.DOWN_ARROW) {
                player.moveDown(dt);
            }
        });
    }

    /**
     * Removes hearts based on the player's lives left
     */
    public void removeHeart() {
        if (currentLife == 3) {
            canvas.remove(heart3);
        }
        if (currentLife == 2) {
            canvas.remove(heart2);
        }
        if (currentLife == 1) {
            canvas.remove(heart);
        }
    }
}
