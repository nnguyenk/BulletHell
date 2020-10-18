package BulletHell;

import BulletHell.Powerups.Slow;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;

/**
 * The game of Bullet Hell
 */
public class BulletHell {
    private CanvasWindow canvas;
    private Player player;
    private int currentLife;
    private BulletManager manager;
    private HeartManager heartManagement;
    private Terrain terrain;
    private Slow slow = new Slow();

    public static final int MAX_LIFE = 3;

    public BulletHell(){
        canvas = new CanvasWindow("Stage 1", 800, 800);
        manager = new BulletManager(canvas);
        heartManagement = new HeartManager(canvas);
        terrain = new Terrain(canvas);
    }

    public static void main(String[] args) {
        BulletHell bulletHell = new BulletHell();
        bulletHell.start();
    }

    public void start(){
        heartManagement.SummonHearts();
        terrain.SummonTerrain();
        createPlayer(0.1);
        manager.spawnBullets(5);

        currentLife = MAX_LIFE;

        canvas.animate(dt -> {
            if (currentLife > 0) {

                if (player.isImmune()) {
                    player.reduceImmunity(dt);
                    if (!player.isImmune()) {
                        player.endImmunity();
                    }
                }

                if (manager.bulletsIntersect(player, terrain)) {
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

                if (slow.isSlowed(manager)) {
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
            canvas.remove(heartManagement.getHeart());
        }
        if (currentLife == 2) {
            canvas.remove(heartManagement.getHeart2());
        }
        if (currentLife == 1) {
            canvas.remove(heartManagement.getHeart3());
        }
    }
}
