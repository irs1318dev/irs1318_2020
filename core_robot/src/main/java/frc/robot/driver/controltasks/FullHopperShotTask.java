package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.DigitalOperation;
import frc.robot.mechanisms.PowerCellMechanism;

public class FullHopperShotTask extends ControlTaskBase
{
    private PowerCellMechanism powerCellMechanism;
    private ITimer timer;
    private Double kickTime;
    private boolean hasTB;
    private int shotsShot;
    private int previousSlot;
    
    public FullHopperShotTask()
    {
    }

    @Override
    public void begin()
    {
        hasTB = TuningConstants.POWERCELL_HAS_THROUGH_BEAM_SENSOR;
        shotsShot = 0;
        this.powerCellMechanism = this.getInjector().getInstance(PowerCellMechanism.class);
        this.timer = this.getInjector().getInstance(ITimer.class);
        this.previousSlot = this.powerCellMechanism.getCurrentCarouselIndex();
    }

    @Override
    public void update()
    {
        if (this.kickTime == null)
        {
            int currentSlot = this.powerCellMechanism.getCurrentCarouselIndex();
            if(hasTB)
            {
                if (this.powerCellMechanism.hasPowerCell(currentSlot))
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
            else if (currentSlot == previousSlot)
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, true);
            }
            else
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, true);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                this.kickTime = this.timer.get();
                shotsShot ++;
                this.previousSlot = currentSlot;
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

        if(shotsShot >= 5)
        {
            return true;
        }
        return false;
    }
}