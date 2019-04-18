package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.States;
import frc.robot.commands.*;
import frc.robot.Neptune;

public class Climb extends CommandGroup {

	private int climbSet;
	public Climb(int climbSet) {
		this.climbSet = climbSet;
		if(climbSet == 3){//Level 3 climb from platform
			addSequential(new ElevateToPosition(6));
			addSequential(new WaitCommand(1.6));
			addSequential(new EngageForeams(0.35));
			addSequential(new WaitCommand(0.5));
			addParallel(new ElevateToPosition(7));
			addSequential(new LevitateToPosition(RobotMap.lvl3Climb));
			addSequential(new WaitCommand(3.2));
			addSequential(new FinalSequence(3));
		}else if(climbSet == 2){//Level 2 climb from platform
			addSequential(new ElevateToPosition(9));
			addSequential(new WaitCommand(1.2));
			addSequential(new EngageForeams(0.3));
			addSequential(new WaitCommand(0.2));
			addParallel(new ElevateToPosition(7));
			addSequential(new LevitateToPosition(RobotMap.lvl2Climb));
			addSequential(new WaitCommand(1));
			addSequential(new FinalSequence(2));
		}		
	}

	@Override
	protected void initialize() {
		States.led = States.LEDstates.CLIMBING;
	}


	@Override
	protected void interrupted() {
		States.led = States.LEDstates.PASSIVE;
	}
}
