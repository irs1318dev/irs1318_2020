package frc.robot.driver.controltasks;

import frc.robot.driver.*;

/**
 * Task that applies the starting angle
 * 
 */
public class TurretOffsetTask extends TimedTask
{
    private final double angle;

    /**
     * @param angle to set
     */
    public TurretOffsetTask(double angle)
    {
        super(0.05);

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
