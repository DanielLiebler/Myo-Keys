import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*; 
import javax.imageio.ImageIO;
import java.awt.image.*;      
import com.thalmic.myo.*;
import com.thalmic.myo.enums.*;

/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 15.07.2015
  * @author                                                                                                                                                
  */         

public class MyoKey extends JFrame {
  private static float shrink = 1f;
  private static final int[][] ballpos = {{75,337}, {20,207}, {75,75}, {206,20}, {337,75}, {392,206}, {337,337}, {206,392}};
  private static int selectedGroup = 0;//0 == none
  private BufferedImage[][] groups = new BufferedImage[7][6];        //Group[x][0] is the complete Group Icon. Group 6 is additional Character Group. So Groups[6][0] is null   
  private BufferedImage[] selectors = new BufferedImage[8];
  // Anfang Attribute
  public static MyoKey mainFrame;
  private JPopupMenu optionMenu = new JPopupMenu();
  
  
  //start MYO Attributes
  //Using Nicolas A Stuarts Java Library
  
  
  private static Hub hub;
  private static Myo myo;
  private static CombinedListener comListener;
  
  
  //Calculating Arm Pos stuff
  
  private static double[] lastPos = {0, 0};
  private static final double correctionGrade = 0;
  private static double[] midPos = {0,0};//Will be improved when height >1.3 {position, Value(how much greater the height value is than 1.3)}
  private static int selected = -1;
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
    
    //Read Images
    for (int i=1; i<=6; i++) {  
      try{
        groups[i-1][0] = ImageIO.read(getClass().getResource("textures/Group0" + i + ".png")); 
      }catch(Exception e){e.printStackTrace();}
      for (int j=1; j<=5; j++) {
        try{
          groups[i-1][j] = ImageIO.read(getClass().getResource("textures/Group" + Integer.toString(i) + Integer.toString(j) + ".png"));
        }catch(Exception e){e.printStackTrace();}
      } // end of for    
    }
    try{
      groups[6][0] = ImageIO.read(getClass().getResource("textures/Group65.png"));
    }catch(Exception e){e.printStackTrace();}
    for (int j=1; j<=5; j++) {
      try{
        groups[6][j] = ImageIO.read(getClass().getResource("textures/Group7"+ Integer.toString(j) + ".png"));
      }catch(Exception e){e.printStackTrace();}
    } // end of for
    
    
    for (int i=0; i<=7; i++) {  
      try{
        selectors[i] = ImageIO.read(getClass().getResource("textures/selector" + i + ".png")); 
      }catch(Exception e){e.printStackTrace();}
    }
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
      }
      public void mouseReleased(MouseEvent evt) {
        if (evt.isPopupTrigger()) 
        optionMenu.show(evt.getComponent(), evt.getX(), evt.getY()); 
      }
    });
    
    optionMenu.setBackground(new Color(1f,1f,1f,1f));
    JMenuItem menuItemExit = new JMenuItem("   EXIT Myo Keys", new ImageIcon("textures/exit.png"));
    menuItemExit.setBackground(new Color(1.0f,1.0f,1.0f,0f));  
    menuItemExit.setOpaque(false);
    menuItemExit.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        mainFrame.dispose();
      }
    });
    optionMenu.add(menuItemExit);
    
    // Ende Komponenten
    
    setVisible(true);
  } // end of public MyoKey
  
  // Anfang Methoden
  
  public static void main(String[] args) {
    mainFrame = new MyoKey("MyoKey");
    
    
    //MYO Functionality
    
    try {
      hub = new Hub("com.example.hello-myo");
      
      System.out.println("Attempting to find a Myo...");
      myo = hub.waitForMyo(10000);
      myo.unlock(UnlockType.UNLOCK_HOLD);
      
      
      if (myo == null) {
        throw new RuntimeException("Unable to find a Myo!");
      }
      
      System.out.println("Connected to a Myo armband!");
      comListener = new CombinedListener();
      hub.addListener(comListener);
      
      while (true) {      
        hub.run(1000 / 20);
      } // end of while
      
    } catch (Exception e) {
      System.err.println("Error: ");
      e.printStackTrace();
      System.exit(1);
    }
    
    
  } // end of main
  
  @Override
  public void paint(Graphics g){
    super.paint(g);
    if(selectedGroup == 0){
      for (int i = 0; i<=5; i++) {
        g.drawImage(groups[i][0], ballpos[i][0], ballpos[i][1], new Color(1f,1f,1f,0f), null);
      } // end of for 
    }else{
      for (int i = 0; i<=5; i++) {
        g.drawImage(groups[selectedGroup][i], ballpos[i][0], ballpos[i][1], new Color(1f,1f,1f,0f), null);
      } // end of for 
    }
    
    if(selected >= 0){
      g.drawImage(selectors[selected], 184, 184, new Color(1f,1f,1f,0f), null);  
    }
  }
  
  public void myoKey_MouseClicked(MouseEvent evt) {
    // TODO hier Quelltext einfügen
  } // end of myoKey_MouseClicked
  
  public void calculatePos(double rot, double pan, double height){
    //System.out.println(comListener.xDir.toString());              
    //System.out.println(comListener.curArm.toString());
    
    if(comListener.xDir == XDirection.X_DIRECTION_TOWARDS_WRIST) height = -height;
    System.out.println(Double.toString(rot).substring(0,4) + "/" + Double.toString(pan).substring(0,4) + "/" + Double.toString(height).substring(0,4));
    
    //height -1.4 - 1.4
    //0.7 per field
    //0-0.35, 0.36-1.05, 1.06 - 1.4
    
    double dPan = pan - lastPos[0];
    if (dPan < 0) dPan = -dPan;
    if((height > midPos[1]+1.3-correctionGrade*dPan) || (-height > midPos[1]+1.3-correctionGrade*dPan)){
      //improve midPos
      System.out.print("improving: " + midPos[1] + " to ");
      if(height<0){
        midPos[1] = (-height)-1.3;      
        System.out.println(midPos[1]);
      }else{
        midPos[1] = height-1.3;
        System.out.println(midPos[1]);
      }
      midPos[0] = pan;
    }
    
    boolean side = pan < midPos[0];//true == right, false == left
    
    
    if(!side){
      if(height< -0.25){
        //Field 0
        System.out.println("Field 0");
        selected = 0;
      }else if(height<= 0.25 && height> -0.35){
        //Field 1
        System.out.println("Field 1");
        selected = 1;
      }else if(height<= 1.05 && height> 0.35){
        //Field 2
        System.out.println("Field 2");
        selected = 2;
      }else if(height> 1.05){
        //Field 3
        System.out.println("Field 3");
        selected = 3;
      }
    }else{
      if(height> 1.05){  
        //Field 3 
        System.out.println("Field 3");
        selected = 3;
      }else if(height<= 1.05 && height> 0.6){
        //Field 4     
        System.out.println("Field 4");
        selected = 4;
      }else if(height<= 0.6){  
        //Field 5
        System.out.println("Field 5 selected");
        selected = 5;
      }
    }             
    mainFrame.repaint();
    //System.out.println("Midpos: " + midPos[0] + "/" + midPos[1]);
    lastPos[0] = pan;
    lastPos[1] = height;
  }
  
  // Ende Methoden
} // end of class MyoKey
