package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.common.robotprovider.ColorMatchResult;
import frc.robot.driver.*;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.ControlPanelMechanism;
import frc.robot.mechanisms.ControlPanelMechanism.TargetColor;

public class ColorSpinTask extends ControlTaskBase implements IControlTask
{
    private ControlPanelMechanism controlPanel;

    private TargetColor targetColor;
    private int counter;
    private boolean seenRed;

    public ColorSpinTask()
    {
    }

    @Override
    public void begin()
    {
        this.controlPanel = this.getInjector().getInstance(ControlPanelMechanism.class);

        this.targetColor = this.controlPanel.getTargetColor();
        this.counter = 0;
        this.seenRed = false;
    }

    @Override
    public void update()
    {
        ColorMatchResult colorMatch = this.controlPanel.getColorResult();
        if (this.targetColor == TargetColor.None)
        {
            this.setAnalogOperationState(AnalogOperation.ControlPanelSpinSpeed, TuningConstants.CONTROL_PANEL_POSITION_SPIN_SPEED);

            if (!this.seenRed && colorMatch.getName().equals("Red"))
            {
                this.seenRed = true; // seenRed prevents double counting - if we see it once, it cannot count it again 
                this.counter++;
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
    public void end()
    {
        this.setAnalogOperationState(AnalogOperation.ControlPanelSpinSpeed, TuningConstants.STHOPE_BLEASE);
    }

    @Override
    public boolean hasCompleted()
    {
        ColorMatchResult colorMatch = this.controlPanel.getColorResult();
        if (this.counter >= 7 || colorMatch.getName().equals(this.targetColor.toString()))
        {
            return true;
        }

        return false;
    }
}