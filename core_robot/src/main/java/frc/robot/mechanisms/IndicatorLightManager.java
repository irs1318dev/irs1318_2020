package frc.robot.mechanisms;

import frc.robot.*;
import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.common.Driver;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Indicator Light manager
 * 
 * This class manages indicator lights on the robot.
 * 
 */
@Singleton
public class IndicatorLightManager implements IMechanism
{
    private static final double FlashingFrequency = 0.2;
    private static final double FlashingComparisonFrequency = IndicatorLightManager.FlashingFrequency / 2.0;

    private final ITimer timer;
    private final IDashboardLogger logger;

    private final IDigitalOutput indicator;

    /**
     * Initializes a new IndicatorLightManager
     * @param provider for obtaining electronics objects
     * @param timer to use
     */
    @Inject
    public IndicatorLightManager(
        IRobotProvider provider,
        IDashboardLogger logger,
        ITimer timer)
    {
        this.timer = timer;
        this.logger = logger;

        this.indicator = provider.getDigitalOutput(ElectronicsConstants.INDICATOR_LIGHT_DIO);
    }

    /**
     * set the driver that the mechanism should use
     * @param driver to use
     */
    @Override
    public void setDriver(Driver driver)
    {
    }

    @Override
    public void readSensors()
    {
    }

    /**
     * calculate the various outputs to use based on the inputs and apply them to the outputs for the relevant mechanism
     */
    @Override
    public void update()
    {
    }

    /**
     * stop the relevant component
     */
    @Override
    public void stop()
    {
        this.indicator.set(false);
    }

    private void controlLight(IRelay indicatorLight, LightMode mode)
    {
        if (mode == LightMode.On)
        {
            indicatorLight.set(RelayValue.Forward);
        }
        else if (mode == LightMode.Off)
        {
            indicatorLight.set(RelayValue.Off);
        }
        else
        {
            double currentTime = this.timer.get();
            if (currentTime % IndicatorLightManager.FlashingFrequency >= IndicatorLightManager.FlashingComparisonFrequency)
            {
                indicatorLight.set(RelayValue.Forward);
            }
            else
            {
                indicatorLight.set(RelayValue.Off);
            }
        }
    }

    private enum LightMode
    {
        Off,
        Flashing,
        On,
    }
}
