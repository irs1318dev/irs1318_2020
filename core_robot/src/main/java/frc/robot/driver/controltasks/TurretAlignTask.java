package frc.robot.driver.controltasks;

import frc.robot.driver.*;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.*;

public class TurretAlignTask extends ControlTaskBase implements IControlTask
{
    public PowerCellMechanism powerCell;

    public TurretAlignTask()
    {
    }

    @Override
    public void begin()
    {
        this.powerCell = this.getInjector().getInstance(PowerCellMechanism.class);
    }

    @Override
    public void update()
    {
        this.setAnalogOperationState(AnalogOperation.PowerCellTurretPosition, 0.0);
    }

    @Override
    public void end()
    {
    }

    @Override
    public boolean hasCompleted()
    {
        if (this.powerCell.getTurretPosition() == 0.0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}