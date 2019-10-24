package frc.robot.subsystems;

import static frc.robot.Conversions.*;
import static frc.robot.RobotMap.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.misc.math.Vector2;


public class SwerveMod{
    private double lastTargetAngle = 0;
    public final int moduleNumber;

    public final int mZeroOffset;

    private final TalonSRX mAngleMotor;
    private final TalonSRX mDriveMotor;
    private Vector2 modulePosition;
    private boolean driveInverted = false;
    
    public double targetAngle;
    public double targetSpeed;
    public double smartAngle;
    public Vector2 velocity;
    public double currentAngle;
    //private int absolutePosition;
    private boolean invertSensorPhase;

    public SwerveMod(int moduleNumber, Vector2 modulePosition, TalonSRX angleMotor, AnalogInput angleEncoder, TalonSRX driveMotor, boolean invertDrive, boolean invertSensorPhase, int zeroOffset) {
        this.moduleNumber = moduleNumber;
        this.modulePosition = modulePosition;
        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        mZeroOffset = zeroOffset;
        targetAngle = 0;
        targetSpeed = 0;
        currentAngle = 0;
        this.invertSensorPhase = invertSensorPhase;



        angleMotor.configFactoryDefault();
        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, SLOTIDX, SWERVETIMEOUT);
        angleMotor.configFeedbackNotContinuous(false, SWERVETIMEOUT); //make false if using relative, make true if using absolute
        angleMotor.config_kP(SLOTIDX, ANGLEP);
        angleMotor.config_kI(SLOTIDX, ANGLEI);
        angleMotor.config_kD(SLOTIDX, ANGLED);
        angleMotor.setSensorPhase(invertSensorPhase);


        angleMotor.setNeutralMode(NeutralMode.Brake);

        driveMotor.setNeutralMode(NeutralMode.Brake);
        
        // Setup Current Limiting
        angleMotor.configContinuousCurrentLimit(ANGLECONTINUOUSCURRENTLIMIT, SWERVETIMEOUT);
        angleMotor.configPeakCurrentLimit(ANGLEPEAKCURRENT, SWERVETIMEOUT);
        angleMotor.configPeakCurrentDuration(ANGLEPEAKCURRENTDURATION, SWERVETIMEOUT);
        angleMotor.enableCurrentLimit(ANGLEENABLECURRENTLIMIT);

        driveMotor.configContinuousCurrentLimit(DRIVECONTINUOUSCURRENTLIMIT, SWERVETIMEOUT);
        driveMotor.configPeakCurrentLimit(DRIVEPEAKCURRENT, SWERVETIMEOUT);
        driveMotor.configPeakCurrentDuration(DRIVEPEAKCURRENTDURATION, SWERVETIMEOUT);
        driveMotor.enableCurrentLimit(DRIVEENABLECURRENTLIMIT);        
        setDriveInverted(invertDrive);


    }

    public TalonSRX getAngleMotor(){
        return mAngleMotor;
    }

    public void setTargetVelocity(Vector2 velocity, boolean speed, double rotation){
            this.velocity = velocity;
            if(moduleNumber == 1){
                targetAngle = (velocity.getAngle().toDegrees());
            }
            else{
                targetAngle = velocity.getAngle().toDegrees();
            }
            smartAngle = targetAngle;
            if(speed){
            targetSpeed = velocity.length;
            }
            else{
            targetSpeed = 0;
            }
    }

    public Vector2 getModulePosition(){
        return modulePosition;
    }

    public TalonSRX getDriveMotor() {
        return mDriveMotor;
    }

    public double getDrivePos(){
        return mDriveMotor.getSelectedSensorPosition(SLOTIDX);
    }
    
    public double getTargetAngle() {
        return lastTargetAngle;
    }

    public void setDriveInverted(boolean inverted) {
        driveInverted = inverted;
    }

    public void setTargetAngle(double targetAngle) {
        targetAngle = modulate360(targetAngle);
        double currentAngleMod = getPos();
        if (currentAngleMod < 0) currentAngleMod += 360;
        double delta = currentAngleMod - targetAngle;
        if (delta > 180) {
            targetAngle += 360;
        } else if (delta < -180) {
            targetAngle -= 360;
        }
        delta = currentAngleMod - targetAngle;
        if (delta > 90 || delta < -90) {
            if (delta > 90)
                targetAngle += 180;
            else if (delta < -90)
                targetAngle -= 180;
            mDriveMotor.setInverted(false);
        } else {
            mDriveMotor.setInverted(true);
        }

        lastTargetAngle = targetAngle;
        if(targetAngle < 0){
            targetAngle += 360;
        }
        lastTargetAngle = targetAngle;

        mAngleMotor.set(ControlMode.Position, targetAngle);            
        
    }

    public void setTargetSpeed(double speed) {
        if (driveInverted) {speed = -speed;}
        mDriveMotor.set(ControlMode.PercentOutput, speed);
    } 


    public  double getPos(){
        double angle = mAngleMotor.getSelectedSensorPosition();
        return angle;
    }    

    public void setSensorPhase(boolean invert){
        mAngleMotor.setSensorPhase(invert);
    }

}
