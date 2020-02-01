package frc.robot.mechanisms;

import frc.robot.*;
import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.*;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ControlPanelMechanism implements IMechanism
{
    private static final String logName = "color";

    private final IDashboardLogger logger;
    private final IColorSensorV3 sensor;
    private final IColorMatch colorMatch;
    private final IVictorSPX spinnerMotor;
    private final IDriverStation ds;

    private boolean buttonPressed;

    private String gsm; 
    private TargetColor targetColor;
    private ColorMatchResult colorResult;

    private Driver driver;

    private enum TargetColor
    {
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
        this.colorMatch.addColorMatch("red", TuningConstants.COLOR_MATCH_RED_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_RED_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_RED_TARGET_BLUE_PERCENTAGE);
        this.colorMatch.addColorMatch("green", TuningConstants.COLOR_MATCH_GREEN_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_GREEN_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_GREEN_TARGET_BLUE_PERCENTAGE);
        this.colorMatch.addColorMatch("blue",TuningConstants.COLOR_MATCH_BLUE_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_BLUE_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_BLUE_TARGET_BLUE_PERCENTAGE);
        this.colorMatch.addColorMatch("yellow", TuningConstants.COLOR_MATCH_YELLOW_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_YELLOW_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_YELLOW_TARGET_BLUE_PERCENTAGE);

        this.ds = provider.getDriverStation();

        this.spinnerMotor = provider.getVictorSPX(ElectronicsConstants.CONTROL_PANEL_SPINNER_CAN_ID);
        this.spinnerMotor.setControlMode(TalonSRXControlMode.PercentOutput);
        this.spinnerMotor.setNeutralMode(MotorNeutralMode.Brake);
    }

    @Override
    public void readSensors()
    {
        if (this.buttonPressed)
        {
            this.gsm = this.ds.getGameSpecificMessage();

            RawColorRGBIR rawColor = this.sensor.getRawColor();
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

            this.colorResult = this.colorMatch.matchClosestColor(redPercent, greenPercent, bluePercent);
            this.logger.logString(ControlPanelMechanism.logName, "name", this.colorResult.getName());
            this.logger.logNumber(ControlPanelMechanism.logName, "confidence", this.colorResult.getConfidence());

            int proximity = this.sensor.getProximity();
            this.logger.logInteger(ControlPanelMechanism.logName, "proximity", proximity);

            // Mapped colors, taking the game specific message and mapping it to the color desired
            if (this.gsm.equals("G"))
            {
                this.targetColor = TargetColor.yellow;
            }
            else if (this.gsm.equals("Y"))
            {
                this.targetColor = TargetColor.green;
            }
            else if (this.gsm.equals("B"))
            {
                this.targetColor = TargetColor.red;
            }
            else if (this.gsm.equals("R"))
            {
                this.targetColor = TargetColor.blue;
            }

            this.logger.logString(ControlPanelMechanism.logName, "targetColorMapped", this.targetColor.toString());
            this.logger.logString(ControlPanelMechanism.logName, "gameMessage", this.gsm);
        }
    }

    @Override
    public void update()
    {
        this.buttonPressed = this.driver.getDigital(DigitalOperation.ControlPanelEnable);
        double speed = this.driver.getAnalog(AnalogOperation.ControlPanelMotorSpeed);
        this.spinnerMotor.set(speed);
    }

    @Override
    public void stop()
    {
        this.spinnerMotor.set(0.0);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public ColorMatchResult getColorResult()
    {
        return this.colorResult;
    }

    public TargetColor getTargetColor()
    {
        return this.targetColor;
    }
}