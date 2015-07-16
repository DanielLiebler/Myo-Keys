package com.test;
import java.awt.*;
import javax.swing.*;
import com.thalmic.myo.*;
import com.thalmic.myo.enums.*;

public class MainFrame extends JFrame{
  
  private Hub hub;
  
  private Label[] data = new Label[3];
  public static Panel bow = new Panel(); 
  public static boolean bowLoaded = false;   
  public static boolean bowCharged = false;
  private Panel room = new Panel();
  private Panel pos = new Panel(); 
  private Panel roomW = new Panel();
  private Panel posW = new Panel();
  public static double max = 10;
  public static double[] offset = new double[3];
  
  public MainFrame(){           
    Container cp = getContentPane();
    cp.setLayout(null);
    
    setTitle("Myo & Java Test");
    setBounds(0,0,650,450);
    setBackground(Color.black);
    room = new Panel();
    pos = new Panel();    
    bow = new Panel();   
    room.setBounds(50,50,300,300);
    pos.setBounds(195,195,10,10); 
    roomW.setBounds(360,50,10,300);
    posW.setBounds(360,195,10,10);
    bow.setBounds(500,100,100,100);
    room.setBackground(Color.black);    
    pos.setBackground(Color.red);   
    roomW.setBackground(Color.blue);    
    posW.setBackground(Color.red);    
    bow.setBackground(Color.black);
    
    data[0] = new Label(); 
    data[1] = new Label();
    data[2] = new Label();
    
    data[0].setBounds(50,40, 140, 10);
    data[1].setBounds(200,40, 140, 10);
    data[2].setBounds(350,40, 140, 10);
    data[0].setText("X");             
    data[1].setText("Y");
    data[2].setText("W");
    
    cp.add(data[0]); 
    cp.add(data[1]);
    cp.add(data[2]);
    cp.add(pos);
    cp.add(room);  
    cp.add(posW);
    cp.add(roomW); 
    cp.add(bow);
    
    setVisible(true);
    run();
  }
  
  public void resetPos(double w, double y, double x){
    x=-offset[0] + x;                                   
    y=-offset[1] + y;
    w=-offset[2] + w;
    if(x > max){
      max = x;
    }else if(y > max){
      max = y;
    }else if(w > max){
      max = w;
    }
    
    pos.setLocation(350+(int)(-300*(x/max)), 350+(int)(-300*(y/max)));
    posW.setLocation(360, 50+(int)(300*(w/max)));
    
  }
  
  public void run(){
    try {
      hub = new Hub("com.example.hello-myo");
      
      System.out.println("Attempting to find a Myo...");
      Myo myo = hub.waitForMyo(10000);
      myo.unlock(UnlockType.UNLOCK_HOLD);
      
      
      if (myo == null) {
        throw new RuntimeException("Unable to find a Myo!");
      }
      
      System.out.println("Connected to a Myo armband!");
      DevListener devListener = new DevListener();
      hub.addListener(devListener);
      
      while (true) {
        hub.run(1000 / 20);
        //System.out.print(devListener.getOrientationData()[0] + "/" + devListener.getOrientationData()[1] + "/" + devListener.getOrientationData()[2]);    
        data[2].setText("X:" + (int)(devListener.getOrientationData()[0]) + " von " + max);             
        data[0].setText("Y:" + (int)(devListener.getOrientationData()[1]) + " von " + max);
        data[1].setText("W:" + (int)(devListener.getOrientationData()[2]) + " von " + max);  
        resetPos(devListener.getOrientationData()[0], devListener.getOrientationData()[1], devListener.getOrientationData()[2]);
      }
      
    } catch (Exception e) {
      System.err.println("Error: ");
      e.printStackTrace();
      System.exit(1);
    }
  }
  
  public static void main(String[] args){
    MainFrame mf = new MainFrame();
  }
  
}
