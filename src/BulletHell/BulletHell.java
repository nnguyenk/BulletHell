package BulletHell;

import BulletHell.BulletTypes.BlueBullet;
import BulletHell.BulletTypes.RedBullet;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;

public class BulletHell {
    CanvasWindow canvas;
    Character character;
    private int characterLives = 1;

    public BulletHell(){
        canvas = new CanvasWindow("Stage 1", 800, 800);
    }

    public static void main(String[] args) {
        BulletHell bulletHell = new BulletHell();
        bulletHell.start();
    }

    public void start(){
        createCharacter(0.1);
        BlueBullet bullet = new BlueBullet(canvas, 315, 5);
        canvas.add(bullet.getShape());
        RedBullet bullet2 = new RedBullet(canvas, 65, 8);
        canvas.add(bullet2.getShape());
        canvas.animate(() -> {
            bullet.updatePosition();
            bullet2.updatePosition();
            character.bulletHitsCharacter(bullet);
            character.bulletHitsCharacter(bullet2);
        });
    }

    public void createCharacter(double dt){
        character = new Character(canvas);
        canvas.add(character.getcharacterShape());
        canvas.onKeyDown(event -> {
            if (event.getKey() == Key.LEFT_ARROW) {
                character.moveLeft(dt);
            }
            if (event.getKey() == Key.RIGHT_ARROW) {
                character.moveRight(dt);
            }
            if (event.getKey() == Key.UP_ARROW) {
                character.moveUp(dt);
            }
            if (event.getKey() == Key.DOWN_ARROW) {
                character.moveDown(dt);
            }
        });
    }

}
