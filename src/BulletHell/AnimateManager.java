package BulletHell;

import java.util.ArrayList;

import edu.macalester.graphics.*;

public class AnimateManager {
    CanvasWindow canvas;

    ArrayList<Image> forward = new ArrayList<Image>();
    ArrayList<Image> right = new ArrayList<Image>();
    ArrayList<Image> left = new ArrayList<Image>();
    ArrayList<Image> backward = new ArrayList<Image>();
    

    private Image sprite;
    private int frameNumber = 0;
    private boolean L = false;
    private boolean R = false;
    private boolean D = false;
    private boolean U = false;

    public AnimateManager(CanvasWindow canvas){
        this.canvas = canvas;
    }

    public void betterAnimate(String key, Player player, CanvasWindow canvas){
        if(frameNumber == 4){
            frameNumber = 0;
        }
        if (key.equals("left")) {
            if((R || D || U) == true){
                canvas.remove(sprite);
                frameNumber = 0;
                R = false;
                D = false;
                U = false;
            }else if(L == true){
                canvas.remove(sprite);
            }
            sprite = new Image("left" + frameNumber + ".png");
            sprite.setMaxHeight(player.getPlayerShape().getHeight() * 3);
            sprite.setMaxHeight(player.getPlayerShape().getWidth() * 3);
            sprite.setCenter(player.getPlayerShape().getCenter());
            canvas.add(sprite);
            frameNumber += 1;
            L = true;
        }
        if (key.equals("right")) {
            if((L || D || U) == true){
                canvas.remove(sprite);
                frameNumber = 0;
                L = false;
                D = false;
                U = false;
            }else if(R == true){
                canvas.remove(sprite);
            }
            sprite = new Image("right" + frameNumber + ".png");
            sprite.setMaxHeight(player.getPlayerShape().getHeight() * 3);
            sprite.setMaxHeight(player.getPlayerShape().getWidth() * 3);
            sprite.setCenter(player.getPlayerShape().getCenter());
            canvas.add(sprite);
            frameNumber += 1;
            R = true;
        }
        if (key.equals("up")) {
            if((R || D || L) == true){
                canvas.remove(sprite);
                frameNumber = 0;
                R = false;
                D = false;
                L = false;
            }else if(U == true){
                canvas.remove(sprite);
            }
            sprite = new Image("backward" + frameNumber + ".png");
            sprite.setMaxHeight(player.getPlayerShape().getHeight() * 3);
            sprite.setMaxHeight(player.getPlayerShape().getWidth() * 3);
            sprite.setCenter(player.getPlayerShape().getCenter());
            canvas.add(sprite);
            frameNumber += 1;
            U = true;
        }
        if (key.equals("down")) {
            if((R || L || U) == true){
                canvas.remove(sprite);
                frameNumber = 0;
                R = false;
                L = false;
                U = false;
            }else if(D = true){
                canvas.remove(sprite);
            }
            sprite = new Image("forward" + frameNumber + ".png");
            sprite.setMaxHeight(player.getPlayerShape().getHeight() * 3);
            sprite.setMaxHeight(player.getPlayerShape().getWidth() * 3);
            sprite.setCenter(player.getPlayerShape().getCenter());
            canvas.add(sprite);
            frameNumber += 1;
            D = true;
        }
    }
}
