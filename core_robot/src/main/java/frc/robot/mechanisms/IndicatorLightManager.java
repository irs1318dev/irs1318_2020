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
    private enum LightMode
    {
        Off,
        Flashing,
        On,
    }

    private static final double FlashingFrequency = 0.5;
    private static final double FlashingComparisonFrequency = IndicatorLightManager.FlashingFrequency / 2.0;

    private final ITimer timer;

    private final IDigitalOutput spunUpIndicator;
    private final PowerCellMechanism powerCellMechanism;

    @Inject
    public IndicatorLightManager(
        IRobotProvider provider,
        ITimer timer,
        PowerCellMechanism powerCellMechanism)
    {
        this.timer = timer;

        this.spunUpIndicator = provider.getDigitalOutput(ElectronicsConstants.INDICATOR_LIGHT_SPUN_UP_DIO);
        this.powerCellMechanism = powerCellMechanism;
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

    @Override
    public void update()
    {
        LightMode spunUpMode = LightMode.Off;
        if (this.powerCellMechanism.getFlywheelVelocitySetpoint() != 0.0 && this.powerCellMechanism.isFlywheelSpunUp())
        {
            spunUpMode = LightMode.On;
        }

        this.controlLight(this.spunUpIndicator, spunUpMode);
    }

    @Override
    public void stop()
    {
        this.spunUpIndicator.set(false);
    }

    private void controlLight(IDigitalOutput indicatorLight, LightMode mode)
    {
        if (mode == LightMode.On)
        {
            indicatorLight.set(true);
        }
        else if (mode == LightMode.Off)
        {
            indicatorLight.set(false);
        }
        else
        {
            double currentTime = this.timer.get();
            if (currentTime % IndicatorLightManager.FlashingFrequency >= IndicatorLightManager.FlashingComparisonFrequency)
            {
                indicatorLight.set(true);
            }
            else
            {
                indicatorLight.set(false);
            }
        }
    }
}
