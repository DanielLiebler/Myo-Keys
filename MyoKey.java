import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*; 
import javax.imageio.ImageIO;
import java.awt.image.*;

/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 15.07.2015
  * @author 
  */

public class MyoKey extends JFrame {
  // Anfang Attribute
  private JPopupMenu optionMenu = new JPopupMenu();
  // Ende Attribute
  
  public MyoKey(String title) { 
    // Frame-Initialisierung
    super(title);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 512; 
    int frameHeight = 512;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width)-10;
    int y = 10;
    setLocation(x, y);
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    
    // Anfang Komponenten
    
    setUndecorated(true); 
    setAlwaysOnTop(true);
    setBackground(new Color(1.0f,1.0f,1.0f,0f));
    setLayout(new BorderLayout());
    BufferedImage bground = null;
    try{
      bground = ImageIO.read(getClass().getResource("textures/fullUI.png")); 
    }catch(Exception e){
      e.printStackTrace();
    }
    JLabel background=new JLabel(new ImageIcon(bground));
    add(background);
    addMouseListener(new MouseAdapter() { 
      public void mouseClicked(MouseEvent evt) { 
        myoKey_MouseClicked(evt);
      }                                       
      public void mousePressed(MouseEvent evt) { 
        myoKey_MouseClicked(evt);
      }     public void mouseReleased(MouseEvent evt) {
        if (evt.isPopupTrigger()) 
        optionMenu.show(evt.getComponent(), evt.getX(), evt.getY()); 
      }
    });
    addMouseMotionListener(new MouseMotionAdapter() { 
      public void mouseDragged(MouseEvent evt) { 
        myoKey_MouseDragged(evt);
      }
    });
    
    JMenuItem menuItem = new JMenuItem(new ImageIcon("textures/group.png"));
    menuItem.setMnemonic(KeyEvent.VK_D);   
    optionMenu.add(menuItem);
    // Ende Komponenten
    
    setVisible(true);
  } // end of public MyoKey
  
  // Anfang Methoden
  
  public static void main(String[] args) {
    new MyoKey("MyoKey");
  } // end of main
  
  public void myoKey_MouseClicked(MouseEvent evt) {
    // TODO hier Quelltext einfügen
  } // end of myoKey_MouseClicked
  
  public void myoKey_MouseDragged(MouseEvent evt) {
    // TODO hier Quelltext einfügen
  } // end of myoKey_MouseDragged
  
  // Ende Methoden
} // end of class MyoKey
