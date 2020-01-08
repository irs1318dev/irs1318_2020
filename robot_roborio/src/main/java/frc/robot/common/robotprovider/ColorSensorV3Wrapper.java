package frc.robot.common.robotprovider;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorSensorV3.RawColor;

import edu.wpi.first.wpilibj.I2C.Port;

public class ColorSensorV3Wrapper implements IColorSensorV3
{
    private final ColorSensorV3 wrappedObject;

    public ColorSensorV3Wrapper()
    {
        this.wrappedObject = new ColorSensorV3(Port.kOnboard);
    }

    /**
     * Get the raw proximity value from the sensor ADC (11 bit). This value 
     * is largest when an object is close to the sensor and smallest when 
     * far away.
     * 
     * @return  Proximity measurement value, ranging from 0 to 2047
     */
    @Override
    public int getProximity()
    {
        return this.wrappedObject.getProximity();
    }

    /**
     * Get the raw color values from their respective ADCs (20-bit).
     * 
     * @return  ColorValues struct containing red, green, blue and IR values
     */
    @Override
    public RawColorRGBIR getRawColor()
    {
        RawColor rawColor = this.wrappedObject.getRawColor();
        return new RawColorRGBIR(rawColor.red, rawColor.green, rawColor.blue, rawColor.ir);
    }
}
