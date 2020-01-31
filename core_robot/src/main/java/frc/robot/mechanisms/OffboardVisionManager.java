package frc.robot.mechanisms;

import frc.robot.TuningConstants;
import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.*;
import frc.robot.vision.VisionConstants;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Offboard Vision manager.
 * 
 * @author Will
 *
 */
@Singleton
public class OffboardVisionManager implements IMechanism
{
    private static final String logName = "rpi";

    private final INetworkTableProvider networkTable;
    private final IDashboardLogger logger;

    private Driver driver;

    private double centerX;
    private double centerY;

    private Double distance;
    private Double horizontalAngle;

    /**
     * Initializes a new OffboardVisionManager
     * @param provider for obtaining electronics objects
     * @param logger for logging to smart dashboard
     */
    @Inject
    public OffboardVisionManager(IRobotProvider provider, IDashboardLogger logger)
    {
        this.networkTable = provider.getNetworkTableProvider();
        this.logger = logger;

        this.centerX = 0.0;
        this.centerY = 0.0;
    }

    public double getCenterX()
    {
        return this.centerX;
    }

    public double getCenterY()
    {
        return this.centerY;
    }

    /**
     * read all of the sensors for the mechanism that we will use in macros/autonomous mode and record their values
     */
    @Override
    public void readSensors()
    {
        this.centerX = this.networkTable.getSmartDashboardNumber("v.x");
        this.centerY = this.networkTable.getSmartDashboardNumber("v.y");

        this.logger.logNumber(OffboardVisionManager.logName, "x_read", this.centerX);
        this.logger.logNumber(OffboardVisionManager.logName, "y_read", this.centerY);

        // return if we couldn't find a vision target
        if (this.centerX < 0.0 || this.centerY < 0)
        {
            this.distance = null;
            this.horizontalAngle = null;

            return;
        }

        double yOffset = VisionConstants.LIFECAM_CAMERA_CENTER_WIDTH - this.centerY;
        double verticalAngle = Helpers.atand(yOffset / VisionConstants.LIFECAM_CAMERA_FOCAL_LENGTH_Y);

        this.distance = (TuningConstants.CAMERA_TO_TARGET_Z_OFFSET / Helpers.tand(verticalAngle + TuningConstants.CAMERA_PITCH)) - TuningConstants.CAMERA_X_OFFSET;

        double xOffset = this.centerX - VisionConstants.LIFECAM_CAMERA_CENTER_WIDTH;
        this.horizontalAngle = Helpers.atand(xOffset / VisionConstants.LIFECAM_CAMERA_FOCAL_LENGTH_X) + TuningConstants.CAMERA_YAW;
    }

    @Override
    public void update()
    {
        boolean enableVideoStream = this.driver.getDigital(DigitalOperation.VisionEnableOffboardStream);
        boolean enableVideoProcessing = this.driver.getDigital(DigitalOperation.VisionEnableOffboardProcessing);
        this.logger.logBoolean(OffboardVisionManager.logName, "enableStream", enableVideoStream);
        this.logger.logBoolean(OffboardVisionManager.logName, "enableProcessing", enableVideoProcessing);
    }

    @Override
    public void stop()
    {
        this.logger.logBoolean(OffboardVisionManager.logName, "enableStream", false);
        this.logger.logBoolean(OffboardVisionManager.logName, "enableProcessing", false);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public Double getHorizontalAngle()
    {
        return this.horizontalAngle;
    }

    public Double getDistance()
    {
        return this.distance;
    }
}
