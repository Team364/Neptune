/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Piston opens, closes, and has no input
 */
public class PistonBase extends Subsystem {

  private DoubleSolenoid piston;
  /** State tracks what position piston is in */
      public enum PistonStates{
        OPEN,
        CLOSED
    }
    public PistonStates pistonState = PistonStates.CLOSED;

  public PistonBase(DoubleSolenoid piston){
    this.piston = piston;
  }
    /**
     * open piston
     */ 
    public void open() {
      piston.set(DoubleSolenoid.Value.kForward);
      pistonState = PistonStates.OPEN;
  }

  /**
   * closePiston()
   */ 
  public void close() {
      piston.set(DoubleSolenoid.Value.kReverse);
      pistonState = PistonStates.CLOSED;
  }

  /**
   * noInput()
   * Leaves the piston where it is
   */ 
  public void noInput() {
      piston.set(DoubleSolenoid.Value.kOff);
  }
  /**Treat as abstract */
  @Override
  public void initDefaultCommand() {
  }
}
