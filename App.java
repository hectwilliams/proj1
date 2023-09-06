package jx_review.java_fun_child.proj1_submodule;

import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List; 


public class App extends JFrame {

    public JLabel llabel = null; 
    public Button bbutton = null;
    public JPanel jjpanel = null; 

    Random rand = new Random();

    public App() {

        this.setTitle("Java GridBagLayout");
        this.setSize(20, 20);

        GraphicsDevice gd = this.getGraphicsConfiguration().getDevice();
        this.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
        
        
        /*  Layout + */
        GridBagLayout gridlayout = new GridBagLayout();
        System.out.println(gridlayout.columnWidths);

        this.getContentPane().setLayout(gridlayout); // getContentPane is Container 

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        /* ERROR ICON */
        JLabel label = this.getImageLabel("img");
        llabel = label;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(
            0, // pad from top 
        0,  // pad left side 
        100,  // pad from bottom 
        80  // pad right side 
        );
        this.getContentPane().add(label, c);
        this.resetConstraints(c);

        /* MESSAGES */
        String [] strs = {"Click a button"};
        JPanel containerLabelBar = new JPanel();
        FlowLayout layoutflow = new FlowLayout();
        layoutflow.setAlignment(FlowLayout.CENTER);
        containerLabelBar.setLayout(layoutflow);
        for (String s: strs) {
            JLabel label_ = new JLabel(s);
            label_.setForeground(  Color.DARK_GRAY ); // new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())
            containerLabelBar.add(label_);
        }
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 100,0);
        this.getContentPane().add(containerLabelBar,c);
        this.resetConstraints(c);


        /* BUTTONS  */
        class JButtonCustom extends JButton {
            public App app = null;   
            JButtonCustom(String text, App app) {
                super(text);
                this.app = app;
            }
        }

        JButtonCustom button = null;
        List<JButtonCustom> buttons = new ArrayList<>();
        buttons.add(new JButtonCustom("rk",this));
        buttons.add(new JButtonCustom("goku", this));
        buttons.add(new JButtonCustom("jigen", this));

        c.gridx =  0;
        c.gridy =  0;
        this.getContentPane().add(buttons.get(0), c);

        c.gridx = 1;
        c.gridy =  0;        
        this.getContentPane().add(buttons.get(1), c);

        c.gridx = 2;
        c.gridy =  0;        
        this.getContentPane().add(buttons.get(2), c);

        /* 
            PANEL
             -> FRAME
        */
        
        JPanel panel = new JPanel(); 
        jjpanel = panel;
        panel.setVisible(true);
        panel.setBackground(new Color(0xF1C716));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 360;      //make this component tall
        c.ipadx = 420;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        
        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage img = null;
                Image scaledImage = null; 
                JButtonCustom currButton = (JButtonCustom) e.getSource() ;
                String key = currButton.getText();
                App app = currButton.app;
                
                try {
                    /* inherited button was used to bring App into scope */
                    img = ImageIO.read(new File( app.getImgURL(key)  )  );
                    scaledImage = img.getScaledInstance(panel.getWidth(), panel.getHeight(), panel.getHeight())    ;
                    
                    panel
                    .getGraphics()
                    .drawImage(scaledImage, 0, 0, panel);

                   app.updateLabelIcon(app.llabel, "img2");


                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

        }; 
        
        for (int i = 0; i < buttons.size(); i++) {
            button = buttons.get(i);
            button.addActionListener( listener );
        }

        /* top button click changes the */
        this.getContentPane().add(panel, c);
        this.resetConstraints(c);

        /* LAST BUTTON */
        button = new JButtonCustom( "Reset", this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        // c.weighty = 0.90; 
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(2, 0, 0, 0);
        c.gridx = 2;
        c.gridy = 2;
        // c.gridwidth = 2;

        this.getContentPane().add(button, c);
        this.resetConstraints(c);

        this.setVisible(true);
        button.addActionListener( event -> {
            JOptionPane.showMessageDialog( new Frame(), "Resetting .. .");
            this.updateLabelIcon(this.llabel, "img");
            
            this.jjpanel.setBackground(null);
            this.jjpanel.setBackground(new Color(0xFFFFFF));

        });

        this.addWindowListener( 
            new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent we) {
                    // we.getWindow().dispose();
                    System.exit(0);
                }
            }
        );
    }


// /Users/hectorwilliams/Documents/dev/repos/AlgorithmBook/jx_review/java_fun_child/proj1_submodule/App.java
    public String getImgURL (String key) {
        String filePath = Paths.get("jx_review", "java_fun_child", "proj1_submodule", "config.json").toAbsolutePath().normalize().toString();
        JSONObject jsonObject = null;
        String result = ""; 

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            jsonObject = new JSONObject(content);
            result = jsonObject.getString(key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace(); 
        } catch(IOException e) {
            e.printStackTrace();
        }

        return result; 
    }

    public void resetConstraints(GridBagConstraints c) {
        c.insets = new Insets (0,0,0,0);  // reset insets 
        c.ipadx = 0;
    }

    public JLabel getImageLabel(String key) {
        Image dimg = null;
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(this.getImgURL("img")));
            dimg = img.getScaledInstance(10, 10, Image.SCALE_SMOOTH);

        } catch(IOException e) {
            e.printStackTrace();
        }

        ImageIcon icon = new ImageIcon(dimg) ;

        return new JLabel(icon);
    }

    public void updateLabelIcon (JLabel label, String jsonKey) {
        Image dimg = null;
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(this.getImgURL(jsonKey)));
            dimg = img.getScaledInstance(10, 10, Image.SCALE_SMOOTH);

        } catch(IOException e) {
            e.printStackTrace();
        }

        ImageIcon icon = new ImageIcon(dimg) ;
        label.setIcon(icon);
    }
    

    public static void main (String [] args) {
        App app = new App();
    }
}
