package frc.robot.driver.controltasks;

import frc.robot.HardwareConstants;
import frc.robot.TuningConstants;
import frc.robot.common.PIDHandler;
import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.OffboardVisionManager;
import frc.robot.mechanisms.PowerCellMechanism;

/**
 * Task that turns the turret a certain amount clockwise or counterclockwise based on vision center
 */
public class TurretVisionCenteringTask extends ControlTaskBase implements IControlTask
{
    private static final int NO_CENTER_THRESHOLD = 40;

    private final boolean useTime;
    private final boolean trackingMode;

    private OffboardVisionManager visionManager;
    private PowerCellMechanism powerCell;
    private ITimer timer;

    private PIDHandler turnPidHandler;
    private Double centeredTime;

    private int noCenterCount;

    /**
    * Initializes a new TurretVisionCenteringTask
    */
    public TurretVisionCenteringTask()
    {
        this(false, false);
    }

    /**
    * Initializes a new TurretVisionCenteringTask
    * @param useTime whether to make sure we are centered for a second or not
    * @param trackingMode whether to run forever, as long as it sees a vision target
    */
    public TurretVisionCenteringTask(boolean useTime, boolean trackingMode)
    {
        if (useTime && trackingMode)
        {
            throw new RuntimeException("Can't use time and run forever");
        }

        this.useTime = useTime;
        this.trackingMode = trackingMode;

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
        this.powerCell = this.getInjector().getInstance(PowerCellMechanism.class);
        this.turnPidHandler = this.createTurnHandler();

        if (this.useTime)
        {
            this.timer = this.getInjector().getInstance(ITimer.class);
        }

        this.setDigitalOperationState(DigitalOperation.VisionEnable, true);
    }

    /**
     * Run an iteration of the current task and apply any control changes
     */
    @Override
    public void update()
    {
        double turretPosition = TuningConstants.POWERCELL_TURRET_USE_PID ? this.powerCell.getTurretPosition() : 0.0;

        Double currentMeasuredAngle = this.visionManager.getHorizontalAngle();
        if (currentMeasuredAngle != null)
        {
            this.setAnalogOperationState(
                AnalogOperation.PowerCellTurretPosition,
                turretPosition + this.turnPidHandler.calculatePosition(0.0, currentMeasuredAngle));
        }
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        this.setAnalogOperationState(AnalogOperation.PowerCellTurretPosition, HardwareConstants.POWERCELL_TURRET_MAGIC_DONT_MOVE_VALUE);
        this.setDigitalOperationState(DigitalOperation.VisionEnable, false);
    }

    /**
     * Checks whether this task has completed, or whether it should continue being processed
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    @Override
    public boolean hasCompleted()
    {
        if (this.trackingMode)
        {
            return false;
        }

        Double currentMeasuredAngle = this.visionManager.getHorizontalAngle();
        if (currentMeasuredAngle == null)
        {
            return false;
        }

        double centerAngleDifference = Math.abs(currentMeasuredAngle);
        if (centerAngleDifference > TuningConstants.MAX_VISION_TURRET_CENTERING_RANGE_DEGREES)
        {
            return false;
        }

        if (!this.useTime)
        {
            return true;
        }

        // otherwise, use time:
        if (this.centeredTime == null)
        {
            this.centeredTime = this.timer.get();
            return false;
        }
        else if (this.timer.get() - this.centeredTime < TuningConstants.POWERCELL_TURRET_VISION_CENTERING_TIMEOUT)
        {
            return false;
        }
        else
        {
            return true;
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

        return this.noCenterCount >= TurretVisionCenteringTask.NO_CENTER_THRESHOLD;
    }

    private PIDHandler createTurnHandler()
    {
        if (TuningConstants.POWERCELL_TURRET_USE_PID)
        {
            return new PIDHandler(
                TuningConstants.TURRET_VISION_POSITIONAL_CENTERING_PID_KP,
                TuningConstants.TURRET_VISION_POSITIONAL_CENTERING_PID_KI,
                TuningConstants.TURRET_VISION_POSITIONAL_CENTERING_PID_KD,
                TuningConstants.TURRET_VISION_POSITIONAL_CENTERING_PID_KF,
                TuningConstants.TURRET_VISION_POSITIONAL_CENTERING_PID_KS,
                TuningConstants.TURRET_VISION_POSITIONAL_CENTERING_PID_MIN,
                TuningConstants.TURRET_VISION_POSITIONAL_CENTERING_PID_MAX,
                this.getInjector().getInstance(ITimer.class));
        }

        return new PIDHandler(
            TuningConstants.TURRET_VISION_PERCENTAGE_CENTERING_PID_KP,
            TuningConstants.TURRET_VISION_PERCENTAGE_CENTERING_PID_KI,
            TuningConstants.TURRET_VISION_PERCENTAGE_CENTERING_PID_KD,
            TuningConstants.TURRET_VISION_PERCENTAGE_CENTERING_PID_KF,
            TuningConstants.TURRET_VISION_PERCENTAGE_CENTERING_PID_KS,
            TuningConstants.TURRET_VISION_PERCENTAGE_CENTERING_PID_MIN,
            TuningConstants.TURRET_VISION_PERCENTAGE_CENTERING_PID_MAX,
            this.getInjector().getInstance(ITimer.class));
}
}
