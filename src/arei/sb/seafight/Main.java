package arei.sb.seafight;

import java.awt.Container;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

public class Main extends javax.swing.JFrame {
    
    private BgPanel fone;
    private BgPanel findGame;
    private BgPanel dialog;
    private BgPanel captain;
    private BgPanel reiting;
    private BgPanel exit;
    
    private Image imFindGame, imFindGameFocus;
    private Image imCaptain, imCaptainFocus;
    private Image imReiting, imReitingFocus;
    private Image imExit, imExitFocus;
    
    private Image imClickedFind, imClickedCaptain, imClickedExit, imClickedReiting;
   
    private Image velcome;
    
    private Pirate pir = new Pirate();
    
    private Dialog pirat;
    
    private boolean clickedReiting = false;
    
    class Pirate extends SwingWorker<Void, Void>{
        @Override
        public Void doInBackground() {
            pirat = new Dialog(Login.jockes, dialog);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            pirat.run();
            return null;
        }
        
        @Override
        public void done() 
        {
          
        }
    }

    private void downloadImages() {
        try {
           imFindGame = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\main_menu\\find_game.png"));
           imFindGameFocus = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\main_menu\\find_game_focus.png"));
           imCaptain = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\main_menu\\captain.png"));
           imCaptainFocus = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\main_menu\\captain_focus.png"));
           imReiting = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\main_menu\\reiting.png"));
           imReitingFocus = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\main_menu\\reiting_focus.png"));
           imExit = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\main_menu\\exit.png"));
           imExitFocus = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\main_menu\\exit_focus.png"));
           velcome = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\velcome.jpg"));
           
           imClickedFind = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\clicked\\find.jpg"));
           imClickedReiting = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\clicked\\reiting.jpg"));
           imClickedExit = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\clicked\\exit.jpg"));
           imClickedCaptain = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\clicked\\captain.jpg"));
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public Main(){
        initComponents();
        setBounds(0, 0, 800, 600);
        setResizable(false); 
        downloadImages();
        //Addition all panels on the main window
        addMyPanels();
        //Addition listeners for all panels
        addMyListeners();
        pir.execute();
    }
    
    public Main(Point point) {
        initComponents();
        setBounds(0, 0, 800, 600);
        setResizable(false); 
        downloadImages();
        setLocation(point);
        //Addition all panels on the main window
        addMyPanels();
        //Addition listeners for all panels
        addMyListeners();
        pir.execute();
    }
    
    
    private void addMyPanels(){
        fone = new BgPanel("C:\\Users\\vlad\\Desktop\\Пираты\\fon800x600dialog.jpg");
        setContentPane(fone);
        Container cont = getContentPane();
        cont.setLayout(null);
        
        dialog = new BgPanel(velcome);
        dialog.setBounds(254, 400, 386, 156);
        cont.add(dialog);
           
        /*
            Меню поиска игры
        */
        findGame = new BgPanel(imFindGame);
        findGame.setBounds(550, 60, 270, 66);
        cont.add(findGame);
       
        /*
            Каюта капитана
        */
        captain = new BgPanel(imCaptain);
        captain.setBounds(550, 120, 270, 66);
        cont.add(captain);
        
        /*
            Рейтинг
        */
        reiting = new BgPanel(imReiting);
        reiting.setBounds(550, 180, 270, 66);
        cont.add(reiting);
        
        /*
            Выход
        */
        exit = new BgPanel(imExit);
        exit.setBounds(550, 240, 270, 66);
        cont.add(exit);
    }
    
    private void addMyListeners(){
        /*
            Меню поиска игры
        */
        findGame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                findGame.reloadImage(imFindGameFocus);
                dialog.reloadImage(imClickedFind);
                if (pirat != null) pirat.setSwitchImages(false);
            }
        });
        findGame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                findGame.reloadImage(imFindGame);
                if (pirat != null) pirat.setSwitchImages(true);
            }
        });
        
        /*
            Каюта капитана
        */
        captain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                captain.reloadImage(imCaptainFocus);
                dialog.reloadImage(imClickedCaptain);
                if (pirat != null) pirat.setSwitchImages(false);
            }
        });
        captain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                captain.reloadImage(imCaptain);
                if (pirat != null) pirat.setSwitchImages(true);
            }
        });
        
        /*
            Рейтинг
        */
        reiting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reiting.reloadImage(imReitingFocus);
                dialog.reloadImage(imClickedReiting);
                if (pirat != null) pirat.setSwitchImages(false);
            }
        });
        reiting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reiting.reloadImage(imReiting);
                if (pirat != null) pirat.setSwitchImages(true);
            }
        });
        
        /*
            Выход
        */
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exit.reloadImage(imExitFocus);
                dialog.reloadImage(imClickedExit);
                if (pirat != null) pirat.setSwitchImages(false);
            }
        });
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exit.reloadImage(imExit);
                if (pirat != null) pirat.setSwitchImages(true);
            }
        });
        
        reiting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (clickedReiting) return;
                if (pirat != null) pirat.setStopDialog(true);
                clickedReiting = true;
                Reiting reiting = new Reiting(Main.this.getLocation());
                reiting.setVisible(true);
                clickedReiting = false;
                Main.this.dispose();
            }
        });
        
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (pirat != null) pirat.setStopDialog(true);
                System.exit(0);
            }
        });
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Морской бой");
        setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 661, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 392, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
