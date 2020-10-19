package BulletHell;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;

public class GameDescription {
    CanvasWindow canvas;
    GraphicsText welcome;
    GraphicsText gameRules;

    public GameDescription(CanvasWindow canvas){
        this.canvas = canvas;

        welcome = new GraphicsText();
        welcome.setFont("Times New Roman", FontStyle.BOLD_ITALIC, 30);
        welcome.setCenter(canvas.getWidth() / 2, 100);

        gameRules = new GraphicsText();
        gameRules.setFont("Bradley Hand", FontStyle.ITALIC, 15);
        gameRules.setCenter(canvas.getWidth() / 2, 400);
    }

    public void addRules(){
        welcome.setText("Welcome to Bullet Hell!");

        gameRules.setText("Here are the rules: Move around with the arrow keys. Use power ups with q and e. \n"
                            + "You have 3 lives, and will go invulnerable for some time upon being hit by bullets \n"
                            + "Once all bullets on the screen disappear, you have won the round and will proceed to the next! \n"
                            + "Beware, blue bullets freeze you.\n"
                            + "Press space to start game");


        canvas.add(welcome);
        canvas.add(gameRules);
    }

//While keyPress is false, keep repeating addRules

    public void beginGame(){
        canvas.remove(welcome);
        canvas.remove(gameRules);
        canvas.pause(100);
    }
}
