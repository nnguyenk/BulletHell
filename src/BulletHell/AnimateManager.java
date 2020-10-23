package BulletHell;

import java.util.ArrayList;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.Key;

/**
 * A class that contains and controls the sprite of the player.
 */
public class AnimateManager {
    private CanvasWindow canvas;
    
    private Image sprite;
    private int frameNumber = 0;
    private boolean L = false;
    private boolean R = false;
    private boolean D = false;
    private boolean U = false;

    private boolean spriteHasMoved = false;
    private boolean spriteWasFrozen = false;
    private Key lastkey;

    ArrayList<Boolean> directions = new ArrayList<Boolean>();
    ArrayList<Image> down = new ArrayList<Image>();
    ArrayList<Image> left = new ArrayList<Image>();
    ArrayList<Image> right = new ArrayList<Image>();
    ArrayList<Image> up = new ArrayList<Image>();
    ArrayList<Image> frozen = new ArrayList<Image>();


    public AnimateManager(CanvasWindow canvas) {
        this.canvas = canvas;
        directions.add(L);
        directions.add(R);
        directions.add(D);
        directions.add(U);

        fillImagelist(down, "down");
        fillImagelist(left, "left");
        fillImagelist(right, "right");
        fillImagelist(up, "up");
        frozen.add(new Image("frozen.png"));
    }

    /*
     * Animates the sprite based on player inputs.
     */
    public void animateSprite(Key key, Player player, CanvasWindow canvas, boolean isFrozen) {
        if (frameNumber == 4) {
            frameNumber = 0;
        }
        if (key == Key.LEFT_ARROW) {
           spriteChange(player, key, left, isFrozen);
        }
        if (key == Key.RIGHT_ARROW) {
            spriteChange(player, key, right, isFrozen);
        }
        if (key == Key.UP_ARROW) {
            spriteChange(player, key, up, isFrozen);
        }
        if (key == Key.DOWN_ARROW) {
            spriteChange(player, key, down, isFrozen);
        }
        getSprite();
    }

    /*
     * Gets the current sprite image.
     */
    public Image getSprite() {
        return sprite;
    }

    /*
     * Adds the sprite to the canvas based on the player's x & y.
     */
    private void generateSprite(Image sprite, Player player) {
        sprite.setMaxHeight(90);
        sprite.setMaxWidth(60);
        sprite.setCenter(player.getPlayerHitbox().getCenter());
        canvas.add(sprite);
        if (!spriteWasFrozen) {
            frameNumber += 1;
        }
        
    }

    /*
     * Gets the shape of the bullet.
     */
    public void placeSprite(Player player) {
        if (sprite != null) {
            canvas.add(sprite);
            sprite.setCenter(player.getPlayerHitbox().getCenter());
            canvas.remove(sprite);
        }
    }

    /*
     * Adds images to image arraylists.
     */
    private void fillImagelist(ArrayList<Image> images, String direction) {
        for(int i = 0; i < 4; i++) {
            images.add(new Image(direction + i + ".png"));
        }
    }

    /*
     * Handles animation frames for each sprite.
     */
    private void spriteChange(Player player, Key key, ArrayList<Image> spriteSet, boolean isFrozen) {
        spriteThaw(player, isFrozen);
        if (spriteHasMoved == true && sprite != null && lastkey != key && !isFrozen) {
            canvas.remove(sprite);
            frameNumber = 0;
            spriteHasMoved = false;
        } else if (lastkey == key && sprite != null && !isFrozen && !spriteWasFrozen) {
            canvas.remove(sprite);
        }
        if (!isFrozen) {
            sprite = spriteSet.get(frameNumber);
            generateSprite(sprite, player);
            lastkey = key;
            spriteHasMoved = true;
        }
    }

    /*
     * Freezes the sprite if a blue bullet is hit.
     */
    public void spriteFreeze(boolean isFrozen, Player player) {
        if (isFrozen) {
            if (sprite != null) {
                canvas.remove(sprite);
            }
            sprite = frozen.get(0);
            generateSprite(sprite, player);
            spriteWasFrozen = true;
        }
    }

    /*
     * Changes sprite image back to normal unfrozen sprite.
     */
    private void spriteThaw(Player player, boolean isFrozen) {
        if (spriteWasFrozen){
            if (sprite != null) {
                canvas.remove(sprite);
            }
            generateSprite(sprite, player);
            spriteWasFrozen = false;
        }
    }
}
