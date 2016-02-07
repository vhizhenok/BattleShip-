package arei.sb.seafight;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javazoom.jl.player.Player;

public class Login extends javax.swing.JFrame {
    static String HOST;
    static String DATABASE;
    static String USER;
    static String DATABASEPASSWORD;
    static String PORT;
    static String name;
        
    private BgPanel fone;
    private BgPanel dialog;
    private BgPanel registration;
    private BgPanel sign_in;
    private BgPanel editLogin;
    private BgPanel editPassword;
    
    private JTextField editTextLogin;
    private JTextField editTextPassword;
    
    private SoundWorker sound = new SoundWorker();
    
    private Image reg, reg_focus;
    private Image signIn, signIn_focus;
    private Image edit, edit_focus;
    private Image error_empty, error, error_login, error_reg_login;
    private Image succesfull_login;
    private Image waiting;
    
    private Image imClickedReg, imClickedSignIn;
    
    static Image[] jockes = new Image[7];
    
    private Dialog pirat;
    private Pirate pir = new Pirate();
    
    private boolean clickedReg = false;
    private boolean clickedLog = false;

    private void uploadingDataBaseInformation() {
        try {
            Scanner sc = new Scanner(new File("C:\\Users\\vlad\\Documents\\NetBeansProjects\\SeaFight\\src\\arei\\sb\\seafight\\databaseinformation.info"));
            HOST = sc.nextLine();
            DATABASE = sc.nextLine();
            USER = sc.nextLine();
            DATABASEPASSWORD = sc.nextLine();
            PORT = sc.nextLine();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    class Pirate extends SwingWorker<Void, Void>{
        @Override
        public Void doInBackground() {
            pirat = new Dialog(jockes, dialog);
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
    
    class Regestration extends SwingWorker<Void, Void>{
        @Override
        public Void doInBackground() {
                if (editTextLogin.getText().equals("") || editTextPassword.getText().equals("")){
                    /*JOptionPane.showMessageDialog(Login.this, "Введите логин и пароль!", "Ошибка!", JOptionPane.WARNING_MESSAGE);*/
                    if (error_empty == null){
                        try {
                            error_empty = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\error_empty.jpg"));
                        }
                        catch(IOException ex){
                            ex.printStackTrace();
                        }
                    }
                    dialog.reloadImage(error_empty);
                    return null;
                }
                if (waiting == null){
                    try {
                        waiting = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\waiting.jpg"));
                    }
                    catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
                dialog.reloadImage(waiting);
                DataBaseConnector data = null;
                try {
                    data = new DataBaseConnector("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useUnicode=true&characterEncoding=UTF-8", USER, DATABASEPASSWORD);
                    ResultSet result = data.query("select * from users where login = '" + editTextLogin.getText() + "'");
                    if (result.next()){
                        /*JOptionPane.showMessageDialog(Login.this, "Логин уже занят, придумайте новый!", "Ошибка!", JOptionPane.WARNING_MESSAGE);*/
                        if (error_reg_login == null){
                            try {
                                error_reg_login = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\error_reg_login.jpg"));
                            }
                            catch(IOException ex){
                                ex.printStackTrace();
                            }
                        }
                        dialog.reloadImage(error_reg_login);
                    }
                    else {
                        name = editTextLogin.getText();
                        data.select("insert into users values (null, '" + editTextLogin.getText() + "', '" + editTextPassword.getText() + "', 0, 0, 'no')");
                        /*JOptionPane.showMessageDialog(Login.this, "Поздравляю, вы успешно зарегестрированы. Можете войти в игру!", "Успешная регистрация!", JOptionPane.PLAIN_MESSAGE);*/
                        if (succesfull_login == null){
                            try {
                                succesfull_login = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\succesfull_login.jpg"));
                            }
                            catch(IOException ex){
                                ex.printStackTrace();
                            }
                        }
                        dialog.reloadImage(succesfull_login);
                    }
                }catch(SQLException ex){
                    /*JOptionPane.showMessageDialog(Login.this, "Ошибка получения данных, попробуйте еще раз!", "Ошибка!", JOptionPane.ERROR_MESSAGE);*/
                    if (error == null){
                        try {
                            error = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\error.jpg"));
                        }
                        catch(IOException ex1){
                            ex1.printStackTrace();
                        }
                    }
                    dialog.reloadImage(error);
                    try {
                        data.close();
                    } catch (SQLException ex1) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            return null;
        }
        
        @Override
        public void done() 
        {
          clickedReg = false;  
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
            if (pirat != null) pirat.setSwitchImages(true);
        }
    }
    
    class LoginInGame extends SwingWorker<Void, Void>{
         @Override
        public Void doInBackground() {
            if (editTextLogin.getText().equals("") || editTextPassword.getText().equals("")){
                JOptionPane.showMessageDialog(Login.this, "Введите логин и пароль!", "Ошибка!", JOptionPane.WARNING_MESSAGE);
                return null;
            }
            if (waiting == null){
                try {
                    waiting = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\waiting.jpg"));
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            dialog.reloadImage(waiting);
            DataBaseConnector data = null;
            try {
                data = new DataBaseConnector("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useUnicode=true&characterEncoding=UTF-8", USER, DATABASEPASSWORD);
                ResultSet result = data.query("select * from users where login = '" + editTextLogin.getText() + "' and password = '" + editTextPassword.getText() + "'");
                if (!result.next()){
                    /*JOptionPane.showMessageDialog(Login.this, "Неверный логин или пароль!", "Ошибка!", JOptionPane.WARNING_MESSAGE);*/
                    if (error_login == null){
                        try {
                            error_login = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\error_login.jpg"));
                        }
                        catch(IOException ex){
                            ex.printStackTrace();
                        }
                    }
                    dialog.reloadImage(error_login);
                }
                else {
                    name = editTextLogin.getText();
                    data.select("update users set find = 'no' where login = '" + editTextLogin.getText() +  "'");
                    Main main = new Main(Login.this.getLocation());
                    main.setVisible(true);
                    if (pirat != null) pirat.setStopDialog(true);
                    Login.this.setVisible(false);
                }
                
             } catch (SQLException ex) {
                 /*JOptionPane.showMessageDialog(Login.this, "Ошибка соединения с сервером, попробуйте еще раз!", "Ошибка!", JOptionPane.ERROR_MESSAGE);*/
                if (error == null){
                    try {
                        error = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\error_login.jpg"));
                    }
                    catch(IOException ex1){
                        ex1.printStackTrace();
                    }
                   }
                dialog.reloadImage(error);
                try {
                    data.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
                }
             }
            return null;
        }
        @Override
        public void done() 
        {
            clickedLog = false;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (pirat != null) pirat.setSwitchImages(true);
        }
    }

    private void downloadImages() {
        try {
            reg = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\login\\reg.png"));
            reg_focus = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\login\\reg_focus.png"));
            
            signIn = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\login\\sign_in.png"));
            signIn_focus = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\login\\sign_in_focus.png"));
            
            edit = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\login\\edit.png"));
            edit_focus = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\login\\edit_focus.png"));
            
            imClickedReg = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\clicked\\reg.jpg"));
            imClickedSignIn = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\clicked\\sign_in.jpg"));
            
            for (int i = 0; i < jockes.length; i++){
               jockes[i] = ImageIO.read(new File("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\joke(" + (i + 1) + ").jpg"));
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Login() {
        uploadingDataBaseInformation();
        initComponents();
        setBounds(0, 0, 800, 600);
        setResizable(false); 
        setLocationRelativeTo(null);
        downloadImages();
        //Addition all panels on the main window
        addMyPanels();
        //Addition listeners for all panels
        addMyListeners();
        sound.execute();
        pir.execute();
    }
    
    class SoundWorker extends SwingWorker<Void, Void>{
        @Override
        public Void doInBackground() {
            try{
                FileInputStream stream = new FileInputStream("C:\\Users\\vlad\\Desktop\\Пираты\\music\\hearthstone.mp3");
                //FileInputStream stream = new FileInputStream("C:\\Users\\vlad\\Desktop\\Пираты\\music\\1.mp3");
                Player player = new Player(stream);
                player.play();
                System.out.println("hello");
            }catch(Exception ex){
                ex.printStackTrace();
            }
            doInBackground();
            return null;
        }
    }
    
    private void addMyPanels(){
        fone = new BgPanel("C:\\Users\\vlad\\Desktop\\Пираты\\fon800x600dialog.jpg");
        setContentPane(fone);
        Container cont = getContentPane();
        cont.setLayout(null);
        
        editTextLogin = new JTextField("");
        editTextLogin.setBounds(220, 148, 430, 66);
        editTextLogin.setFont(new Font("Time New Roman", Font.BOLD, 25));
        editTextLogin.setForeground(Color.BLACK);
        editTextLogin.setBackground(new Color(255, 255, 255, 0));
        editTextLogin.setBorder(null);
        cont.add(editTextLogin);
        
        editTextPassword = new   JTextField("");
        editTextPassword.setBounds(220, 203, 430, 66);
        editTextPassword.setFont(new Font("Time New Roman", Font.BOLD, 25));
        editTextPassword.setForeground(Color.BLACK);
        editTextPassword.setBackground(new Color(255, 255, 255, 0));
        editTextPassword.setBorder(null);
        cont.add(editTextPassword);
        
        editLogin = new BgPanel(edit);
        editLogin.setBounds(180, 155, 500, 66);
        cont.add(editLogin);
        
        editPassword = new BgPanel(edit);
        editPassword.setBounds(180, 210, 500, 66);
        cont.add(editPassword);
        
        registration = new BgPanel(reg);
        registration.setBounds(195, 265, 240, 66);
        cont.add(registration);
        
        sign_in = new BgPanel(signIn);
        sign_in.setBounds(440, 265, 240, 66);
        cont.add(sign_in);
        
        
        dialog = new BgPanel("C:\\Users\\vlad\\Desktop\\Пираты\\dialog\\log_pas.jpg");
        dialog.setBounds(254, 400, 386, 156);
        cont.add(dialog);
    }
    
    private void addMyListeners(){
        /* Кнопка регистрации */
        registration.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (Login.this != null)
                registration.reloadImage(reg_focus);
                dialog.reloadImage(imClickedReg);
                if (pirat != null) pirat.setSwitchImages(false);
            }
        });
        registration.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (Login.this != null)
                registration.reloadImage(reg);
                if (pirat != null && !clickedReg) pirat.setSwitchImages(true);
            }
        });
        
        /* Кнопка входа */
        sign_in.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (Login.this != null)
                sign_in.reloadImage(signIn_focus);
                dialog.reloadImage(imClickedSignIn);
                if (pirat != null) pirat.setSwitchImages(false);
            }
        });
        sign_in.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (Login.this != null)
                sign_in.reloadImage(signIn);
                if (pirat != null && !clickedLog) pirat.setSwitchImages(true);
            }
        });
        
        /* Поле логина*/
        editTextLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (Login.this != null)
                editLogin.reloadImage(edit_focus);
            }
        });
        editTextLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (Login.this != null)
                editLogin.reloadImage(edit);
            }
        });
        
        /* Поле пароля*/
        editTextPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (Login.this != null)
                editPassword.reloadImage(edit_focus);
            }
        });
        editTextPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (Login.this != null)
                editPassword.reloadImage(edit);
            }
        });
        
        registration.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (clickedReg) return;
                clickedReg = true;
                new Regestration().execute();
                if (pirat != null) pirat.setSwitchImages(false);
            }
        });
        
        sign_in.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (clickedLog) return;
                clickedLog = true;
                new LoginInGame().execute();
                if (pirat != null) pirat.setSwitchImages(false);
            }
        });
        
        editTextLogin.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (getLength() < 10) {
                        super.insertString(offs, str, a);
                }
            }
        });
        
        editTextPassword.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (getLength() < 10) {
                        super.insertString(offs, str, a);
                }
            }
        });
        
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
