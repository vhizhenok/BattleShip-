package arei.sb.seafight;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Dialog {
    private long SLEEP = 1000;
    private Image[] images;
    private BgPanel dialog;
    private boolean switchImages = true;
    private boolean stopDialog = false;
    private ArrayList<Image> list;
    private Random rand = new Random();
    private int index = -1;

    public Dialog(Image[] images, BgPanel dialog){
        this.images = images;
        this.dialog = dialog;
        list = new ArrayList<Image>(Arrays.asList(images));
    }
    
    public void setSleep(long sleep){
        SLEEP = sleep;
        
    }

    public void setSwitchImages(boolean status){
        switchImages = status;
        if (index != -1 && status && !list.isEmpty()){
            dialog.reloadImage(list.get(index));
        }
    }
    
    public void setStopDialog(boolean status){
        stopDialog = status;
    }

    public void run(){
        int oldIndex = -1;
        while (!stopDialog){
            if (!switchImages) continue;
            
            if (list.isEmpty()){
                list = new ArrayList<Image>(Arrays.asList(images));
                oldIndex = -1;
                System.out.println("------------------");
            }
            
            if (oldIndex != -1) list.remove(oldIndex);
            if (list.isEmpty()) continue;
            index = rand.nextInt(list.size());
            oldIndex = index;
            dialog.reloadImage(list.get(index));
            System.out.println(list.get(index));
            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}


    