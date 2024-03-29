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
    private final ILogger logger;

    private final IColorSensorV3 sensor;
    private final IColorMatch colorMatch;
    private final IDriverStation ds;

    private final IDoubleSolenoid extender;
    private final IVictorSPX spinnerMotor;

    private Driver driver;

    private String gsm;
    private TargetColor targetColor;
    private ColorMatchResult colorResult;
    private boolean isExtended;

    public enum TargetColor
    {
        None,
        Red,
        Blue,
        Green,
        Yellow;
    }

    @Inject
    public ControlPanelMechanism(LoggingManager logger, IRobotProvider provider)
    {
        this.logger = logger;

        this.sensor = provider.getColorSensor();
        this.colorMatch = provider.getColorMatch();
        this.colorMatch.addColorMatch("Red", TuningConstants.COLOR_MATCH_RED_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_RED_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_RED_TARGET_BLUE_PERCENTAGE);
        this.colorMatch.addColorMatch("Green", TuningConstants.COLOR_MATCH_GREEN_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_GREEN_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_GREEN_TARGET_BLUE_PERCENTAGE);
        this.colorMatch.addColorMatch("Blue",TuningConstants.COLOR_MATCH_BLUE_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_BLUE_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_BLUE_TARGET_BLUE_PERCENTAGE);
        this.colorMatch.addColorMatch("Yellow", TuningConstants.COLOR_MATCH_YELLOW_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_YELLOW_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_YELLOW_TARGET_BLUE_PERCENTAGE);

        this.ds = provider.getDriverStation();

        this.extender = provider.getDoubleSolenoid(ElectronicsConstants.PCM_A_MODULE, ElectronicsConstants.CONTROLPANEL_EXTENDER_FORWARD_PCM, ElectronicsConstants.CONTROLPANEL_EXTENDER_REVERSE_PCM);

        this.spinnerMotor = provider.getVictorSPX(ElectronicsConstants.CONTROLPANEL_SPINNER_CAN_ID);
        this.spinnerMotor.setInvertOutput(HardwareConstants.CONTROLPANEL_SPINNER_INVERT_OUTPUT);
        this.spinnerMotor.setControlMode(TalonSRXControlMode.PercentOutput);
        this.spinnerMotor.setNeutralMode(MotorNeutralMode.Brake);

        this.isExtended = false;
    }

    @Override
    public void readSensors()
    {
        if (this.isExtended)
        {
            this.gsm = this.ds.getGameSpecificMessage();

            this.sensor.start();
            RawColorRGBIR rawColor = this.sensor.getRawColor();
            if (rawColor != null)
            {
                int red = rawColor.getRed();
                int green = rawColor.getGreen();
                int blue = rawColor.getBlue();

                this.logger.logNumber(LoggingKey.ControlPanelRed, red);
                this.logger.logNumber(LoggingKey.ControlPanelGreen, green);
                this.logger.logNumber(LoggingKey.ControlPanelBlue, blue);
                this.logger.logNumber(LoggingKey.ControlPanelIR, rawColor.getIR());

                double total = red + green + blue;
                double redPercent = (double)red / total;
                double greenPercent = (double)green / total;
                double bluePercent = (double)blue / total;

                this.colorResult = this.colorMatch.matchClosestColor(redPercent, greenPercent, bluePercent);
                this.logger.logString(LoggingKey.ControlPanelName, this.colorResult.getName());
                this.logger.logNumber(LoggingKey.ControlPanelConfidence, this.colorResult.getConfidence());

                int proximity = this.sensor.getProximity();
                this.logger.logInteger(LoggingKey.ControlPanelProximity, proximity);
            }
            else
            {
                this.logger.logNumber(LoggingKey.ControlPanelRed, -1);
                this.logger.logNumber(LoggingKey.ControlPanelGreen, -1);
                this.logger.logNumber(LoggingKey.ControlPanelBlue, -1);
                this.logger.logNumber(LoggingKey.ControlPanelIR, -1);
                this.logger.logString(LoggingKey.ControlPanelName, "");
                this.logger.logNumber(LoggingKey.ControlPanelConfidence, -1);
                this.logger.logInteger(LoggingKey.ControlPanelProximity, -1);
            }

            // Mapped colors, taking the game specific message and mapping it to the color desired along the front edge
            if (this.gsm != null || this.gsm.equals(""))
            {
                this.targetColor = TargetColor.None;
            }
            else if (this.gsm.equals("G"))
            {
                this.targetColor = TargetColor.Yellow;
            }
            else if (this.gsm.equals("Y"))
            {
                this.targetColor = TargetColor.Green;
            }
            else if (this.gsm.equals("B"))
            {
                this.targetColor = TargetColor.Red;
            }
            else if (this.gsm.equals("R"))
            {
                this.targetColor = TargetColor.Blue;
            }
            else
            {
                // unexpected
                this.targetColor = TargetColor.None;
            }

            this.logger.logString(LoggingKey.ControlPanelTargetColorMapped, this.targetColor.toString());
            this.logger.logString(LoggingKey.ControlPanelGameMessage, this.gsm);
        }
        else
        {
            this.sensor.stop();

            this.logger.logNumber(LoggingKey.ControlPanelRed, -1);
            this.logger.logNumber(LoggingKey.ControlPanelGreen, -1);
            this.logger.logNumber(LoggingKey.ControlPanelBlue, -1);
            this.logger.logNumber(LoggingKey.ControlPanelIR, -1);
            this.logger.logString(LoggingKey.ControlPanelName, "");
            this.logger.logNumber(LoggingKey.ControlPanelConfidence, -1);
            this.logger.logInteger(LoggingKey.ControlPanelProximity, -1);
            this.logger.logString(LoggingKey.ControlPanelTargetColorMapped, "");
            this.logger.logString(LoggingKey.ControlPanelGameMessage, "");
            this.targetColor = TargetColor.None;
            this.colorResult = null;
        }
    }

    @Override
    public void update()
    {
        if (this.driver.getDigital(DigitalOperation.ControlPanelExtend))
        {
            this.isExtended = true;
            this.extender.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.ControlPanelRetract))
        {
            this.isExtended = false;
            this.extender.set(DoubleSolenoidValue.Reverse);
        }

        this.logger.logBoolean(LoggingKey.ControlPanelExtend, this.isExtended);
        if (this.isExtended)
        {
            double speed = this.driver.getAnalog(AnalogOperation.ControlPanelSpinSpeed);
            this.spinnerMotor.set(speed);
        }
        else
        {
            this.spinnerMotor.stop();
        }
    }

    @Override
    public void stop()
    {
        this.sensor.stop();
        this.extender.set(DoubleSolenoidValue.Off);
        this.spinnerMotor.stop();
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