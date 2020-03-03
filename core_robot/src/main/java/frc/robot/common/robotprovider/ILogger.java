package frc.robot.common.robotprovider;

public interface ILogger
{
    /**
     * Write a boolean to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logBoolean(String component, String key, boolean value);

    /**
     * Write a boolean array to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logBooleanArray(String component, String key, boolean[] value);

    /**
     * Write a number (double) to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logNumber(String component, String key, double value);

    /**
     * Write a number (double) to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logNumber(String component, String key, Double value);

    /**
     * Write a number (integer) to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logInteger(String component, String key, int value);

    /**
     * Write a number (integer) to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     * @param formatString to use
     */
    void logInteger(String component, String key, int value, String formatString);

    /**
     * Write a point (x,y or N/A) to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logPoint(String component, String key, IPoint value);

    /**
     * Write a string to the log
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logString(String component, String key, String value);

    /**
     * Flush the output stream, if appropriate..
     */
    void flush();
}
