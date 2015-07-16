import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 23.02.2015
  * @author 
  */

public class test extends JFrame {
  // Anfang Attribute
  private Panel panel1 = new Panel(null);
  private Label label1 = new Label();
  // Ende Attribute
  
  public test(String title) { 
    // Frame-Initialisierung
    super(title);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 300; 
    int frameHeight = 300;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // Anfang Komponenten
    
    panel1.setBounds(32, 32, 97, 89);
    cp.add(panel1);
    label1.setBounds(152, 96, 59, 65);
    label1.setText("text");
    cp.add(label1);
    // Ende Komponenten
    
    setVisible(true);
  } // end of public test
  
  // Anfang Methoden
  
  public static void main(String[] args) {
    new test("test");
  } // end of main
  
  // Ende Methoden
} // end of class test
