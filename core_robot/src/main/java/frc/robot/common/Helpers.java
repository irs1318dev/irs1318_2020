package frc.robot.common;

public class Helpers
{
    // Conversion constants...
    public static final double DEGREES_TO_RADIANS = (Math.PI / 180.0f);
    public static final double RADIANS_TO_DEGREES = (180.0f / Math.PI);

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

    /**
     * Returns the trigonometric tangent of an angle.
     * @param angle in degrees.
     * @return the tangent of the argument (opposite length over adjacent length).
     */
    public static double tand(double angle)
    {
        return Math.tan(angle * Helpers.DEGREES_TO_RADIANS);
    }

    /**
     * Returns the trigonometric arctangent.
     * @param ratio of the opposite side over the adjacent side
     * @return the arctangent angle in degrees between -90 and 90
     */
    public static double atand(double ratio)
    {
        return Math.atan(ratio) * Helpers.RADIANS_TO_DEGREES;
    }

    /**
     * Returns the trigonometric sine of an angle.
     * @param angle in degrees.
     * @return the sine of the argument (opposite length over hypotenuse length).
     */
    public static double sind(double angle)
    {
        return Math.sin(angle * Helpers.DEGREES_TO_RADIANS);
    }

    /**
     * Returns the trigonometric arcsine.
     * @param ratio of the opposite side over the hypotenuse side
     * @return the arcsine angle in degrees between -90 and 90
     */
    public static double asind(double ratio)
    {
        return Math.asin(ratio) * Helpers.RADIANS_TO_DEGREES;
    }

    /**
     * Returns the trigonometric cosine of an angle.
     * @param angle in degrees.
     * @return the cosine of the argument (adjacent length over hypotenuse length).
     */
    public static double cosd(double angle)
    {
        return Math.cos(angle * Helpers.DEGREES_TO_RADIANS);
    }

    /**
     * Returns the trigonometric arccosine.
     * @param ratio of the adjacent side over the hypotenuse side
     * @return the arccosine angle in degrees between -90 and 90
     */
    public static double acosd(double ratio)
    {
        return Math.acos(ratio) * Helpers.RADIANS_TO_DEGREES;
    }
}
