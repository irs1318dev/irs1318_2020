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
public class TurretMoveTask extends TimedTask implements IControlTask
{
    private final double turnAmount;
    private double desiredAngle;

    /**
     * Initializes a new TurretMoveTask
     * @param duration to wait in seconds
     * @param turnAmount the amount to turn (positive == left, negative == right)
     */
    public TurretMoveTask(double duration, double turnAmount)
    {
        super(duration);
        this.turnAmount = turnAmount;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        super.begin();

        PowerCellMechanism powerCell = this.getInjector().getInstance(PowerCellMechanism.class);
        this.desiredAngle = powerCell.getTurretPosition() + this.turnAmount;

        this.setAnalogOperationState(AnalogOperation.PowerCellTurretPosition, desiredAngle);
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    @Override
    public void update()
    {
        this.setAnalogOperationState(AnalogOperation.PowerCellTurretPosition, desiredAngle);
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        super.end();

        this.setAnalogOperationState(AnalogOperation.PowerCellTurretPosition, -1.0);
    }
}
