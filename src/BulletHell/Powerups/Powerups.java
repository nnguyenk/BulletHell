package BulletHell.Powerups;

import edu.macalester.graphics.GraphicsGroup;

public interface Powerups {
    public void reduceCooldown(double dt);
    public boolean onCooldown();
    public void activate();
    public boolean inEffect();
    public void reduceDuration(double dt);
    public GraphicsGroup getShape();
}
