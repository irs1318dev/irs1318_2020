package frc.robot.common.robotprovider;

public class MultiLogger implements ILogger
{
    private final ILogger[] loggers;

    /**
     * Initializes a new instance of the MultiLogger class
     * @param loggers to log to
     */
    public MultiLogger(ILogger... loggers)
    {
        this.loggers = loggers;
    }

    /**
     * Write a boolean to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBoolean(String component, String key, boolean value)
    {
        for (ILogger logger : this.loggers)
        {
            logger.logBoolean(component, key, value);
        }
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
        for (ILogger logger : this.loggers)
        {
            logger.logBooleanArray(component, key, value);
        }
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
        for (ILogger logger : this.loggers)
        {
            logger.logNumber(component, key, value);
        }
    }

    /**
     * Write a number (Double) to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(String component, String key, Double value)
    {
        for (ILogger logger : this.loggers)
        {
            logger.logNumber(component, key, value);
        }
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
        for (ILogger logger : this.loggers)
        {
            logger.logInteger(component, key, value);
        }
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
        for (ILogger logger : this.loggers)
        {
            logger.logInteger(component, key, value, formatString);
        }
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
        for (ILogger logger : this.loggers)
        {
            logger.logPoint(component, key, value);
        }
    }

    /**
     * Write a string to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logString(String component, String key, String value)
    {
        for (ILogger logger : this.loggers)
        {
            logger.logString(component, key, value);
        }
    }

    /**
     * Flush the output stream, if appropriate..
     */
    @Override
    public void flush()
    {
        for (ILogger logger : this.loggers)
        {
            logger.flush();
        }
    }
}
