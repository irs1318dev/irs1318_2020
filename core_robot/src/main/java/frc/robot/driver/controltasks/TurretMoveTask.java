package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.PowerCellMechanism;

/**
 * Task that applies a single operation from a group of related operations for a short period of time.
 * 
 */
public class TurretMoveTask extends TimedTask implements IControlTask
{
    private final boolean useTimeout;
    private final boolean absolute;
    private final double turnAmount;

    private double desiredAngle;
    private PowerCellMechanism powerCell;

    /**
     * Initializes a new TurretMoveTask
     * @param absolute or relative from current position
     * @param turnAmount the amount to turn (positive == left, negative == right)
     */
    public TurretMoveTask(boolean absolute, double turnAmount)
    {
        super(0.0);

        this.useTimeout = false;
        this.absolute = absolute;
        this.turnAmount = turnAmount;
    }

    /**
     * Initializes a new TurretMoveTask
     * @param duration to wait in seconds
     * @param absolute or relative from current position
     * @param turnAmount the amount to turn (positive == left, negative == right)
     */
    public TurretMoveTask(double duration, boolean absolute, double turnAmount)
    {
        super(duration);

        this.useTimeout = true;
        this.absolute = absolute;
        this.turnAmount = turnAmount;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        super.begin();

        this.powerCell = this.getInjector().getInstance(PowerCellMechanism.class);
        if (this.absolute)
        {
            this.desiredAngle = this.turnAmount;
        }
        else
        {
            this.desiredAngle = this.powerCell.getTurretPosition() + this.turnAmount;
        }

        this.setAnalogOperationState(AnalogOperation.PowerCellTurretPosition, this.desiredAngle);
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    @Override
    public void update()
    {
        this.setAnalogOperationState(AnalogOperation.PowerCellTurretPosition, this.desiredAngle);
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        super.end();

        this.setAnalogOperationState(AnalogOperation.PowerCellTurretPosition, TuningConstants.POWERCELL_TURRET_MAGIC_DONT_MOVE_VALUE);
    }

    @Override
    public boolean hasCompleted()
    {
        if (!TuningConstants.POWERCELL_TURRET_USE_PID)
        {
            return true;
        }

        // timeout if we are basing it on time
        if (this.useTimeout && super.hasCompleted())
        {
            return true;
        }

        // otherwise, complete when we are within an allowable range
        return Math.abs(this.powerCell.getTurretPosition() - this.desiredAngle) <= TuningConstants.POWERCELL_MIN_TURRET_OFFSET;
    }
}
