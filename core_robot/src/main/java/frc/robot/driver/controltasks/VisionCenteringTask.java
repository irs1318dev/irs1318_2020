package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.common.PIDHandler;
import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.OffboardVisionManager;

/**
 * Task that turns the robot a certain amount clockwise or counterclockwise in-place based on vision center
 */
public class VisionCenteringTask extends ControlTaskBase implements IControlTask
{
    private static final int NO_CENTER_THRESHOLD = 40;

    private final boolean useTime;
    private final DigitalOperation toPerform;

    private PIDHandler turnPidHandler;
    private Double centeredTime;
    protected OffboardVisionManager visionManager;

    private int noCenterCount;

    /**
    * Initializes a new VisionCenteringTask
    */
    public VisionCenteringTask(DigitalOperation toPerform)
    {
        this(true, toPerform);
    }

    /**
    * Initializes a new VisionCenteringTask
    * @param useTime whether to make sure we are centered for a second or not
    */
    public VisionCenteringTask(boolean useTime, DigitalOperation toPerform)
    {
        this.useTime = useTime;
        this.toPerform = toPerform;

        this.turnPidHandler = null;
        this.centeredTime = null;

        this.noCenterCount = 0;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        this.visionManager = this.getInjector().getInstance(OffboardVisionManager.class);
        this.turnPidHandler = this.createTurnHandler();
    }

    /**
     * Run an iteration of the current task and apply any control changes
     */
    @Override
    public void update()
    {
        this.setDigitalOperationState(DigitalOperation.DriveTrainUsePositionalMode, false);
        this.setDigitalOperationState(this.toPerform, true);

        Double currentMeasuredAngle = this.visionManager.getHorizontalAngle();
        if (currentMeasuredAngle != null)
        {
            this.setAnalogOperationState(
                AnalogOperation.DriveTrainTurn,
                -this.turnPidHandler.calculatePosition(0.0, currentMeasuredAngle));
        }
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        this.setDigitalOperationState(DigitalOperation.DriveTrainUsePositionalMode, false);
        this.setAnalogOperationState(AnalogOperation.DriveTrainTurn, 0.0);

        this.setDigitalOperationState(this.toPerform, false);
        this.setDigitalOperationState(DigitalOperation.VisionDisable, true);
    }

    /**
     * Checks whether this task has completed, or whether it should continue being processed
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    @Override
    public boolean hasCompleted()
    {
        Double currentMeasuredAngle = this.visionManager.getHorizontalAngle();
        if (currentMeasuredAngle == null)
        {
            return false;
        }

        double centerAngleDifference = Math.abs(currentMeasuredAngle);
        if (centerAngleDifference > TuningConstants.MAX_VISION_CENTERING_RANGE_DEGREES)
        {
            return false;
        }

        if (!this.useTime)
        {
            return true;
        }
        else
        {
            ITimer timer = this.getInjector().getInstance(ITimer.class);
            if (this.centeredTime == null)
            {
                this.centeredTime = timer.get();
                return false;
            }
            else if (timer.get() - this.centeredTime < 0.75)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    /**
     * Checks whether this task should be stopped, or whether it should continue being processed.
     * @return true if we should cancel this task (and stop performing any subsequent tasks), otherwise false (to keep processing this task)
     */
    @Override
    public boolean shouldCancel()
    {
        if (this.visionManager.getDistance() == null)
        {
            this.noCenterCount++;
        }
        else
        {
            this.noCenterCount = 0;
        }

        return this.noCenterCount >= VisionCenteringTask.NO_CENTER_THRESHOLD;
    }

    protected PIDHandler createTurnHandler()
    {
        return new PIDHandler(
            TuningConstants.VISION_STATIONARY_CENTERING_PID_KP,
            TuningConstants.VISION_STATIONARY_CENTERING_PID_KI,
            TuningConstants.VISION_STATIONARY_CENTERING_PID_KD,
            TuningConstants.VISION_STATIONARY_CENTERING_PID_KF,
            TuningConstants.VISION_STATIONARY_CENTERING_PID_KS,
            TuningConstants.VISION_STATIONARY_CENTERING_PID_MIN,
            TuningConstants.VISION_STATIONARY_CENTERING_PID_MAX,
            this.getInjector().getInstance(ITimer.class));
    }
}
