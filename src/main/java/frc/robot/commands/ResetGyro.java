package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;


public class ResetGyro extends Command {

    public ResetGyro() {
        requires(Neptune.elevator);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Neptune.elevator.getPigeon().setYaw(0);
    }


    @Override
    protected void end() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
