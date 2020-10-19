package BulletHell;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;

public class EnergyManager {
    private Rectangle energyBox;
    private double energy;
    private CanvasWindow canvas;
    private double boxSize = 15;

    public EnergyManager(CanvasWindow canvas) {
        this.canvas = canvas;
    }


    public void summonEnergy(double dt){
        energyBox = new Rectangle(25, 10, increaseEnergy(dt), boxSize);
        energyBox.setFillColor(Color.GREEN);
        canvas.add(energyBox);  
    }

    public double increaseEnergy(double dt) {
        if(energy <= boxSize){
            energy += boxSize/100;
        }
        return energy;
    }
}
