package frc.robot.driver.controltasks;

import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.IControlTask;

/**
 * Task that sets the Intake Position.
 */
public class IntakePositionTask extends CompositeOperationTask implements IControlTask
{
    private static DigitalOperation[] intakePositionOperations =
    {
        DigitalOperation.PowerCellIntakeExtend,
        DigitalOperation.PowerCellIntakeRetract,
    };

    /**
    * Initializes a new IntakePositionTask
    * @param intakeIn in or out
    */
    public IntakePositionTask(boolean intakeIn)
    {
        super(
            0.5, 
            intakeIn ? DigitalOperation.PowerCellIntakeExtend : DigitalOperation.PowerCellIntakeRetract, 
            IntakePositionTask.intakePositionOperations);
    }
}
