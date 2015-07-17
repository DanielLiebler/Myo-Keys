import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*; 

public class ResizeFrame extends JFrame{
  
  public ResizeFrame(int side){
    super("");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = (side == 1 || side == 5)?200:100; 
    int frameHeight = (side == 3 || side == 7)?200:100;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width)-10;
    int y = 10;
    setLocation(x, y);
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
  }
}