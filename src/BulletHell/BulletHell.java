package BulletHell;

import BulletHell.BulletTypes.PlayerBullet;
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
    private PlayerBullet newPlayerBullet;

    public static final int MAX_LIFE = 3;

    public BulletHell(){
        canvas = new CanvasWindow("Stage 1", 800, 800);
        manager = new BulletManager(canvas);
    }

    public static void main(String[] args) {
        BulletHell bulletHell = new BulletHell();
        bulletHell.start();
    }

    public void start(){
        createPlayer(0.1);
        manager.spawnBullets(2);

        currentLife = MAX_LIFE;

        canvas.animate(() -> {
            if (currentLife > 0) {
                manager.updateBulletState(player, newPlayerBullet);
                // newPlayerBullet.updatePosition(manager.getAllBullets());

                if (player.isHit(manager.getAllBullets())) { //if the player gets hit
                    manager.removeBullets();
                    currentLife -= 1;
                    if (!manager.bulletsLeft()){ 
                        System.out.println("Congratulations! You have WON!");
                        canvas.closeWindow();
                        //go to next room?
                    }
                }
            }
            else { //breaks out of the animation loop
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
    
    // public void shootBullet(){
    //     canvas.onKeyDown(event -> {
    //         if (event.getKey() == Key.W) {
    //             newPlayerBullet = new PlayerBullet(canvas, 90);
    //             canvas.add(newPlayerBullet.getShape());
    //         }
    //         if (event.getKey() == Key.A) {
    //             newPlayerBullet = new PlayerBullet(canvas, 90);
    //             canvas.add(newPlayerBullet.getShape());
    //         }
    //         if (event.getKey() == Key.S) {
    //             newPlayerBullet = new PlayerBullet(canvas, 90);
    //             canvas.add(newPlayerBullet.getShape());
    //         }
    //         if (event.getKey() == Key.D) {
    //             newPlayerBullet = new PlayerBullet(canvas, 90);
    //             canvas.add(newPlayerBullet.getShape());
    //         }
    //     });
    // }
}
