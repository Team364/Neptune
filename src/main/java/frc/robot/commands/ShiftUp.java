package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class ShiftUp extends Command {

    public ShiftUp() {
        requires(Neptune.driveTrain);
        setTimeout(0.04);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Neptune.driveTrain.shiftUp();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.driveTrain.NoInputShift();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}