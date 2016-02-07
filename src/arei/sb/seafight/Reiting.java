package arei.sb.seafight;

import static arei.sb.seafight.Login.HOST;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;


public class Reiting extends javax.swing.JFrame {
    
    private Image back;
    private Image back_focus;
    private Image find;
    private Image not_find;
    private Image waiting;
    
    private BgPanel backPanel;
    private BgPanel fone;
    private BgPanel dialog;
    
    private JLabel[] players = new JLabel[5];
    private JLabel[] wins = new JLabel[5];
    
    private boolean clickedBack = false;
    
    UploadReiting reit = new UploadReiting();
    
    class UploadReiting extends SwingWorker<Void, Void>{
        @Override
        public Void doInBackground() {
            DataBaseConnector data = null;
            try {
                data = new DataBaseConnector("jdbc:mysql://" + Login.HOST + ":" + Login.PORT + "/" + Login.DATABASE + "?useUnicode=true&characterEncoding=UTF-8", Login.USER, Login.DATABASEPASSWORD);
                ResultSet result = data.query("select login, countWin from users order by countWin desc, login limit 5");
                int index = 0;
                while (result.next()){
                    players[index].setText(result.getString("login"));
                    wins[index].setText(result.getInt("countWin") + "");
                    index++;
                }
                boolean met = false;
                String name = Login.name;
                for (int i = 0; i < 5; i++){
                    if (name.equals(players[i].getText())){
                        met = true;
                        break;
                    }
                }
                if (met){
                    if (find == null){
                        try {
                            find = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\reiting_dialog\\find.jpg"));
                        }
                        catch(IOException ex){
                            ex.printStackTrace();
                        }
                    }
                    dialog.reloadImage(find);
                }
                else {
                    if (not_find == null){
                        try {
                            not_find = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\reiting_dialog\\not_find.jpg"));
                        }
                        catch(IOException ex){
                            ex.printStackTrace();
                        }
                    }
                    dialog.reloadImage(not_find);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            try {
                data.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        }
        
        @Override
        public void done() 
        {
            
        }
    }

    private void downloadImages() {
        try{
            back = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\main_menu\\back.png"));
            back_focus = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\main_menu\\back_focus.png"));
            waiting = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\reiting_dialog\\waiting.jpg"));
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void addMyPanels() {
        fone = new BgPanel("C:\\Users\\vlad\\Desktop\\Пираты\\reiting800x600.jpg");
        setContentPane(fone);
        Container cont = getContentPane();
        cont.setLayout(null);
        
        backPanel = new BgPanel(back);
        backPanel.setBounds(15, 435, 245, 66);
        cont.add(backPanel);
        
        dialog = new BgPanel(waiting);
        dialog.setBounds(268, 400, 334, 131);
        cont.add(dialog);
        
        for (int i = 0; i < 5; i++){
            players[i] = new JLabel();
            players[i].setFont(new Font("Time New Roman", Font.BOLD, 30));
        }
        players[0].setBounds(80, 62, 200, 40);
        players[1].setBounds(80, 130, 200, 40);
        players[2].setBounds(80, 199, 200, 40);
        players[3].setBounds(80, 272, 200, 40);
        players[4].setBounds(80, 340, 200, 40);
        for (int i = 0; i < 5; i++){
            cont.add(players[i]);
        }
        for (int i = 0; i < 5; i++){
            wins[i] = new JLabel();
            wins[i].setFont(new Font("Time New Roman", Font.BOLD, 30));
        }
        wins[0].setBounds(520, 62, 200, 40);
        wins[1].setBounds(520, 130, 200, 40);
        wins[2].setBounds(520, 199, 200, 40);
        wins[3].setBounds(520, 272, 200, 40);
        wins[4].setBounds(520, 340, 200, 40);
        for (int i = 0; i < 5; i++){
            cont.add(wins[i]);
        }
        
    }

    private void addMyListeners() {
        backPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backPanel.reloadImage(back_focus);
            }
        });
        backPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backPanel.reloadImage(back);
            }
        });
        
        backPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (clickedBack) return;
                clickedBack = true;
                Main main = new Main(Reiting.this.getLocation());
                main.setVisible(true);
                Reiting.this.dispose();
                clickedBack = false;
            }
        });
    }
    
    public Reiting() {
        initComponents();
        setBounds(0, 0, 800, 600);
        setResizable(false); 
        downloadImages();
        //Addition all panels on the main window
        addMyPanels();
        //Addition listeners for all panels
        addMyListeners();
        reit.execute();
    }
    
    public Reiting(Point point){
        initComponents();
        setBounds(0, 0, 800, 600);
        setResizable(false); 
        setLocation(point);
        downloadImages();
        //Addition all panels on the main window
        addMyPanels();
        //Addition listeners for all panels
        addMyListeners();
        reit.execute();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(Reiting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reiting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reiting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reiting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reiting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
