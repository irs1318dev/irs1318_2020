package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.common.robotprovider.ColorMatchResult;
import frc.robot.driver.*;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.ControlPanelMechanism;
import frc.robot.mechanisms.ControlPanelMechanism.TargetColor;

public class SpinTask extends ControlTaskBase implements IControlTask
{
    public ControlPanelMechanism controlPanel;
    public TargetColor targetColor;
    public int counter;
    public boolean seenRed;

    public SpinTask(){
    }

    @Override
    public void begin() {
        this.controlPanel = this.getInjector().getInstance(ControlPanelMechanism.class);
        this.targetColor = controlPanel.getTargetColor();
        this.counter = 0;
        this.seenRed = false;
    }

    @Override
    public void update() {
        ColorMatchResult colorMatch = controlPanel.getColorResult();
        if(targetColor == TargetColor.None)
        {
            this.setAnalogOperationState(AnalogOperation.ControlPanelSpinSpeed, TuningConstants.CONTROL_PANEL_POSITION_SPIN_SPEED);

            if(colorMatch.toString().equals("Red") && !this.seenRed)
            {
                this.seenRed = true; // seenRed prevents double counting - if we see it once, it cannot count it again 
                counter++;
            }
            else 
            {   
                this.seenRed = false;
            } 
        }  
        else
        {
            this.setAnalogOperationState(AnalogOperation.ControlPanelSpinSpeed, TuningConstants.CONTROL_PANEL_ROTATION_SPIN_SPEED);
        }
    }

    @Override
    public void end() {
        this.setAnalogOperationState(AnalogOperation.ControlPanelSpinSpeed, TuningConstants.STHOPE_BLEASE);
    }

    @Override
    public boolean hasCompleted() {
        ColorMatchResult colorMatch = controlPanel.getColorResult();
        if(counter >= 7 || colorMatch.toString().equals(targetColor.toString()))
        {
            return true;
        }
        return false;
    }

    
}