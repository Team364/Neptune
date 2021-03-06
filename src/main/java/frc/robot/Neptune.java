package frc.robot;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.*;
import frc.robot.oi.*;
import frc.robot.subroutines.ActivateTrident;
import frc.robot.States;
import frc.robot.misc.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.auto.*;
public class Neptune extends TimedRobot {

  public static AnalogOutput LEDs = new AnalogOutput(0);
  public static Elevator elevator = Elevator.getInstance();
  public static DriveTrain driveTrain = DriveTrain.getInstance();
  public static Trident trident = Trident.getInstance();
  public static VisionProcessing vision = VisionProcessing.getInstance();
  public static Pitchfork climber = Pitchfork.getInstance();

  public static DriverOI oi;
  public static OperatorOI oi2;

  public UsbCamera camera;
  public static boolean manualControl;
  public static Command sandstorm = new Auto(0);
  public int stopLoops;

  private DriverStation dStation = DriverStation.getInstance();
  public static RobotController diagnostics;
 

  @Override
  public void robotInit() {
    oi = new DriverOI();
    oi2 = new OperatorOI();

    camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
    camera.setResolution(320, 240);
    camera.setBrightness(50);
    camera.setFPS(20);

     driveTrain.setDriverCamMode();
  } 

  @Override
  public void robotPeriodic() {
    elevator.postSmartDashVars();
    driveTrain.postSmartDashVars();
    double time = dStation.getMatchTime();
    SmartDashboard.putNumber("Match Time", time);
        //LED set
        if(!dStation.isDSAttached()){
          LEDs.setVoltage(1);
        }else if(dStation.isDSAttached()&&(dStation.isDisabled())){
          LEDs.setVoltage(2);
        }else if(States.led == States.LEDstates.INTAKE_MODE){
          LEDs.setVoltage(3);
        }else if(States.led == States.LEDstates.HAS_OBJ){
          LEDs.setVoltage(4);
        }else if(States.led == States.LEDstates.CLIMBING){
          LEDs.setVoltage(5);
        }else if(States.led == States.LEDstates.PASSIVE){
          LEDs.setVoltage(2);
        }
      
  }

  @Override
  public void autonomousInit() {
    Scheduler.getInstance().removeAll();
    sandstorm.start();
    //rocket.start();
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    oi2.controlLoop();
  }

  @Override
  public void teleopInit() {
    Scheduler.getInstance().removeAll();
  }

  @Override
  public void teleopPeriodic() { 
    Scheduler.getInstance().run();
    oi2.controlLoop();
    postSmartDashVars();
    if ((elevator.getLiftPosition() < 10000) && (elevator.getLiftPosition() > RobotMap.liftLowerBound)) {
      States.liftZone = States.LiftZones.LOWER_DANGER;
    } else if ((elevator.getLiftPosition() > 100000) && (elevator.getLiftPosition() < RobotMap.liftUpperBound))
      States.liftZone = States.LiftZones.UPPER_DANGER;
    else {
      States.liftZone = States.LiftZones.SAFE;
    }

    if ((Neptune.elevator.getLiftPosition() >= RobotMap.liftUpperBound)) {
      if(stopLoops == 0){
        elevator.stopLift();
        stopLoops++;
      }
      elevator.setLiftPosition(RobotMap.liftHighH);

    }
    if ((Neptune.elevator.getLiftPosition() <= RobotMap.liftLowerBound)) {
      if(stopLoops == 0){
        elevator.stopLift();
        stopLoops++;
      }
      elevator.setLiftPosition(0);
  
    }


  }

  @Override
  public void disabledInit() {
    System.out.println("DISABLED INIT");
  }

  @Override
  public void disabledPeriodic() {
    postSmartDashVars();
  }

  @Override
  public void testPeriodic() {
  }

  public void postSmartDashVars() {
    SmartDashboard.putString("Object State:", States.objState.toString());
    SmartDashboard.putString("Lift Zone: ", States.liftZone.toString());
    SmartDashboard.putString("Elevator Command: ", elevator.getCurrentCommandName());
    SmartDashboard.putNumber("Elevator Target Height: ", elevator.TargetHeight);
    SmartDashboard.putNumber("Elevator Actaul Height: ", elevator.getLiftPosition());
    SmartDashboard.putNumber("Elevator Velocity: ", elevator.getLiftVelocity());
    SmartDashboard.putNumber("Arm Target Angle: ", elevator.TargetAngle);
    SmartDashboard.putNumber("Arm Actual Angle", elevator.getArmAngle());
    SmartDashboard.putNumber("Arm Velocity: ", elevator.getArmVelocity());
    SmartDashboard.putString("LED state: ", States.led.toString());
    SmartDashboard.putNumber("Lev Pos ", climber.levitator.getSelectedSensorPosition());
    SmartDashboard.putNumber("Back wheel velocity", climber.driver.getMotorOutputPercent());
    SmartDashboard.putBoolean("Shift State", driveTrain.isShifterHigh());
    SmartDashboard.putString("Piston Get", trident.lever.pistonGet());
    SmartDashboard.putNumber("Right enc", driveTrain.getRightCounts());
    SmartDashboard.putNumber("Left enc", driveTrain.getLeftCounts());
    SmartDashboard.putNumber("Sin 90",  Math.sin(90) * 1.11857);
    //(Math.sin(90) / 180)*Math.PI);
  }

}
