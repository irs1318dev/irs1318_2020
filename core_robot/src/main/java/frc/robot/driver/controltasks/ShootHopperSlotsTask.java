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

    private final List<Integer> slots;

    private PowerCellMechanism powerCellMechanism;
    private ITimer timer;
    private Double kickTime;
    private int index;

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

        this.index = 0;
        this.kickTime = null;
    }

    @Override
    public void update()
    {
        if (this.kickTime == null)
        {
            int desiredSlot = this.slots.get(this.index);
            int currentSlot = this.powerCellMechanism.getCurrentCarouselIndex();
            // System.out.println("desired slot (" + this.index + "): " + desiredSlot + ", current " + currentSlot);
            if (currentSlot == desiredSlot)
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, true);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);

                this.kickTime = this.timer.get();
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
        else
        {
            if (this.timer.get() - this.kickTime >= ShootHopperSlotsTask.KICK_TIME)
            {
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToNextSlot, false);
                this.setDigitalOperationState(DigitalOperation.PowerCellMoveToPreviousSlot, false);

                this.kickTime = null;
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
}