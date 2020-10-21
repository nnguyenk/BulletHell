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
    private int currentRound = 0;
    private static boolean started = false;

    private BulletManager bulletManager;
    private HeartManager heartManager;
    private PowerManager powerManager;

    private AnimateManager animationManager;

    private RoundTitle roundTitle;
    private GameDescription gamedescription;
    private Terrain terrain;
    private Key currentDirection; // Holds the most recent key pressed.

    // private Boolean booleanholder = true;

    public static final int MAX_LIFE = 3;

    private BulletHell() {
        canvas = new CanvasWindow("Bullet Hell!", 800, 800);
        canvas.add(new Rectangle(0, 40, 800, 1));

        bulletManager = new BulletManager(canvas);
        heartManager = new HeartManager(canvas);
        roundTitle = new RoundTitle(canvas);
        gamedescription = new GameDescription(canvas);
        terrain = new Terrain(canvas);

        // animationManager = new AnimateManager(canvas);
    }

    public static void main(String[] args) {
        BulletHell bulletHell = new BulletHell();
        bulletHell.gamedescription.addRules();
        bulletHell.canvas.onClick((d) -> {
            if (!started) {
                bulletHell.gamedescription.beginGame();
                bulletHell.start();
            }
        });
    }

    private void start() {
        started = true;
        setUpGame();
        canvas.animate(dt -> {
            if (currentLife > 0) {
                if (!bulletManager.bulletsLeft()) {
                    newRound();
                }
                player.move();
                reduceAllTimer(dt);
                playerIsHit();
            }
            else { // breaks out of the animation loop
                lose();
            }
        });
    }

    /**
     * Sets up the player, the maximum lives, and the powerups.
     */
    private void setUpGame() {
        heartManager.generateHearts(3);
        createPlayer(0.1);
        createPowerups();
        currentLife = MAX_LIFE;
        // createSprite(0.1);
    }

    /**
     * Adds the player to the canvas and enables control with the keyboard.
     * When a key is pressed, turn the player to the corresponding direction.
     * When a key is lifted, if it's the same direction with the current one, stops the player.
     * 
     * @param dt The movement rate of the player.
     */
    private void createPlayer(double dt) {
        player = new Player(canvas);
        canvas.add(player.getPlayerShape());
        
        canvas.onKeyDown(event -> {
            currentDirection = event.getKey();
            player.turn(currentDirection);
        });
        canvas.onKeyUp(event -> {
            if (currentDirection == event.getKey())
            player.stop();
        });
    }

    // private void createSprite(double dt){
    //     canvas.onKeyDown(event -> moveSprite(event.getKey(), dt));
    // }

    // private void moveSprite(Key key, double dt) {
    //     if (key == Key.LEFT_ARROW) {
    //         animationManager.animateSprite(key, player, canvas, dt);
    //     }
    //     if (key == Key.RIGHT_ARROW) {
    //         animationManager.animateSprite(key, player, canvas, dt);
    //     }
    //     if (key == Key.UP_ARROW) {
    //         animationManager.animateSprite(key, player, canvas, dt);
    //     }
    //     if (key == Key.DOWN_ARROW) {
    //         animationManager.animateSprite(key, player, canvas, dt);
    //     }
    // }
    
     /**
     * Creates all the boxes of powerups.
     * Allows the player to activate the power they click on.
     */
    private void createPowerups() {
        powerManager = new PowerManager(canvas, this);
        powerManager.generatePowerups();
        canvas.onKeyDown(event -> powerManager.activatePowerups(event.getKey()));
    } 

    /**
     * Reduces the cooldown/duration of all powers.
     * Also reduces the immunity of the player (if any).
     * 
     * @param dt The number of seconds that will be deducted.
     */
    private void reduceAllTimer(double dt) {
        powerManager.reduceCooldown(dt);
        powerManager.reduceDuration(dt);
        player.reduceImmunity(dt);
    }

    /**
     * Creates the environment for a new round using the roundnumber to decide number of bullets and title.
     * Pauses the canvas for 1 second
     */
    private void newRound() {
        currentRound++;
        roundTitle.changeTitle(currentRound);
        terrain.clearList();
        terrain.generateTerrain(3);
        bulletManager.spawnBullets(3 + currentRound * 2, player, terrain);
        canvas.draw();
        canvas.pause(1000);
    }

    /**
     * Checks if the player is hit with any bullet.
     * Removes one heart (life) if true.
     */
    private void playerIsHit() {
        if (bulletManager.bulletsIntersect(player, terrain)) {
            heartManager.removeHeart();
            currentLife -= 1;
        }
    }

    /**
     * Closes the window, and prints out an encouraging message. 
     */
    private void lose() {
        canvas.removeAll();
        System.out.println("You have LOST!");
        canvas.closeWindow();
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
        return bulletManager;
    }

    /**
     * Return the heart manager of the game.
     */
    public HeartManager getHeartManager() {
        return heartManager;
    }
}
