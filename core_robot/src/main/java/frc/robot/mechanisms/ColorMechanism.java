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
    public ColorMechanism(IDashboardLogger logger, IRobotProvider provider, IColorMatch color)
    {
        this.logger = logger;
        this.sensor = provider.getColorSensor();
        this.color = provider.getColorMatch();
    }

    @Override
    public void readSensors()
    {
        RawColorRGBIR rawColor = sensor.getRawColor();
        logger.logNumber(ColorMechanism.logName,"blue",rawColor.getBlue());
        logger.logNumber(ColorMechanism.logName,"red",rawColor.getRed());
        logger.logNumber(ColorMechanism.logName,"green",rawColor.getGreen());
        logger.logNumber(ColorMechanism.logName,"IR", rawColor.getIR());

        //percentages of color
        double red = (double)rawColor.getRed()/((double) rawColor.getRed() + (double)rawColor.getGreen() + (double)rawColor.getBlue());
        double green = (double)rawColor.getGreen()/((double) rawColor.getRed() + (double)rawColor.getGreen() + (double)rawColor.getBlue());
        double blue = (double)rawColor.getBlue()/((double) rawColor.getRed() + (double)rawColor.getGreen() + (double)rawColor.getBlue());

        color.matchClosestColor(red, green, blue);

        int proximity = sensor.getProximity();
        logger.logInteger(ColorMechanism.logName, "proximity", proximity);


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