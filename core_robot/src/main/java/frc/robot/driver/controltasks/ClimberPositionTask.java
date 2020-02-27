package frc.robot.driver.controltasks;

import frc.robot.driver.DigitalOperation;

/**
 * Task that sets the Climber Position.
 */
public class ClimberPositionTask extends CompositeOperationTask
{
    private static DigitalOperation[] climberPositionOperations =
    {
        DigitalOperation.ClimberExtend,
        DigitalOperation.ClimberRetract,
    };

    /**
    * Initializes a new ClimberPositionTask
    * @param duration to extend/retract
    * @param extend or retract
    */
    public ClimberPositionTask(double duration, boolean extend)
    {
        super(
            duration,
            extend ? DigitalOperation.ClimberExtend : DigitalOperation.ClimberRetract, 
            ClimberPositionTask.climberPositionOperations);
    }
}
