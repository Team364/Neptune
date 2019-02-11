package frc.robot.subroutines.pressed.grip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.grip.*;
import frc.robot.util.States;

/**
 * Subroutine to be run in teleop on button press
 */
public class IntakeCargo extends CommandGroup {
    /**
     * Uses Object State to determine which subroutine to run to intake
     */
    public IntakeCargo() {

        //Score Cargo Subroutine
        addSequential(new IntakeCargo());
        addSequential(new CloseClaw());
        States.actionState = States.ActionStates.INTAKE_ACT;
    
    }
}