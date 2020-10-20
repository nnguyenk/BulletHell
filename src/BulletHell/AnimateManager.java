package BulletHell;

import java.util.ArrayList;

import javax.print.attribute.standard.DialogOwner;

import edu.macalester.graphics.*;

public class AnimateManager {
    CanvasWindow canvas;

    ArrayList<Image> forward = new ArrayList<Image>();
    ArrayList<Image> right = new ArrayList<Image>();
    ArrayList<Image> left = new ArrayList<Image>();
    ArrayList<Image> backward = new ArrayList<Image>();
    

    private int frameNumber = 0;
    private boolean lastFrame = false;

    public AnimateManager(CanvasWindow canvas){
        this.canvas = canvas;
    }
    
    private void createCycle(Player player, ArrayList<Image> imageList, String direction){
        for (int i = 0; i <= 3; i++) {
            imageList.add(new Image(direction + i + ".png"));
            imageList.get(i).setMaxHeight(player.getPlayerShape().getHeight() * 3);
            imageList.get(i).setMaxHeight(player.getPlayerShape().getWidth() * 3);
            imageList.get(i).setCenter(player.getPlayerShape().getCenter());
        }
    }

    public void updateCycle(Player player, ArrayList<Image> imageList, String direction){
        createCycle(player, imageList, direction);
        if(frameNumber == 0){
            System.out.println(frameNumber);
            canvas.add(imageList.get(frameNumber));
            frameNumber += 1;
            if(lastFrame == true){
                canvas.remove(imageList.get(3));
                lastFrame = false;
            }
        }else if(frameNumber == 1){
            System.out.println(frameNumber);
            canvas.add(imageList.get(frameNumber));
            canvas.remove(imageList.get(frameNumber - 1));
            frameNumber += 1;
        }else if(frameNumber == 2){
            System.out.println(frameNumber);
            canvas.add(imageList.get(frameNumber));
            canvas.remove(imageList.get(frameNumber - 1));
            frameNumber += 1;
        }else if(frameNumber == 3){
            System.out.println(frameNumber);
            canvas.add(imageList.get(frameNumber));
            canvas.removeAll();
            frameNumber = 0;
            lastFrame = true;
        }
    }

    

   

    public void forwardWalk(Player player){
        updateCycle(player, forward, "forward");
    }

    public void rightWalk(Player player){
        updateCycle(player, right, "right");
    }

    public void leftWalk(Player player){
        updateCycle(player, left, "left");
    }

    public void backwardWalk(Player player){
        updateCycle(player, backward, "backward");
    }

}
