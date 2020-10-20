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
    private BulletManager bulletManager;
    private HeartManager heartManagement;
    private PowerManager powerManager;

    private AnimateManager animationManagement;

    private RoundTitle roundTitle;
    // private GameDescription gamedescription;
    private Terrain terrain;

    // private Boolean booleanholder = true;

    public static final int MAX_LIFE = 3;

    public BulletHell() {
        canvas = new CanvasWindow("Bullet Hell!", 800, 800);
        canvas.add(new Rectangle(0, 40, 800, 1));
        
        bulletManager = new BulletManager(canvas);
        heartManagement = new HeartManager(canvas);
        roundTitle = new RoundTitle(canvas);
        // gamedescription = new GameDescription(canvas);
        terrain = new Terrain(canvas);

        animationManagement = new AnimateManager(canvas);
    }

    public static void main(String[] args) {
        BulletHell bulletHell = new BulletHell();
        bulletHell.start();
    }

    public void start() {
        // while (preGameText()) {
        //     gamedescription.addRules();
        // }
        // gamedescription.beginGame();
        setUpGame();
        canvas.animate(dt -> {
            if (currentLife > 0) {
                if (!bulletManager.bulletsLeft()) {
                    newRound();
                }
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
    public void setUpGame() {
        heartManagement.SummonHearts();
        createPlayer(0.1);
        createPowerups();
        currentLife = MAX_LIFE;

        // createSprite(0.1);
    }

    /**
     * Adds the player to the canvas and enables control with the keyboard.
     * 
     * @param dt The movement rate of the player.
     */
    public void createPlayer(double dt) {
        player = new Player(canvas);
        canvas.add(player.getPlayerShape());
        canvas.onKeyDown(event -> movePlayer(event.getKey(), dt));
    }

    // public void createSprite(double dt){
    //     canvas.onKeyDown(event -> moveSprite(event.getKey(), dt));
    // }

    /**
     * Moves the player based on the key.
     * 
     * @param key The input direction key.
     * @param dt The movement rate of the player.
     */
    private void movePlayer(Key key, double dt) {
        if (key == Key.LEFT_ARROW) {
            player.moveLeft(dt);
        }
        if (key == Key.RIGHT_ARROW) {
            player.moveRight(dt);
        }
        if (key == Key.UP_ARROW) {
            player.moveUp(dt);
        }
        if (key == Key.DOWN_ARROW) {
            player.moveDown(dt);
        }
    }

    // private void moveSprite(Key key, double dt) {
    //     if (key == Key.LEFT_ARROW) {
    //         animationManagement.betterAnimate("left", player, canvas);
    //     }
    //     if (key == Key.RIGHT_ARROW) {
    //         animationManagement.betterAnimate("right", player, canvas);
    //     }
    //     if (key == Key.UP_ARROW) {
    //         animationManagement.betterAnimate("up", player, canvas);
    //     }
    //     if (key == Key.DOWN_ARROW) {
    //         animationManagement.betterAnimate("down", player, canvas);
    //     }
    // }
    
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
     * Reduces the cooldown/duration of all powers.
     * Also reduces the immunity of the player (if any).
     * 
     * @param dt The number of seconds that will be deducted.
     */
    public void reduceAllTimer(double dt) {
        powerManager.reduceCooldown(dt);
        powerManager.reduceDuration(dt);
        player.reduceImmunity(dt);
    }

    /**
     * Creates the environment for a new round using the roundnumber to decide number of bullets and title.
     */
    public void newRound() {
        if (!bulletManager.bulletsLeft()) {
            currentRound++;
            roundTitle.changeTitle(currentRound);
            terrain.SummonTerrain();
            bulletManager.spawnBullets(3 + currentRound * 2, player, terrain);
        }
    }

    /**
     * Checks if the player is hit with any bullet.
     * Removes one heart (life) if true.
     */
    public void playerIsHit() {
        if (bulletManager.bulletsIntersect(player, terrain)) {
            removeHeart();
            currentLife -= 1;
        }
    }

    /**
     * Closes the window, and prints out an encouraging message. 
     */
    public void lose() {
        canvas.removeAll();
        System.out.println("You have LOST!");
        canvas.closeWindow();
    }

    // public boolean preGameText() {
    //     canvas.onKeyDown(event -> {
    //         if (event.getKey() == Key.SPACE){
    //             booleanholder = false;
    //         }
    //     });
    //     return booleanholder;
    // } 

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
        return bulletManager;
    }
}
