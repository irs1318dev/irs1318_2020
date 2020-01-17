package frc.robot.mechanisms;

import frc.robot.*;
import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.common.Driver;
import frc.robot.common.robotprovider.IDashboardLogger;

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

    private Driver driver;

    @Inject
    public ColorMechanism(IDashboardLogger logger, IRobotProvider provider)
    {
        this.logger = logger;
        this.sensor = provider.getColorSensor();
    }

    @Override
    public void readSensors()
    {
        RawColorRGBIR rawColor = sensor.getRawColor();
        logger.logNumber(ColorMechanism.logName,"blue",rawColor.getBlue());
        logger.logNumber(ColorMechanism.logName,"red",rawColor.getRed());
        logger.logNumber(ColorMechanism.logName,"green",rawColor.getGreen());
        logger.logNumber(ColorMechanism.logName,"IR", rawColor.getIR());

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