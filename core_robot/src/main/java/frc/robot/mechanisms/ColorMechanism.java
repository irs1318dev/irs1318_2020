package frc.robot.mechanisms;

import frc.robot.*;
import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.common.Driver;
import frc.robot.common.robotprovider.IDashboardLogger;
import frc.robot.common.robotprovider.IColorMatch;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.*;

@Singleton
public class ColorMechanism implements IMechanism
{
    private static final String logName = "color";

    private final IDashboardLogger logger;
    private final IColorSensorV3 sensor;
    private final IColorMatch color;


    private Driver driver;

    @Inject
    public ColorMechanism(IDashboardLogger logger, IRobotProvider provider)
    {
        this.logger = logger;
        this.sensor = provider.getColorSensor();
        this.color = provider.getColorMatch();

        this.color.addColorMatch("red", TuningConstants.COLOR_MATCH_RED_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_RED_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_RED_TARGET_BLUE_PERCENTAGE);
        this.color.addColorMatch("green", TuningConstants.COLOR_MATCH_GREEN_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_GREEN_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_GREEN_TARGET_BLUE_PERCENTAGE);
        this.color.addColorMatch("blue",TuningConstants.COLOR_MATCH_BLUE_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_BLUE_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_BLUE_TARGET_BLUE_PERCENTAGE);
        this.color.addColorMatch("yellow", TuningConstants.COLOR_MATCH_YELLOW_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_YELLOW_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_YELLOW_TARGET_BLUE_PERCENTAGE);
    }

    @Override
    public void readSensors()
    {        
        RawColorRGBIR rawColor = sensor.getRawColor();
        int red = rawColor.getRed();
        int green = rawColor.getGreen();
        int blue = rawColor.getBlue();

        this.logger.logNumber(ColorMechanism.logName, "red", red);
        this.logger.logNumber(ColorMechanism.logName, "green", green);
        this.logger.logNumber(ColorMechanism.logName, "blue", blue);
        this.logger.logNumber(ColorMechanism.logName, "IR", rawColor.getIR());

        double total = red + green + blue;
        double redPercent = (double)red / total;
        double greenPercent = (double)green / total;
        double bluePercent = (double)blue / total;

        ColorMatchResult result = color.matchClosestColor(redPercent, greenPercent, bluePercent);
        this.logger.logString(ColorMechanism.logName, "name", result.getName());
        this.logger.logNumber(ColorMechanism.logName, "confidence", result.getConfidence());

        int proximity = sensor.getProximity();
        this.logger.logInteger(ColorMechanism.logName, "proximity", proximity);
    }

    @Override
    public void update()
    {
    }

    @Override
    public void stop()
    {
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}