package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.PowerCellMechanism;

/**
 * Task that applies a single operation from a group of related operations for a short period of time.
 * 
 */
public class DecreaseFlyWheelSpeedTask extends TimedTask implements IControlTask
{
    private final double velocityAmount;
    private double desiredSpeed;

    /**
     * Initializes a new CompositeOperationTask
     * @param duration to wait in seconds
     * @param toPerform the operation to perform by setting to true for duration
     * @param possibleOperations to set of linked operations that should be set to false for duration
     */
    public DecreaseFlyWheelSpeedTask(double duration, double velocityAmount)
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
        PowerCellMechanism powerCell = this.getInjector().getInstance(PowerCellMechanism.class);
        this.desiredSpeed = this.velocityAmount + powerCell.getFlywheelVelocity();
        super.begin();
        this.setAnalogOperationState(AnalogOperation.PowerCellFlywheelVelocity, desiredSpeed);
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    @Override
    public void update()
    {
        this.setAnalogOperationState(AnalogOperation.PowerCellFlywheelVelocity, desiredSpeed);
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
