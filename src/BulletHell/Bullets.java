package BulletHell;

import java.util.List;

import edu.macalester.graphics.GraphicsObject;

public interface Bullets {
    public void updatePosition();
    public List<GraphicsObject> hit();
}

// GraphicsGroup to contain all the bullet objects
// BulletManager
// Implement lives
// Implement shoot?