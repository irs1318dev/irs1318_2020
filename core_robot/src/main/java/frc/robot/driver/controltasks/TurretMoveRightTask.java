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
public class TurretMoveRightTask extends TimedTask implements IControlTask
{
    private final double turnAmount;
    private double desiredAngle;

    /**
     * Initializes a new CompositeOperationTask
     * @param duration to wait in seconds
     * @param toPerform the operation to perform by setting to true for duration
     * @param possibleOperations to set of linked operations that should be set to false for duration
     */
    public TurretMoveRightTask(double duration, double turnAmount)
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
        PowerCellMechanism powerCell = this.getInjector().getInstance(PowerCellMechanism.class);
        this.desiredAngle = this.turnAmount + powerCell.getTurretPosition();
        super.begin();
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
