package frc.robot.common.robotprovider;

public interface IColorSensor
{
    RawColorRGBIR getRawColor();
    int getProximity();
}
