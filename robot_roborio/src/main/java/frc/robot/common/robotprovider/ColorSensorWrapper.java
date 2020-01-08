package frc.robot.common.robotprovider;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorSensorV3.RawColor;

import edu.wpi.first.wpilibj.I2C.Port;

public class ColorSensorWrapper implements IColorSensor
{
    private final ColorSensorV3 wrappedObject;

    public ColorSensorWrapper()
    {
        this.wrappedObject = new ColorSensorV3(Port.kOnboard);
    }

    @Override
    public int getProximity()
    {
        return this.wrappedObject.getProximity();
    }

    @Override
    public RawColorRGBIR getRawColor()
    {
        RawColor rawColor = this.wrappedObject.getRawColor();
        return new RawColorRGBIR(rawColor.red, rawColor.green, rawColor.blue, rawColor.ir);
    }
}
