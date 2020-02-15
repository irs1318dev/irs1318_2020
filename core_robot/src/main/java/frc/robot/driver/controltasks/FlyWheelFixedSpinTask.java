package frc.robot.driver.controltasks;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.common.IControlTask;
import frc.robot.TuningConstants;

public class FlyWheelFixedSpinTask extends ControlTaskBase implements IControlTask
{
    private final double speed;

    /**
     * Initializes a new FlyWheelSpinTask
     * @param speed to spin the flywheel
     */
    public FlyWheelFixedSpinTask(double speed)
    {
        this.speed = speed;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        this.setAnalogOperationState(AnalogOperation.PowerCellFlywheelVelocity, this.speed);
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    public void update()
    {
        this.setAnalogOperationState(AnalogOperation.PowerCellFlywheelVelocity, this.speed);
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
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
