package BulletHell;

import BulletHell.BulletTypes.BlueBullet;
import BulletHell.BulletTypes.PlayerBullet;
import BulletHell.BulletTypes.RedBullet;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;

public class BulletHell {
    private CanvasWindow canvas;
    private Character character;
    private int currentLife;

    // private int characterLives = 3;
    private static final int MAX_LIFE = 3;

    public BulletHell(){
        canvas = new CanvasWindow("Stage 1", 800, 800);
    }

    public static void main(String[] args) {
        BulletHell bulletHell = new BulletHell();
        bulletHell.start();
    }

    public void start(){
        createCharacter(0.1);
        BlueBullet bullet = new BlueBullet(canvas, Utility.randomInt(0, 360), 5);
        canvas.add(bullet.getShape());
        RedBullet bullet2 = new RedBullet(canvas, Utility.randomInt(0, 360), 8);
        canvas.add(bullet2.getShape());

        PlayerBullet bullet3 = new PlayerBullet(canvas, 575, 7);
        currentLife = MAX_LIFE;
        

        canvas.animate(() -> {
            if (currentLife > 0) {
                bullet.updatePosition();
                bullet2.updatePosition();

                bullet3.updatePosition();
                
                character.bulletHitsCharacter(bullet);
                character.bulletHitsCharacter(bullet2);

                // character.bulletHitsCharacterAlt(bullet, characterLives);
                // character.bulletHitsCharacterAlt(bullet2, characterLives);

                // bullet3.bulletHitsBullet(bullet);
                // bullet3.bulletHitsBullet(bullet2);
                if (character.bulletHitsCharacter(bullet)) { //if the player gets hit
                    currentLife -= 1;
                    bullet.deflectHorizontal();
                    bullet.deflectVertical();
                    // canvas.remove(character.getcharacterShape());
                    // createCharacter(0.1);
                    // bullet.wait(2000);
                    // wait
                    // player goes translucent for 1 second 
                    // if (!brickManager.bricksStillExist()){ //win condition
                    //     animating = false;
                    //     System.out.println("Congratulations! You have WON!");
                    //     canvas.closeWindow();
                    // }
                    }
                }
            else { //breaks out of the animation loop
                canvas.removeAll();
                System.out.println("You have LOST!");
                canvas.closeWindow();
            }
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
