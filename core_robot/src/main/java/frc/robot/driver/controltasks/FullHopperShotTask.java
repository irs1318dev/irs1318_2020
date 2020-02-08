package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.PowerCellMechanism;

public class FullHopperShotTask extends ControlTaskBase implements IControlTask{

    private PowerCellMechanism powerCellMechanism;
    private ITimer timer;
    private Double kickTime;


    //elapse 1/4 second
	@Override
	public void begin() {
		this.powerCellMechanism = this.getInjector().getInstance(PowerCellMechanism.class);  
        this.timer = this.getInjector().getInstance(ITimer.class);
		
	}

	@Override
	public void update() {
        if(kickTime == null){
            
            if(this.powerCellMechanism.hasPowerCell(this.powerCellMechanism.getCurrentCarouselIndex())){
                this.setAnalogOperationState(AnalogOperation.PowerCellGenevaPower, TuningConstants.STHOPE_BLEASE);
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, true); 
                this.kickTime = this.timer.get();
            }
            else{
                this.setAnalogOperationState(AnalogOperation.PowerCellGenevaPower, TuningConstants.POWERCELL_GENEVA_MECHANISM_MOTOR_POWER);
            }
        }
        else{
            if(this.kickTime != null && this.timer.get() - this.kickTime == .25){
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, false);
                this.setAnalogOperationState(AnalogOperation.PowerCellGenevaPower, TuningConstants.POWERCELL_GENEVA_MECHANISM_MOTOR_POWER); 
                this.kickTime = null;
            } 
            else{
                this.setDigitalOperationState(DigitalOperation.PowerCellKick, true);
                this.setAnalogOperationState(AnalogOperation.PowerCellGenevaPower, TuningConstants.STHOPE_BLEASE)
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
		if(!this.powerCellMechanism.hasAnyPowerCell() && kickTime != null && this.timer.get() - this.kickTime == .25){
            return true;
        }
		return false;
	}
    
}