package jx_review.java_fun_child.proj1_submodule;

import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
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


public class App extends JFrame {

    private JLabel headerLabel1 = null; 
    private JLabel headerLabel2 = null; 
    private Button b1 = null; 
    private Button b2 = null; 
    private Button b3 = null; 
    private Button b4 = null; 
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
            label = new JLabel(s);
            label.setForeground(  Color.DARK_GRAY ); // new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())
            containerLabelBar.add(label);
        }
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 100,0);
        this.getContentPane().add(containerLabelBar,c);
        this.resetConstraints(c);


        /* BUTTONS  */
        JButton button = null;

        button = new JButton("Rurouni Kenshin");
        c.gridx = 0;
        c.gridy =  0;
        this.getContentPane().add(button, c);

        button = new JButton("Goku");
        c.gridx = 1;
        c.gridy =  0;        
        this.getContentPane().add(button, c);
        
        button = new JButton("Jigen");
        c.gridx = 2;
        c.gridy =  0;        
        this.getContentPane().add(button, c);
        
        /* 
        PANEL
         -> FRAME

            YOUTUBE API TBD
         */
        JPanel panel = new JPanel(); 
        panel.setVisible(true);
        panel.setBackground(new Color(0xF1C716));
        panel.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 5));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 320;      //make this component tall
        c.ipadx = 420;      //make this component tall

        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        this.getContentPane().add(panel, c);
        this.resetConstraints(c);

        /* LAST BUTTON */
        button = new JButton( "button 5");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        // c.weighty = 0.90; 
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(2, 0, 0, 0);
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 2;

        this.getContentPane().add(button, c);
        this.resetConstraints(c);

        this.setVisible(true);

        /* */
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

    public String getImgURL () {
        String filePath = Paths.get("jx_review", "java_fun_child", "proj1_submodule", "config.json").toAbsolutePath().normalize().toString();
        JSONObject jsonObject = null;
        String key = "img"; 
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
            img = ImageIO.read(new File(this.getImgURL()));
            dimg = img.getScaledInstance(10, 10, Image.SCALE_SMOOTH);

        } catch(IOException e) {
            e.printStackTrace();
        }

        ImageIcon icon = new ImageIcon(dimg) ;

        return new JLabel(icon);
    }


    

    public static void main (String [] args) {
        App app = new App();
    }
}
