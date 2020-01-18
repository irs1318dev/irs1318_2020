package frc.robot.mechanisms;

import frc.robot.*;
import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.common.Driver;
import frc.robot.common.robotprovider.IDashboardLogger;
import frc.robot.common.robotprovider.IColorMatch;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.*;

@Singleton
public class ControlPanelMechanism implements IMechanism
{
    private static final String logName = "color";

    private final IDashboardLogger logger;
    private final IColorSensorV3 sensor;
    private final IColorMatch colorMatch;
    private final IVictorSPX motor;
    private final IDriverStation ds;
    private boolean buttonPressed;

    private String gsm; 
    private TargetColor targetColor;
    private ColorMatchResult result;

    private Driver driver;

    private enum TargetColor{
        red,
        blue,
        green,
        yellow;
    }

    @Inject
    public ControlPanelMechanism(IDashboardLogger logger, IRobotProvider provider)
    {
        this.logger = logger;
        this.sensor = provider.getColorSensor();
        this.colorMatch = provider.getColorMatch();
        this.motor = provider.getVictorSPX(1);
        this.motor.setControlMode(TalonSRXControlMode.PercentOutput);
        this.ds = provider.getDriverStation();

        this.colorMatch.addColorMatch("red", TuningConstants.COLOR_MATCH_RED_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_RED_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_RED_TARGET_BLUE_PERCENTAGE);
        this.colorMatch.addColorMatch("green", TuningConstants.COLOR_MATCH_GREEN_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_GREEN_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_GREEN_TARGET_BLUE_PERCENTAGE);
        this.colorMatch.addColorMatch("blue",TuningConstants.COLOR_MATCH_BLUE_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_BLUE_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_BLUE_TARGET_BLUE_PERCENTAGE);
        this.colorMatch.addColorMatch("yellow", TuningConstants.COLOR_MATCH_YELLOW_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_YELLOW_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_YELLOW_TARGET_BLUE_PERCENTAGE);
    }

    @Override
    public void readSensors()
    {
        if(this.buttonPressed){
            this.gsm = ds.getGameSpecificMessage();

            RawColorRGBIR rawColor = sensor.getRawColor();
            int red = rawColor.getRed();
            int green = rawColor.getGreen();
            int blue = rawColor.getBlue();

            this.logger.logNumber(ControlPanelMechanism.logName, "red", red);
            this.logger.logNumber(ControlPanelMechanism.logName, "green", green);
            this.logger.logNumber(ControlPanelMechanism.logName, "blue", blue);
            this.logger.logNumber(ControlPanelMechanism.logName, "IR", rawColor.getIR());

            double total = red + green + blue;
            double redPercent = (double)red / total;
            double greenPercent = (double)green / total;
            double bluePercent = (double)blue / total;

            this.result = colorMatch.matchClosestColor(redPercent, greenPercent, bluePercent);
            this.logger.logString(ControlPanelMechanism.logName, "name", result.getName());
            this.logger.logNumber(ControlPanelMechanism.logName, "confidence", result.getConfidence());

            int proximity = sensor.getProximity();
            this.logger.logInteger(ControlPanelMechanism.logName, "proximity", proximity);

            //Mapped colors, taking the game specific message and mapping it to the color desired
            if(gsm.equals("G")){
                this.targetColor = TargetColor.yellow;
            }
            else if(gsm.equals("Y")){
                this.targetColor = TargetColor.green;
            }
            else if(gsm.equals("B")){
                this.targetColor = TargetColor.red;
            }
            else if(gsm.equals("R")){
                this.targetColor = TargetColor.blue;
            }

            this.logger.logString(ControlPanelMechanism.logName, "targetColorMapped", targetColor.toString());
            this.logger.logString(ControlPanelMechanism.logName, "Game Specific Message", this.gsm);

        }
        
    }

    @Override
    public void update()
    {
        this.buttonPressed = this.driver.getDigital(DigitalOperation.ControlPanelEnable);
        double speed = this.driver.getAnalog(AnalogOperation.ControlPanelMotorSpeed);
        this.motor.set(speed);
    }

    @Override
    public void stop()
    {
        this.motor.set(0.0);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
    public ColorMatchResult getResult(){
        return this.result;
    }
    public TargetColor getTargetColor(){
        return this.targetColor;
    }

}