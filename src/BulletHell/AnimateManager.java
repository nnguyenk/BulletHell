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

    private boolean spriteHasMoved = false;
    private Key lastkey;

    ArrayList<Boolean> directions = new ArrayList<Boolean>();
    ArrayList<Image> down = new ArrayList<Image>();
    ArrayList<Image> left = new ArrayList<Image>();
    ArrayList<Image> right = new ArrayList<Image>();
    ArrayList<Image> up = new ArrayList<Image>();


    public AnimateManager(CanvasWindow canvas){
        this.canvas = canvas;
        directions.add(L);
        directions.add(R);
        directions.add(D);
        directions.add(U);

        fillImagelist(down, "down");
        fillImagelist(left, "left");
        fillImagelist(right, "right");
        fillImagelist(up, "up");
    }

    public void animateSprite(Key key, Player player, CanvasWindow canvas){
        if(frameNumber == 4){
            frameNumber = 0;
        }
        if (key == Key.LEFT_ARROW) {
           spriteChange(player, key, left);
        }
        if (key == Key.RIGHT_ARROW) {
            spriteChange(player, key, right);
        }
        if (key == Key.UP_ARROW) {
            spriteChange(player, key, up);
        }
        if (key == Key.DOWN_ARROW) {
            spriteChange(player, key, down);
        }
        getSprite();
    }

    public Image getSprite(){
        return sprite;
    }

    private void generateSprite(Image sprite, Player player){
        sprite.setMaxHeight(player.getPlayerShape().getHeight() - 5);
        sprite.setMaxWidth(player.getPlayerShape().getWidth() - 5);
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

    private void fillImagelist(ArrayList<Image> images, String direction){
        for(int i = 0; i < 4; i++){
            images.add(new Image(direction + i + ".png"));
        }
    }

    private void spriteChange(Player player, Key key, ArrayList<Image> spriteSet){
        if(spriteHasMoved == true && sprite != null && lastkey != key){
            canvas.remove(sprite);
            frameNumber = 0;
            spriteHasMoved = false;
        }else if(lastkey == key && sprite != null){
            canvas.remove(sprite);
        }
        sprite = spriteSet.get(frameNumber);
        generateSprite(sprite, player);
        lastkey = key;
        spriteHasMoved = true;
    }
}
