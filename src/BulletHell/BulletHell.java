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
    private int currentRound = 1;
    private BulletManager manager;
    private HeartManager heartManagement;
    private PowerManager powerManager;
    private EnergyManager energyManagement;

    private RoundTitle roundTitle;
    private GameDescription gamedescription;
    private Terrain terrain;

    private Boolean booleanholder = true;

    public static final int MAX_LIFE = 3;

    public BulletHell(){
        canvas = new CanvasWindow("Stage 1", 800, 800);
        canvas.add(new Rectangle(0, 40, 800, 1));
        
        manager = new BulletManager(canvas);
        heartManagement = new HeartManager(canvas);
        roundTitle = new RoundTitle(canvas);
        gamedescription = new GameDescription(canvas);
        terrain = new Terrain(canvas);

        energyManagement = new EnergyManager(canvas);
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
        roundTitle.changeTitle(currentRound);
        heartManagement.SummonHearts();
        terrain.SummonTerrain();
        manager.spawnBullets(5);
        createPlayer(0.1);
        createPowerups();

        currentLife = MAX_LIFE;

        canvas.animate(dt -> {
            if (currentLife > 0) {

                if(!energyManagement.fullEnergy()){
                    energyManagement.summonEnergy();
                }

                if(energyManagement.fullEnergy()){
                    System.out.println(10);
                }
                

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
                    currentRound ++;
                    newRound(currentRound);
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
     * Creates the environment for a new round using the roundnumber to decide number of bullets and title
     * @param round
     */
    public void newRound(int round){
        roundTitle.changeTitle(round);
        terrain.SummonTerrain();
        manager.spawnBullets(5 + round * 2);
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
