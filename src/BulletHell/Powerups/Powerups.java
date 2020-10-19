package BulletHell.Powerups;

import edu.macalester.graphics.GraphicsGroup;

public interface Powerups {
    public void reduceCooldown(double dt);
    public void activate();
    public void reduceDuration(double dt);
    public GraphicsGroup getShape();
}
