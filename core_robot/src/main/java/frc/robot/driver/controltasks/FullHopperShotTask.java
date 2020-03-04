package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.DigitalOperation;
import frc.robot.mechanisms.PowerCellMechanism;

public class FullHopperShotTask extends ControlTaskBase
{
    private static final double KICK_TIME = 0.3;
    private static final double SETTLE_TIME = 0.25;

    private PowerCellMechanism powerCellMechanism;
    private ITimer timer;
    private Double stateTransitionTime;
    private int shotsShot;
    private int previousSlot;
    private ShotState currentState;
    
    public FullHopperShotTask()
    {
    }

    @Override
    public void begin()
    {
        this.powerCellMechanism = this.getInjector().getInstance(PowerCellMechanism.class);
        this.timer = this.getInjector().getInstance(ITimer.class);
        this.previousSlot = this.powerCellMechanism.getCurrentCarouselIndex();

        this.currentState = ShotState.Moving;
        this.shotsShot = 0;
    }

    @Override
    public void update()
    {
        if (this.currentState == ShotState.Moving)
        {
            int currentSlot = this.powerCellMechanism.getCurrentCarouselIndex();
            if (TuningConstants.POWERCELL_HAS_THROUGH_BEAM_SENSOR)
            {
                if (this.powerCellMechanism.hasPowerCell(currentSlot))
                {
                    this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                    this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                    this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);

                    this.stateTransitionTime = this.timer.get();
                    this.currentState = ShotState.Settling;
                }
                else
                {
                    this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                    this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, true);
                    this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);
                }
            }
            else if (currentSlot == this.previousSlot)
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, true);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);
            }
            else
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);

                this.stateTransitionTime = this.timer.get();
                this.currentState = ShotState.Settling;
                this.shotsShot++;
                this.previousSlot = currentSlot;
            }
        }
        else if (this.currentState == ShotState.Settling)
        {
            double currentTime = this.timer.get();
            if (this.stateTransitionTime != null && currentTime - this.stateTransitionTime >= FullHopperShotTask.SETTLE_TIME)
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, true);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);

                this.stateTransitionTime = currentTime;
                this.currentState = ShotState.Kicking;
            }
            else
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);
            }
        }
        else if (this.currentState == ShotState.Kicking)
        {
            if (this.stateTransitionTime != null && this.timer.get() - this.stateTransitionTime >= FullHopperShotTask.KICK_TIME)
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, true);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);

                this.stateTransitionTime = null;
                this.currentState = ShotState.Moving;
            }
            else
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, true);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);
            }
        }
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
        this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);
        this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
    }

    @Override
    public boolean hasCompleted()
    {
        if (TuningConstants.POWERCELL_HAS_THROUGH_BEAM_SENSOR &&
            !this.powerCellMechanism.hasAnyPowerCell() &&
            (this.currentState == ShotState.Moving && (this.stateTransitionTime == null || this.timer.get() - this.stateTransitionTime == FullHopperShotTask.KICK_TIME)))
        {
            return true;
        }

        if (!TuningConstants.POWERCELL_HAS_THROUGH_BEAM_SENSOR &&
            this.shotsShot >= 5 &&
            (this.currentState == ShotState.Moving && (this.stateTransitionTime == null || this.timer.get() - this.stateTransitionTime == FullHopperShotTask.KICK_TIME)))
        {
            return true;
        }

        return false;
    }

    private enum ShotState
    {
        Moving,
        Settling,
        Kicking,
    }
}