package arei.sb.seafight;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BgPanel extends JPanel{
    private String path;
    private Image im = null;
    BgPanel(String path){
        this.path = path;
    }
    BgPanel(Image image){
        im = image;
    }

    public void reloadImage(Image image){
        im = image;
        paintComponent(this.getGraphics());
    }

    public void paintComponent(Graphics g){
        if (im == null){
            try {
                im = ImageIO.read(new File(path));
            } catch (IOException e) {}
        }
        g.drawImage(im, 0, 0, null); 
    }
}
