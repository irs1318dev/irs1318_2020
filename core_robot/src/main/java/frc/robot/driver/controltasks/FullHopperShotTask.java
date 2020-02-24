package frc.robot.driver.controltasks;

import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.PowerCellMechanism;

public class FullHopperShotTask extends ControlTaskBase implements IControlTask
{
    private PowerCellMechanism powerCellMechanism;
    private ITimer timer;
    private Double kickTime;

    public FullHopperShotTask()
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
        if (this.kickTime == null)
        {
            if (this.powerCellMechanism.hasPowerCell(this.powerCellMechanism.getCurrentCarouselIndex()))
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, true);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                this.kickTime = this.timer.get();
            }
            else
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, true);
            }
        }
        else
        {
            if (this.kickTime != null && this.timer.get() - this.kickTime >= .25)
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, true);
                this.kickTime = null;
            }
            else
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, true);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
            }
        }
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
        this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
    }

    @Override
    public boolean hasCompleted()
    {
        if (!this.powerCellMechanism.hasAnyPowerCell() &&
            (this.kickTime == null || this.timer.get() - this.kickTime == .25))
        {
            return true;
        }

        return false;
    }
}