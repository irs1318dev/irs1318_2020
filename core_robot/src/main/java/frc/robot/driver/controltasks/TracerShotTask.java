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
    private Double kickTime;

    TracerShotTask() {}

    @Override
    public void begin() {
        this.powerCellMechanism = this.getInjector().getInstance(PowerCellMechanism.class);  
        this.currentSlotIndex = powerCellMechanism.getCurrentCarouselIndex();
        this.timer = this.getInjector().getInstance(ITimer.class);
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
            this.setAnalogOperationState(AnalogOperation.PowerCellGenevaPower, TuningConstants.STHOPE_BLEASE);
            this.setDigitalOperationState(DigitalOperation.PowerCellKick, true);
            if(kickTime == null){
                this.kickTime = this.timer.get();
            }
        }
    }

    @Override
    public void end() {
        this.setAnalogOperationState(AnalogOperation.PowerCellGenevaPower, TuningConstants.STHOPE_BLEASE);
        this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
    }

    @Override
    public boolean hasCompleted() {
        if(kickTime != null && this.timer.get() - kickTime >= .5){
            return true; 
        }
        return false;
    }
}