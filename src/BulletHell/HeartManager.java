package BulletHell;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;

public class HeartManager {
    private CanvasWindow canvas;
    private Player player;

    private Image heart = new Image("Heart-1.png");
    private Image heart2 = new Image("Heart-1.png");
    private Image heart3 = new Image("Heart-1.png");

    public HeartManager(CanvasWindow canvas, Player player){
        this.canvas = canvas;
        this.player = player;
    }

    public void SummonHearts(){
        heart.setCenter(750, 130);
        heart.setMaxHeight(80);
        heart.setMaxWidth(80);
        heart2.setCenter(790, 130);
        heart2.setMaxHeight(80);
        heart2.setMaxWidth(80);
        heart3.setCenter(830, 130);
        heart3.setMaxHeight(80);
        heart3.setMaxWidth(80);

        canvas.add(heart);
        canvas.add(heart2);
        canvas.add(heart3);
    }

    /**
     * Removes hearts based on the player's lives left
     */
    // public void removeHeart() {
    //     if (player.getCurrentLife == 3) {
    //         canvas.remove(heart3);
    //     }
    //     if (player.getCurrentLife == 2) {
    //         canvas.remove(heart2);
    //     }
    //     if (player.getCurrentLife == 1) {
    //         canvas.remove(heart);
    //     }
    // }

}
