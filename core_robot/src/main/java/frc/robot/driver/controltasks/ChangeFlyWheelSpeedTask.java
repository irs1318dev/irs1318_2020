package frc.robot.driver.controltasks;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.PowerCellMechanism;

/**
 * Task that applies a single operation from a group of related operations for a short period of time.
 * 
 */
public class ChangeFlyWheelSpeedTask extends TimedTask implements IControlTask
{
    private final double velocityAmount;

    private double desiredSpeed;

    /**
     * Initializes a new ChangeFlyWheelSpeedTask
     * @param duration to wait in seconds
     * @param toPerform the operation to perform by setting to true for duration
     * @param possibleOperations to set of linked operations that should be set to false for duration
     */
    public ChangeFlyWheelSpeedTask(double duration, double velocityAmount)
    {
        super(duration);

        this.velocityAmount = velocityAmount;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        super.begin();

        PowerCellMechanism powerCell = this.getInjector().getInstance(PowerCellMechanism.class);
        this.desiredSpeed = powerCell.getFlywheelVelocity() + this.velocityAmount;
        this.setAnalogOperationState(AnalogOperation.PowerCellFlywheelVelocity, this.desiredSpeed);
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    @Override
    public void update()
    {
        this.setAnalogOperationState(AnalogOperation.PowerCellFlywheelVelocity, this.desiredSpeed);
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        super.end();
        this.setAnalogOperationState(AnalogOperation.PowerCellFlywheelVelocity, -1.0);
    }
}
