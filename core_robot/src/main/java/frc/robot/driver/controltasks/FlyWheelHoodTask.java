package frc.robot.driver.controltasks;

import frc.robot.driver.DigitalOperation;

/**
 * Task that sets the Hood Position.
 */
public class FlywheelHoodTask extends CompositeOperationTask
{
    private static DigitalOperation[] hoodPositionOperations =
    {
        DigitalOperation.PowerCellHoodPointBlank,
        DigitalOperation.PowerCellHoodShort,
        DigitalOperation.PowerCellHoodMedium,
        DigitalOperation.PowerCellHoodLong,
    };

    /**
    * Initializes a new FlyWheelHoodTask
    * @param toPerform the hood position to move to
    */
    public FlywheelHoodTask(DigitalOperation toPerform)
    {
        super(0.5, toPerform, FlywheelHoodTask.hoodPositionOperations);
    }
}
