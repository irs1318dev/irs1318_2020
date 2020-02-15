package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.driver.*;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.*;

public class TurretAlignTask extends ControlTaskBase implements IControlTask
{
    private final double angle;
    private PowerCellMechanism powerCell;
    
    public TurretAlignTask(double angle)
    {
        this.angle = angle;
    }

    @Override
    public void begin()
    {
        this.powerCell = this.getInjector().getInstance(PowerCellMechanism.class);
    }

    @Override
    public void update()
    {
        this.setAnalogOperationState(AnalogOperation.PowerCellTurretPosition, this.angle);
    }

    @Override
    public void end()
    {
    }

    @Override
    public boolean hasCompleted()
    {
        if (Math.abs(this.powerCell.getTurretPosition() - this.angle) <= TuningConstants.POWERCELL_MIN_TURRET_OFFSET)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}