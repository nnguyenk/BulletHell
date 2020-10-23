package BulletHell;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.events.Key;

/**
 * The game of Bullet Hell. Created by Antonio Solis, Erik Borgehammar, and Nam Nguyen.
 */
public class BulletHell {
    private CanvasWindow canvas;
    private Player player;

    private BulletManager bulletManager;
    private HeartManager heartManager;
    private PowerManager powerManager;
    private AnimateManager animationManager;

    private RoundTitle roundTitle;
    private GameDescription gamedescription;
    private Terrain terrain;

    private Image background = new Image("BH_BackgroundGame.png");

    private Key currentDirection;
    private Key currentSpriteDirection;

    private int currentRound = 0;
    private static boolean started = false;
    public static final int MAX_LIFE = 3;

    private BulletHell() {
        canvas = new CanvasWindow("Bullet Hell!", 800, 800);

        bulletManager = new BulletManager(canvas);
        heartManager = new HeartManager(canvas);
        gamedescription = new GameDescription(canvas);
        terrain = new Terrain(canvas);
        animationManager = new AnimateManager(canvas);
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

    /**
     * Sets up the game, then animates the canvas until there are no remaining lives.
     */
    private void start() {
        started = true;
        setUpGame();
        canvas.animate(dt -> {
            if (heartManager.heartsLeft() > 0) {
                if (!bulletManager.bulletsLeft()) {
                    newRound();
                }
                reduceAllTimer(dt);
                updatePlayer();
            }
            else {
                lose();
            }
        });
    }

    /**
     * Sets up the player, the maximum lives, and the powerups.
     */
    private void setUpGame() {
        canvas.add(background);
        heartManager.generateHearts(3);
        createPlayer(0.1);
        createSprite();
        createPowerups();
        roundTitle = new RoundTitle(canvas);
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
        canvas.add(player.getPlayerHitbox());
        
        canvas.onKeyDown(event -> {
            currentDirection = event.getKey();
            player.turn(currentDirection);
        });
        canvas.onKeyUp(event -> {
            if (currentDirection == event.getKey())
            player.stop();
        });
    }

    private void createSprite() {
        canvas.onKeyDown(event -> {
            if (currentSpriteDirection != event.getKey()) {
                currentSpriteDirection = event.getKey();
                moveSprite(currentSpriteDirection);
            } else if (currentSpriteDirection == event.getKey()) {
                currentSpriteDirection = event.getKey();
                moveSprite(currentSpriteDirection);
            }
        });
    }


    private void moveSprite(Key key) {
        if (
            key == Key.LEFT_ARROW
            || key == Key.RIGHT_ARROW
            || key == Key.UP_ARROW
            || key == Key.DOWN_ARROW) {
            animationManager.animateSprite(key, player, canvas, player.isFrozen());
        }
    }
    
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
        bulletManager.spawnBullets(5 + currentRound * 2, player, terrain);
        canvas.draw();
        canvas.pause(1000);
    }

    /**
     * Updates the position of the player and the sprite, and check for collision with bullets.
     */
    private void updatePlayer() {
        player.move();
        animationManager.placeSprite(player);
        animationManager.spriteFreeze(player.isFrozen(), player);
        playerIsHit();
    }

    /**
     * Checks if the player is hit with any bullet.
     * Removes one heart (life) if true.
     */
    private void playerIsHit() {
        if (bulletManager.bulletsIntersect(player, terrain)) {
            heartManager.removeHeart();
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
     * Returns the current player of the game.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the bullet manager of the game.
     */
    public BulletManager getBulletManager() {
        return bulletManager;
    }

    /**
     * Returns the heart manager of the game.
     */
    public HeartManager getHeartManager() {
        return heartManager;
    }
}
