import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;     
import javax.imageio.ImageIO;
import java.awt.image.*;    

public class RedockFrame extends JFrame{
  public int myside;
  public RedockFrame(int side){
    super("");
    myside = side;
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = (side == 1 || side == 5)?200:100; 
    int frameHeight = (side == 3 || side == 7)?200:100;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = 0;
    int y = 0;
    if(side ==0 || side ==6 || side ==7) x = 0;
    if(side ==1 || side ==5) x = d.width/2-100;
    if(side ==2 || side ==3 || side ==4) x = d.width-100;
    if(side ==0 || side ==1 || side ==2) y = 0;
    if(side ==3 || side ==7) y = d.height/2-100;
    if(side ==4 || side ==5 || side ==6) y = d.height-100;
    setLocation(x, y);
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    
    setUndecorated(true); 
    setAlwaysOnTop(true);
    setBackground(new Color(1.0f,1.0f,1.0f,0f));
    setLayout(new BorderLayout());
    BufferedImage bground = null;
    try{
      bground = ImageIO.read(getClass().getResource("textures/Dock" + side + ".png")); 
    }catch(Exception e){
      e.printStackTrace();
    }
    JLabel background=new JLabel(new ImageIcon(bground));
    add(background);   
    addMouseListener(new MouseAdapter() { 
      public void mouseClicked(MouseEvent evt) { 
        MyoKey.reDock(myside);
      }        
    });
  }
}