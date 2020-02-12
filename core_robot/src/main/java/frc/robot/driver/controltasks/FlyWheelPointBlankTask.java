package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.IControlTask;

/**
 * Task that sets the Hood Position and spins the wheel at the appropriate speed for a point-blank shot.
 */
public class FlyWheelPointBlankTask extends CompositeOperationTask implements IControlTask
{
    private static DigitalOperation[] hoodPositionOperations =
    {
        DigitalOperation.PowerCellHoodPointBlank,
        DigitalOperation.PowerCellHoodShort,
        DigitalOperation.PowerCellHoodMedium,
        DigitalOperation.PowerCellHoodLong,
    };

    /**
    * Initializes a new FlyWheelPointBlankTask
    */
    public FlyWheelPointBlankTask()
    {
        super(0.5, DigitalOperation.PowerCellHoodPointBlank, FlyWheelPointBlankTask.hoodPositionOperations);
    }

    /**
     * Run an iteration of the current task and apply any control changes
     */
    @Override
    public void update()
    {
        super.update();

        this.setAnalogOperationState(AnalogOperation.PowerCellFlywheelVelocity, TuningConstants.POWERCELL_FLYWHEEL_POINT_BLANK_MOTOR_VELOCITY);
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        super.end();

        this.setAnalogOperationState(AnalogOperation.PowerCellFlywheelVelocity, TuningConstants.STHOPE_BLEASE);
    }
}
