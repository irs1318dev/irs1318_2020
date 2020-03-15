package frc.robot.driver.controltasks;

import frc.robot.driver.AnalogOperation;
import frc.robot.mechanisms.PowerCellMechanism;

/**
 * Task that modifies the flywheel speed based on a fixed amount
 * 
 */
public class FlywheelModifySpeedTask extends TimedTask
{
    private final double velocityAmount;

    private double desiredSpeed;

    /**
     * Initializes a new FlywheelModifySpeedTask
     * @param velocityAmount the amount to change speed by
     */
    public FlywheelModifySpeedTask(double velocityAmount)
    {
        super(0.1);

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
        this.desiredSpeed = powerCell.getFlywheelVelocitySetpoint() + this.velocityAmount;
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
    }
}
