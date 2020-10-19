package BulletHell;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;
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
    private PowerManager powerManager;

    public static final int MAX_LIFE = 3;

    public BulletHell(){
        canvas = new CanvasWindow("Stage 1", 800, 800);
        canvas.add(new Rectangle(0, 40, 800, 1));
        
        manager = new BulletManager(canvas);
        heartManagement = new HeartManager(canvas);
        terrain = new Terrain(canvas);
    }

    public static void main(String[] args) {
        BulletHell bulletHell = new BulletHell();
        bulletHell.start();
    }

    public void start() {
        heartManagement.SummonHearts();
        terrain.SummonTerrain();
        manager.spawnBullets(5);
        createPlayer(0.1);
        createPowerups();

        currentLife = MAX_LIFE;

        canvas.animate(dt -> {
            if (currentLife > 0) {

                if (player.isImmune()) {
                    player.reduceImmunity(dt);
                }

                powerManager.reduceCooldown(dt);
                powerManager.reduceDuration(dt);

                if (manager.bulletsIntersect(player, terrain)) {
                    removeHeart();
                    currentLife -= 1;
                    player.startImmunity();
                }

                if (!manager.bulletsLeft()) {
                    System.out.println("Congratulations! You have WON!");
                    canvas.closeWindow();
                    //go to next room?
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
     * Adds the player to the canvas and enables control with the keyboard.
     * 
     * @param dt The movement rate of the player
     */
    public void createPlayer(double dt) {
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
     * Creates all the boxes of powerups.
     * Allows the player to activate the power they click on.
     */
    public void createPowerups() {
        powerManager = new PowerManager(canvas, this);
        powerManager.createPowers();
        canvas.onKeyDown(event -> powerManager.activatePower(event.getKey())); 
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

    /**
     * Return the current player of the game.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Return the bullet manager of the game.
     */
    public BulletManager getBulletManager() {
        return manager;
    }
}
