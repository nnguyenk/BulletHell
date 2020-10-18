package BulletHell.Powerups;

import BulletHell.BulletHell;

public interface Powerups {
    public void activate();
    public boolean inEffect();
    public void reduceDuration(double dt);
}
