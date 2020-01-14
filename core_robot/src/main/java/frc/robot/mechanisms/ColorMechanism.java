package frc.robot.mechanisms;

import frc.robot.*;
import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.common.Driver;
import frc.robot.vision.common.VisionProcessingState;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.*;

@Singleton
public class ColorMechanism implements IMechanism
{
    private final IDashboardLogger logger;
    private final IColorSensor sensor;

    @Inject
    public ColorMechanism(IDashboardLogger logger, IRobotProvider provider)
    {
        this.logger = logger;
        this.sensor = provider.getColorSensor();
    }

}