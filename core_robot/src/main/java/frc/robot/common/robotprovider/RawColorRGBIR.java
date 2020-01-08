package frc.robot.common.robotprovider;

public class RawColorRGBIR
{
    private final int red;
    private final int green;
    private final int blue;
    private final int ir;

    public RawColorRGBIR(int red, int green, int blue, int ir)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.ir = ir;
    }

    public int getRed()
    {
        return this.red;
    }

    public int getGreen()
    {
        return this.green;
    }

    public int getBlue()
    {
        return this.blue;
    }

    public int getIR()
    {
        return this.ir;
    }
}
