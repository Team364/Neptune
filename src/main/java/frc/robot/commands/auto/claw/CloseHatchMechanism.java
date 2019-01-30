package frc.robot.commands.auto.claw;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class CloseHatchMechanism extends Command {

    public CloseHatchMechanism() {
        requires(Robot.clawSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.clawSystem.closeHatch();;
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.clawSystem.noHatchInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
