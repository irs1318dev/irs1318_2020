package frc.robot.driver.controltasks;

import frc.robot.driver.*;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.PowerCellMechanism;

/**
 * Task that applies the starting angle
 * 
 */
public class TurretOffsetTask extends TimedTask implements IControlTask
{
    private final double angle;

    /**
     * @param angle to set
     */
    public TurretOffsetTask(double angle)
    {
        super(1.0);

        this.angle = angle;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        super.begin();
        this.setAnalogOperationState(AnalogOperation.PowerCellTurretOffset, this.angle);
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    @Override
    public void update()
    {
        this.setAnalogOperationState(AnalogOperation.PowerCellTurretOffset, this.angle);
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
