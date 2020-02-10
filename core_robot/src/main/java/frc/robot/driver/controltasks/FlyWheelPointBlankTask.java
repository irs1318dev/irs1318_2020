package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.IControlTask;

/**
 * Task that turns the turret a certain amount clockwise or counterclockwise based on vision center
 */
public class FlyWheelPointBlankTask extends ControlTaskBase implements IControlTask
{
    /**
    * Initializes a new FlyWheelPointBlankTask
    */
    public FlyWheelPointBlankTask()
    {
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
    }

    /**
     * Run an iteration of the current task and apply any control changes
     */
    @Override
    public void update()
    {
        this.setDigitalOperationState(DigitalOperation.PowerCellHoodPointBlank, true);
        this.setAnalogOperationState(AnalogOperation.PowerCellFlywheelVelocity, TuningConstants.POWERCELL_FLYWHEEL_POINT_BLANK_MOTOR_VELOCITY);
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        this.setDigitalOperationState(DigitalOperation.VisionDisable, true);
        this.setAnalogOperationState(AnalogOperation.PowerCellFlywheelVelocity, TuningConstants.STHOPE_BLEASE);
    }

    /**
     * Checks whether this task has completed, or whether it should continue being processed
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    @Override
    public boolean hasCompleted()
    {
        return false;
    }
}
