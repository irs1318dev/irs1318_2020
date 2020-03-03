package frc.robot.common.robotprovider;

public abstract class StringLogger implements ILogger
{
    /**
     * Write a string to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    public abstract void logString(String component, String key, String value);

    /**
     * Write a boolean to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBoolean(String component, String key, boolean value)
    {
        this.logString(component, key, String.valueOf(value));
    }

    /**
     * Write a boolean array to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBooleanArray(String component, String key, boolean[] value)
    {
        String str = "";
        if (value != null)
        {
            for (int i = 0; i < value.length; i++)
            {
                if (i > 0)
                {
                    str += ",";
                }

                str += String.valueOf(value[i]);
            }
        }

        this.logString(component, key, str);
    }

    /**
     * Write a number (double) to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(String component, String key, double value)
    {
        this.logString(component, key, String.valueOf(value));
    }

    /**
     * Write a number (double) to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(String component, String key, Double value)
    {
        this.logString(component, key, String.valueOf(value));
    }

    /**
     * Write a number (integer) to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logInteger(String component, String key, int value)
    {
        this.logString(component, key, String.valueOf(value));
    }

    /**
     * Write a number (integer) to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     * @param formatString to use
     */
    @Override
    public void logInteger(String component, String key, int value, String formatString)
    {
        this.logString(component, key, String.format(formatString, value));
    }

    /**
     * Write a point (x,y or N/A) to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logPoint(String component, String key, IPoint value)
    {
        String valueString = "N/A";
        if (value != null)
        {
            valueString = String.format("(%f, %f)", value.getX(), value.getY());
        }

        this.logString(component, key, valueString);
    }

    /**
     * Flush the output stream, if appropriate..
     */
    @Override
    public void flush()
    {
    }
}
