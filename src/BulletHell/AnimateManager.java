package BulletHell;

import java.util.ArrayList;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.Key;

public class AnimateManager {
    CanvasWindow canvas;
    

    private Image sprite;
    private int frameNumber = 0;
    private boolean L = false;
    private boolean R = false;
    private boolean D = false;
    private boolean U = false;

    ArrayList<Boolean> directions = new ArrayList<Boolean>();

    public AnimateManager(CanvasWindow canvas){
        this.canvas = canvas;
        directions.add(L);
        directions.add(R);
        directions.add(D);
        directions.add(U);
    }

    public void animateSprite(Key key, Player player, CanvasWindow canvas){
        if(frameNumber == 4){
            frameNumber = 0;
        }
        if (key == Key.LEFT_ARROW) {
            if((R || D || U) == true && sprite != null){
                canvas.remove(sprite);
                frameNumber = 0;
                R = false;
                D = false;
                U = false;
            }else if(L == true && sprite != null){
                canvas.remove(sprite);
            }
            sprite = new Image("left" + frameNumber + ".png");
            generateSprite(sprite, player);
            L = true;
        }
        if (key == Key.RIGHT_ARROW) {
            if((L || D || U) == true && sprite != null){
                canvas.remove(sprite);
                frameNumber = 0;
                L = false;
                D = false;
                U = false;
            }else if(R == true && sprite != null){
                canvas.remove(sprite);
            }
            sprite = new Image("right" + frameNumber + ".png");
            generateSprite(sprite, player);
            R = true;
        }
        if (key == Key.UP_ARROW) {
            if((R || D || L) == true && sprite != null){
                canvas.remove(sprite);
                frameNumber = 0;
                R = false;
                D = false;
                L = false;
            }else if(U == true && sprite != null){
                canvas.remove(sprite);
            }
            sprite = new Image("backward" + frameNumber + ".png");
            generateSprite(sprite, player);
            U = true;
        }
        if (key == Key.DOWN_ARROW) {
            if((R || L || U) == true && sprite != null){
                canvas.remove(sprite);
                frameNumber = 0;
                R = false;
                L = false;
                U = false;
            }else if(D = true && sprite != null){
                canvas.remove(sprite);
            }
            sprite = new Image("forward" + frameNumber + ".png");
            generateSprite(sprite, player);
            D = true;
        }
        getSprite();
    }

    public Image getSprite(){
        return sprite;
    }

    private void generateSprite(Image sprite, Player player){
        sprite.setMaxHeight(player.getPlayerShape().getHeight() * 3);
        sprite.setMaxHeight(player.getPlayerShape().getWidth() * 3);
        sprite.setCenter(player.getPlayerShape().getCenter());
        canvas.add(sprite);
        frameNumber += 1;
    }

    public void placeSprite(Player player){
        if(sprite != null){
            canvas.add(sprite);
            sprite.setCenter(player.getPlayerShape().getCenter());
            canvas.remove(sprite);
        }
    }
}
