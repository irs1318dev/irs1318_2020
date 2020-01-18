package frc.robot.common;

public class Helpers
{
    // Conversion constants...
    public static final double ANGLE_TO_RADIANS = (Math.PI / 180.0f);
    public static final double RADIANS_TO_ANGLE = (180.0f / Math.PI);

    public static double EnforceRange(double value, double minValue, double maxValue)
    {
        if (value > maxValue)
        {
            return maxValue;
        }
        else if (value < minValue)
        {
            return minValue;
        }

        return value;
    }

    public static boolean WithinRange(double value, double minValue, double maxValue)
    {
        return value >= minValue && value <= maxValue;
    }

    public static boolean WithinDelta(double actualValue, double expectedValue, double acceptableDelta)
    {
        return Helpers.WithinRange(actualValue, expectedValue - acceptableDelta, expectedValue + acceptableDelta);
    }
}
