
import com.thalmic.myo.*;
import com.thalmic.myo.enums.*;  

public class CombinedListener extends AbstractDeviceListener{
  public XDirection xDir;
  public Arm curArm;
  public void onPair(Myo myo, long timestamp, FirmwareVersion firmwareVersion) {
  }
  
  @Override
  public void onUnpair(Myo myo, long timestamp) {
  }
  
  @Override
  public void onConnect(Myo myo, long timestamp, FirmwareVersion firmwareVersion) {
  }
  
  @Override
  public void onDisconnect(Myo myo, long timestamp) {
  }
  
  @Override
  public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection) {
    curArm = arm;
    xDir = xDirection;
  }
  
  @Override
  public void onArmUnsync(Myo myo, long timestamp) {
  }
  
  @Override
  public void onUnlock(Myo myo, long timestamp) { 
    MyoKey.mainFrame.setVisible(true);            
    System.out.println("Unlock");
  }
  
  @Override
  public void onLock(Myo myo, long timestamp) {
    System.out.println("Lock");
    MyoKey.mainFrame.setVisible(false);
  }
  
  @Override
  public void onPose(Myo myo, long timestamp, Pose pose) {
    System.out.println("Pose" + pose.toString());
    if(PoseType.FIST == pose.getType()){
      MyoKey.selectedGroup = MyoKey.selected;
    }
  }
  
  @Override
  public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
    
    Quaternion normalized = rotation.normalized();
    
    double roll = Math.atan2(2.0f * (normalized.getW() * normalized.getX() + normalized.getY() * normalized.getZ()), 1.0f - 2.0f * (normalized.getX() * normalized.getX() + normalized.getY() * normalized.getY()));
    double pitch = Math.asin(2.0f * (normalized.getW() * normalized.getY() - normalized.getZ() * normalized.getX()));
    double yaw = Math.atan2(2.0f * (normalized.getW() * normalized.getZ() + normalized.getX() * normalized.getY()), 1.0f - 2.0f * (normalized.getY() * normalized.getY() + normalized.getZ() * normalized.getZ()));
    
    
    //System.out.println("Orientation: " + roll + "/" + pitch + "/" + yaw);
    
    MyoKey.mainFrame.calculatePos(roll, yaw, pitch);
  }
  
  @Override
  public void onAccelerometerData(Myo myo, long timestamp, Vector3 accel) {
  }
  
  @Override
  public void onGyroscopeData(Myo myo, long timestamp, Vector3 gyro) {
  }
}