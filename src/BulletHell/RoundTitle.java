package BulletHell;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;

public class RoundTitle {
    private CanvasWindow canvas;
    private GraphicsText roundTitle;
    private int roundnumber = 1;
    
    public RoundTitle(CanvasWindow canvas){
        this.canvas = canvas;
        roundTitle = new GraphicsText();
        roundTitle.setCenter(300, 25);
        canvas.add(roundTitle);
    }

    public void changeTitle(){
        roundTitle.setText("BulletHell Round" + roundnumber);
        roundnumber ++;
    }
}
