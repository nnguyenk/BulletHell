package BulletHell;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;

/**
 * The headline of each game, which changes depending on the current level.
 */
public class RoundTitle {
    private GraphicsText roundTitle;
    
    public RoundTitle(CanvasWindow canvas) {
        roundTitle = new GraphicsText();
        roundTitle.setCenter(300, 25);
        canvas.add(roundTitle);
    }

    /**
     * Changes the round title at the top of the screen to match the round
     */
    public void changeTitle(int roundNumber){
        roundTitle.setText("Bullet Hell Round " + roundNumber);
    }
}
