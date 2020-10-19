package BulletHell;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;

public class EnergyManager {
    private Rectangle energyBox;
    private double energy;
    private CanvasWindow canvas;
    private double boxXSize = 150;
    private int boxYSize = 15;
    private Color color = Color.RED;

    public EnergyManager(CanvasWindow canvas) {
        this.canvas = canvas;
    }

    public void summonEnergy(){
        energyBox = new Rectangle(25, 10, increaseEnergy(), boxYSize);
        energyBox.setFillColor((new Color(
            color.getRed(),
            (int)increaseEnergy(),
            color.getGreen()
            )));
        canvas.add(energyBox);  
    }

    public double increaseEnergy() {
        if(energy <= boxXSize){
            energy += boxXSize/1000;
        }
        return energy;
    }

    public boolean fullEnergy(){
        if(increaseEnergy() >= boxXSize){
            energyBox.setFillColor(Color.GREEN);
            return true;
        }
        return false;
    }
}
