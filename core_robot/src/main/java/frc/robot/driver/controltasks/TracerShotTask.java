package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.PowerCellMechanism;

public class TracerShotTask extends ControlTaskBase implements IControlTask
{
    private PowerCellMechanism powerCellMechanism;
    private int currentSlotIndex;
    private ITimer timer;
    private double startTime;
    private double time;

    TracerShotTask() {}

    @Override
    public void begin() {
        this.powerCellMechanism = this.getInjector().getInstance(PowerCellMechanism.class);  
        this.currentSlotIndex = powerCellMechanism.getCurrentCarouselIndex();
        this.timer = this.getInjector().getInstance(ITimer.class);
        this.startTime = this.timer.get();
        this.time = 0;
    }

    @Override
    public void update() {
        boolean toTurn = !this.powerCellMechanism.hasPowerCell(this.powerCellMechanism.getCurrentCarouselIndex());

        if (toTurn && this.powerCellMechanism.getCurrentCarouselIndex() == currentSlotIndex) // if no power cell to kick, rotate
        {
            this.setAnalogOperationState(AnalogOperation.PowerCellGenevaPower, TuningConstants.POWERCELL_GENEVA_MECHANISM_MOTOR_POWER);
        }
        else if (toTurn && this.powerCellMechanism.getCurrentCarouselIndex() != currentSlotIndex) // if no power cell to kick, but we've rotated one slot, keep rotating
        {
            this.currentSlotIndex = this.powerCellMechanism.getCurrentCarouselIndex();
            this.setAnalogOperationState(AnalogOperation.PowerCellGenevaPower, TuningConstants.POWERCELL_GENEVA_MECHANISM_MOTOR_POWER);
        }
        else // if power cell to kick, stop motor and kick piston
        {
            this.setAnalogOperationState(AnalogOperation.PowerCellGenevaPower, 0.0);
            this.setDigitalOperationState(DigitalOperation.PowerCellKick, true);
        }
    }

    @Override
    public void end() {
        this.time = this.timer.get() - this.startTime;

    }

    @Override
    public boolean hasCompleted() {
        // TODO Auto-generated method stub
        return false; 
    }
}