/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.States;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**Controls state logic for variable robot funtionality */
public class Periodic extends Command {
  public int loops = 0;
  public Periodic() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.superStructure);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //System.out.println(Robot.armSystem.getAbsolutePosition());
      Robot.armSystem.instrumentation();
    if(States.loopState == States.LoopStates.CLOSED_LOOP){
      ++loops;
      if(loops > 20){
      if(Robot.armSystem.reachedPosition()){
        States.loopState = States.LoopStates.OPEN_LOOP;
        loops = 0;
      }
    }
    }
    // Robot.liftSystem.instrumentation();
    // // if(Robot.liftSystem.reachedPosition());
    // if(States.loopState == States.LoopStates.CLOSED_LOOP){
    //   ++loops;
    //   if(loops > 20){
    //   if(Robot.liftSystem.reachedPosition()){
    //     States.loopState = States.LoopStates.OPEN_LOOP;
    //     loops = 0;
    //   }
    // }
    // }
  }
  public double getLoops(){
    return loops;
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
