package frc.robot.driver.controltasks;

import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.PowerCellMechanism;

public class TracerShotTask extends ControlTaskBase implements IControlTask
{
    private PowerCellMechanism powerCellMechanism;
    private ITimer timer;
    private Double kickTime;

    public TracerShotTask()
    {
    }

    @Override
    public void begin()
    {
        this.powerCellMechanism = this.getInjector().getInstance(PowerCellMechanism.class);  
        this.timer = this.getInjector().getInstance(ITimer.class);
    }

    @Override
    public void update()
    {
        if (this.kickTime != null || this.powerCellMechanism.hasPowerCell(this.powerCellMechanism.getCurrentCarouselIndex())) 
        {
            this.setDigitalOperationState(DigitalOperation.PowerCellKick, true);
            if (this.kickTime == null)
            {
                this.kickTime = this.timer.get();
            }
            
        }
        else
        {
            this.setDigitalOperationState(DigitalOperation.PowerCellMoveOneSlot, true);
        }
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(DigitalOperation.PowerCellMoveOneSlot, false);
        this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
    }

    @Override
    public boolean hasCompleted()
    {
        if (this.kickTime != null && this.timer.get() - this.kickTime >= .5)
        {
            return true; 
        }
        
        return false;
    }
}