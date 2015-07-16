package com.test;

import com.thalmic.myo.*;
import com.thalmic.myo.enums.*; 
import java.awt.*;
import javax.swing.*;

public class DevListener extends AbstractDeviceListener{
  private static final int SCALE = 10;
  private double rollW;
  private double pitchW;
  private double yawW;
  private Pose currentPose;
  private Arm whichArm;
  
  public DevListener() {
    rollW = 0;
    pitchW = 0;
    yawW = 0;
    currentPose = new Pose();
  }
  
  @Override
  public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
    Quaternion normalized = rotation.normalized();
    
    double roll = Math.atan2(2.0f * (normalized.getW() * normalized.getX() + normalized.getY() * normalized.getZ()), 1.0f - 2.0f * (normalized.getX() * normalized.getX() + normalized.getY() * normalized.getY()));
    double pitch = Math.asin(2.0f * (normalized.getW() * normalized.getY() - normalized.getZ() * normalized.getX()));
    double yaw = normalized.getW();//Math.atan2(2.0f * (normalized.getW() * normalized.getZ() + normalized.getX() * normalized.getY()), 1.0f - 2.0f * (normalized.getY() * normalized.getY() + normalized.getZ() * normalized.getZ()));
    
    rollW = ((roll + Math.PI) / (Math.PI * 2.0) * SCALE);
    pitchW = ((pitch + Math.PI / 2.0) / Math.PI * SCALE);
    yawW = ((yaw + Math.PI) / (Math.PI * 2.0) * SCALE);
    if(rollW >=9){
      MainFrame.bowLoaded = true;    
      MainFrame.bow.setBackground(Color.RED);
    }
    if((currentPose.getType() == PoseType.FIST || currentPose.getType() == PoseType.WAVE_IN) && yawW >8) {   
      MainFrame.bowCharged = true;
      if(MainFrame.bowLoaded){
        MainFrame.bow.setBackground(Color.ORANGE);
      }else{
        MainFrame.bow.setBackground(Color.BLUE);
      }
    }
  }
  
  @Override
  public void onPose(Myo myo, long timestamp, Pose pose) {
    currentPose = pose;
    if ((currentPose.getType() == PoseType.REST || currentPose.getType() == PoseType.WAVE_OUT || currentPose.getType() == PoseType.FINGERS_SPREAD) && MainFrame.bowCharged && MainFrame.bowLoaded) { 
      MainFrame.bowLoaded = false;
      MainFrame.bowCharged = false;
      MainFrame.bow.setBackground(Color.GREEN);
    }else if((currentPose.getType() == PoseType.FIST || currentPose.getType() == PoseType.WAVE_IN) && yawW >8) {   
      MainFrame.bowCharged = true;
      if(MainFrame.bowLoaded){
        MainFrame.bow.setBackground(Color.ORANGE);
      }else{
        MainFrame.bow.setBackground(Color.BLUE);
      }
    }else if(false){
      myo.vibrate(VibrationType.VIBRATION_MEDIUM);
      MainFrame.offset[0]=getOrientationData()[0]-MainFrame.max/2;  
      MainFrame.offset[1]=getOrientationData()[1]-MainFrame.max/2;
      MainFrame.offset[2]=getOrientationData()[2]-MainFrame.max/2;
    }
  }
  
  @Override
  public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection) {
    whichArm = arm;
  }
  public double[] getOrientationData(){
    return new double[]{rollW, pitchW, yawW};
  }
  
  @Override
  public void onArmUnsync(Myo myo, long timestamp) {
    whichArm = null;
  }
}
