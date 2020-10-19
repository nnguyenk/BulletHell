package BulletHell;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;

public class RoundTitle {
    private GraphicsText roundTitle;
    
    public RoundTitle(CanvasWindow canvas) {
        roundTitle = new GraphicsText();
        roundTitle.setCenter(300, 25);
        canvas.add(roundTitle);
    }

    public void changeTitle(int roundNumber){
        roundTitle.setText("BulletHell Round " + roundNumber);
    }
}
