/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.Periodic;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class SuperStructure extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  /**tracks whehter or not the lift is in bounds for open loop control */
  public boolean liftOutofBounds = false;
  public boolean armOutofBounds = false;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
     setDefaultCommand(new Periodic());
  }
  public void resetEncoders(){
    Robot.liftSystem.zero();
    Robot.armSystem.zero();
  }
}
