package frc.robot.driver.controltasks;

import java.util.ArrayList;
import java.util.List;

import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.DigitalOperation;
import frc.robot.mechanisms.PowerCellMechanism;

public class ShootHopperSlotsTask extends ControlTaskBase
{
    private static final boolean MOVE_BACKWARDS = false;
    private static final double KICK_TIME = 0.3;
    private static final double SETTLE_TIME = 0.25;

    private final List<Integer> slots;

    private PowerCellMechanism powerCellMechanism;
    private ITimer timer;
    private Double stateTransitionTime;
    private int index;
    private ShotState currentState;

    public ShootHopperSlotsTask(int... slots)
    {
        this.slots = new ArrayList<Integer>(slots.length);
        for (int slot : slots)
        {
            this.slots.add(slot);
        }
    }

    @Override
    public void begin()
    {
        this.powerCellMechanism = this.getInjector().getInstance(PowerCellMechanism.class);
        this.timer = this.getInjector().getInstance(ITimer.class);

        this.currentState = ShotState.Moving;
        this.index = 0;
        this.stateTransitionTime = null;
    }

    @Override
    public void update()
    {
        if (this.currentState == ShotState.Moving)
        {
            int desiredSlot = this.slots.get(this.index);
            int currentSlot = this.powerCellMechanism.getCurrentCarouselIndex();
            if (currentSlot == desiredSlot)
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);

                this.currentState = ShotState.Settling;
                this.stateTransitionTime = this.timer.get();
            }
            else
            {
                if (!ShootHopperSlotsTask.MOVE_BACKWARDS)
                {
                    // only move forwards:
                    this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                    this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, true);
                    this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);
                }
                else
                {
                    // determine which direction to turn based on which way is closer...
                    int nextDistance = desiredSlot - currentSlot;
                    if (nextDistance < 0)
                    {
                        nextDistance += 5;
                    }

                    int prevDistance = currentSlot - desiredSlot;
                    if (prevDistance < 0)
                    {
                        prevDistance += 5;
                    }

                    if (prevDistance < nextDistance)
                    {
                        this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                        this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                        this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, true);
                    }
                    else
                    {
                        this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                        this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, true);
                        this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);
                    }
                }
            }
        }
        else if (this.currentState == ShotState.Settling)
        {
            double currentTime = this.timer.get();
            if (currentTime - this.stateTransitionTime >= ShootHopperSlotsTask.SETTLE_TIME)
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
        else //if (this.currentState == ShotState.Kicking)
        {
            if (this.timer.get() - this.stateTransitionTime >= ShootHopperSlotsTask.KICK_TIME)
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);

                this.currentState = ShotState.Moving;
                this.stateTransitionTime = null;
                this.index++;
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
        if (this.index >= this.slots.size())
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