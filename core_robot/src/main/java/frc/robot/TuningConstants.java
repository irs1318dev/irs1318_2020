package frc.robot;

import java.util.*;

import com.google.inject.Injector;

import frc.robot.common.*;
import frc.robot.mechanisms.*;

/**
 * All constants related to tuning the operation of the robot.
 * 
 * @author Will
 * 
 */
public class TuningConstants
{
    public static final boolean COMPETITION_ROBOT = false;
    public static boolean THROW_EXCEPTIONS = !TuningConstants.COMPETITION_ROBOT;

    public static final boolean LOG_TO_FILE = TuningConstants.COMPETITION_ROBOT;
    public static final boolean LOG_ONLY_COMPETITION_MATCHES = true;
    public static final double MAGIC_NULL_VALUE = -1318.0;

    public static List<IMechanism> GetActiveMechanisms(Injector injector)
    {
        List<IMechanism> mechanismList = new ArrayList<IMechanism>();
        mechanismList.add(injector.getInstance(DriveTrainMechanism.class));
        mechanismList.add(injector.getInstance(ControlPanelMechanism.class));
        mechanismList.add(injector.getInstance(PowerCellMechanism.class));
        mechanismList.add(injector.getInstance(ClimberMechanism.class));
        mechanismList.add(injector.getInstance(PowerManager.class));
        mechanismList.add(injector.getInstance(PositionManager.class));
        mechanismList.add(injector.getInstance(CompressorMechanism.class));
        mechanismList.add(injector.getInstance(OffboardVisionManager.class));
        // mechanismList.add(injector.getInstance(IndicatorLightManager.class));
        return mechanismList;
    }

    //================================================== Autonomous ==============================================================

    public static final boolean CANCEL_AUTONOMOUS_ROUTINE_ON_DISABLE = true;

    public static final double DRIVETRAIN_POSITIONAL_ACCEPTABLE_DELTA = 1.0;

    // Navx Turn Constants
    public static final double MAX_NAVX_TURN_RANGE_DEGREES = 5.0;
    public static final double MAX_NAVX_FAST_TURN_RANGE_DEGREES = 5.0;
    public static final double NAVX_FAST_TURN_TIMEOUT = 1.25;
    public static final double NAVX_TURN_COMPLETE_TIME = 0.4;
    public static final double NAVX_TURN_COMPLETE_CURRENT_VELOCITY_DELTA = 0;
    public static final double NAVX_TURN_COMPLETE_DESIRED_VELOCITY_DELTA = 0;

    // Navx Turn PID Constants
    public static final double NAVX_TURN_PID_KP = 0.025; // 0.04
    public static final double NAVX_TURN_PID_KI = 0.0;
    public static final double NAVX_TURN_PID_KD = 0.02;
    public static final double NAVX_TURN_PID_KF = 0.0;
    public static final double NAVX_TURN_PID_KS = 1.0;
    public static final double NAVX_TURN_PID_MIN = -0.8;
    public static final double NAVX_TURN_PID_MAX = 0.8;
    public static final double NAVX_FAST_TURN_PID_KP = 0.01;
    public static final double NAVX_FAST_TURN_PID_KI = 0.0;
    public static final double NAVX_FAST_TURN_PID_KD = 0.0;
    public static final double NAVX_FAST_TURN_PID_KF = 0.0;
    public static final double NAVX_FAST_TURN_PID_KS = 1.0;
    public static final double NAVX_FAST_TURN_PID_MIN = -0.8;
    public static final double NAVX_FAST_TURN_PID_MAX = 0.8;

    // Settings for Color Matching
    public static final double COLOR_MATCH_BLUE_TARGET_RED_PERCENTAGE = 0.143;
    public static final double COLOR_MATCH_BLUE_TARGET_GREEN_PERCENTAGE = 0.427;
    public static final double COLOR_MATCH_BLUE_TARGET_BLUE_PERCENTAGE = 0.429;
    public static final double COLOR_MATCH_GREEN_TARGET_RED_PERCENTAGE = 0.197;
    public static final double COLOR_MATCH_GREEN_TARGET_GREEN_PERCENTAGE = 0.561;
    public static final double COLOR_MATCH_GREEN_TARGET_BLUE_PERCENTAGE = 0.240;
    public static final double COLOR_MATCH_RED_TARGET_RED_PERCENTAGE = 0.531; //0.561;
    public static final double COLOR_MATCH_RED_TARGET_GREEN_PERCENTAGE = 0.341; //0.232;
    public static final double COLOR_MATCH_RED_TARGET_BLUE_PERCENTAGE = 0.128; //0.114;
    public static final double COLOR_MATCH_YELLOW_TARGET_RED_PERCENTAGE = 0.321; //0.361;
    public static final double COLOR_MATCH_YELLOW_TARGET_GREEN_PERCENTAGE = 0.554; //0.524;
    public static final double COLOR_MATCH_YELLOW_TARGET_BLUE_PERCENTAGE = 0.124; //0.113;

    // Kinodynamic constraints for driving with roadrunner
    public static final double ROADRUNNER_TIME_STEP = 0.01;
    public static final double ROADRUNNER_MAX_VELOCITY = 100.0;
    public static final double ROADRUNNER_MAX_ACCELERATION = 200.0;
    public static final double ROADRUNNER_MAX_JERK = 2000.0;
    public static final double ROADRUNNER_MAX_ANGULAR_VELOCITY = 5.0;
    public static final double ROADRUNNER_MAX_ANGULAR_ACCELERATION = 10.0;
    public static final double ROADRUNNER_MAX_ANGULAR_JERK = 20.0;

    public static final double STHOPE_BLEASE = 0.0;
    public static final double CONTROL_PANEL_POSITION_SPIN_SPEED = .5;
    public static final double CONTROL_PANEL_ROTATION_SPIN_SPEED = 1;

    //================================================= Vision ======================================================

    // Acceptable vision centering range values in degrees
    public static final double MAX_VISION_CENTERING_RANGE_DEGREES = 5.0;
    public static final double MAX_VISION_TURRET_CENTERING_RANGE_DEGREES = 1.0;

    // Acceptable vision distance from tape in inches
    public static final double MAX_VISION_ACCEPTABLE_FORWARD_DISTANCE = 3.25;

    // PID settings for Centering the robot on a vision target from one stationary place
    public static final double VISION_STATIONARY_CENTERING_PID_KP = 0.02;
    public static final double VISION_STATIONARY_CENTERING_PID_KI = 0.0;
    public static final double VISION_STATIONARY_CENTERING_PID_KD = 0.02;
    public static final double VISION_STATIONARY_CENTERING_PID_KF = 0.0;
    public static final double VISION_STATIONARY_CENTERING_PID_KS = 1.0;
    public static final double VISION_STATIONARY_CENTERING_PID_MIN = -0.3;
    public static final double VISION_STATIONARY_CENTERING_PID_MAX = 0.3;

    // PID settings for Centering the robot on a vision target
    public static final double VISION_MOVING_CENTERING_PID_KP = 0.02;
    public static final double VISION_MOVING_CENTERING_PID_KI = 0.0;
    public static final double VISION_MOVING_CENTERING_PID_KD = 0.03;
    public static final double VISION_MOVING_CENTERING_PID_KF = 0.0;
    public static final double VISION_MOVING_CENTERING_PID_KS = 1.0;
    public static final double VISION_MOVING_CENTERING_PID_MIN = -0.3;
    public static final double VISION_MOVING_CENTERING_PID_MAX = 0.3;

    // PID settings for Advancing the robot towards a vision target
    public static final double VISION_ADVANCING_PID_KP = 0.01;
    public static final double VISION_ADVANCING_PID_KI = 0.0;
    public static final double VISION_ADVANCING_PID_KD = 0.0;
    public static final double VISION_ADVANCING_PID_KF = 0.0;
    public static final double VISION_ADVANCING_PID_KS = 1.0;
    public static final double VISION_ADVANCING_PID_MIN = -0.3;
    public static final double VISION_ADVANCING_PID_MAX = 0.3;

    // PID settings for Advancing the robot quickly towards a vision target
    public static final double VISION_FAST_ADVANCING_PID_KP = 0.01;
    public static final double VISION_FAST_ADVANCING_PID_KI = 0.0;
    public static final double VISION_FAST_ADVANCING_PID_KD = 0.0;
    public static final double VISION_FAST_ADVANCING_PID_KF = 0.0;
    public static final double VISION_FAST_ADVANCING_PID_KS = 1.0;
    public static final double VISION_FAST_ADVANCING_PID_MIN = -0.45;
    public static final double VISION_FAST_ADVANCING_PID_MAX = 0.45;

    // PID settings for Centering the turret on a vision target when the turret is controlled using Positional PID
    public static final double TURRET_VISION_POSITIONAL_CENTERING_PID_KP = 0.35;
    public static final double TURRET_VISION_POSITIONAL_CENTERING_PID_KI = 0.0;
    public static final double TURRET_VISION_POSITIONAL_CENTERING_PID_KD = 0.35;
    public static final double TURRET_VISION_POSITIONAL_CENTERING_PID_KF = 0.0;
    public static final double TURRET_VISION_POSITIONAL_CENTERING_PID_KS = 30.0;
    public static final double TURRET_VISION_POSITIONAL_CENTERING_PID_MIN = -30.0;
    public static final double TURRET_VISION_POSITIONAL_CENTERING_PID_MAX = 30.0;

    // PID settings for Centering the turret on a vision target when the turret is controlled using open loop
    public static final double TURRET_VISION_PERCENTAGE_CENTERING_PID_KP = 0.35;
    public static final double TURRET_VISION_PERCENTAGE_CENTERING_PID_KI = 0.0;
    public static final double TURRET_VISION_PERCENTAGE_CENTERING_PID_KD = 0.35;
    public static final double TURRET_VISION_PERCENTAGE_CENTERING_PID_KF = 0.0;
    public static final double TURRET_VISION_PERCENTAGE_CENTERING_PID_KS = 30.0;
    public static final double TURRET_VISION_PERCENTAGE_CENTERING_PID_MIN = -1.0;
    public static final double TURRET_VISION_PERCENTAGE_CENTERING_PID_MAX = 1.0;

    public static final boolean VISION_ENABLE_DURING_TELEOP = true;

    //================================================== Indicator Lights ========================================================

    public static final double INDICATOR_LIGHT_VISION_ACCEPTABLE_ANGLE_RANGE = 3.0;

    //================================================== PowerCell ===============================================================

    public static final boolean POWERCELL_HAS_THROUGH_BEAM_SENSOR = false;

    public static final double POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KP = 0.55;
    public static final double POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KI = 0.0;
    public static final double POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KD = 0.75;
    public static final double POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KF = 0.0113666; // 90000 / 1023
    public static final double POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KS = 85000.0;

    public static final double POWERCELL_TURRET_POSITION_PID_KP = 4.0;
    public static final double POWERCELL_TURRET_POSITION_PID_KI = 0.0;
    public static final double POWERCELL_TURRET_POSITION_PID_KD = 0.0;
    public static final double POWERCELL_TURRET_POSITION_PID_KF = 0.0;

    public static final boolean POWERCELL_TURRET_FORWARD_LIMIT_SWITCH_ENABLED = false;
    public static final boolean POWERCELL_TURRET_FORWARD_LIMIT_SWITCH_NORMALLY_OPEN = false;
    public static final boolean POWERCELL_TURRET_REVERSE_LIMIT_SWITCH_ENABLED = false;
    public static final boolean POWERCELL_TURRET_REVERSE_LIMIT_SWITCH_NORMALLY_OPEN = false;

    public static final boolean POWERCELL_TURRET_USE_PID = true;
    public static final double POWERCELL_TURRET_VISION_CENTERING_TIMEOUT = 0.75;
    public static final double POWERCELL_TURRET_MAGIC_DONT_MOVE_VALUE = TuningConstants.POWERCELL_TURRET_USE_PID ? TuningConstants.MAGIC_NULL_VALUE : 0.0;

    public static final int POWERCELL_FLYWHEEL_VELOCITY_PERIOD = 10;
    public static final int POWERCELL_FLYWHEEL_VELOCITY_WINDOWSIZE = 32;
    public static final boolean POWERCELL_FLYWHEEL_MASTER_VELOCITY_VOLTAGE_COMPENSATION_ENABLED = true;
    public static final boolean POWERCELL_FLYWHEEL_FOLLOWER_VELOCITY_VOLTAGE_COMPENSATION_ENABLED = true;
    public static final double POWERCELL_FLYWHEEL_MASTER_VELOCITY_VOLTAGE_COMPENSATION_MAXVOLTAGE = 12.0;
    public static final double POWERCELL_FLYWHEEL_FOLLOWER_VELOCITY_VOLTAGE_COMPENSATION_MAXVOLTAGE = 12.0;

    public static final double POWERCELL_INNER_ROLLER_MOTOR_INTAKE_POWER = 0.3;
    public static final double POWERCELL_OUTER_ROLLER_MOTOR_INTAKE_POWER = 0.7;

    public static final double POWERCELL_INNER_ROLLER_MOTOR_OUTTAKE_POWER = -0.3;
    public static final double POWERCELL_OUTER_ROLLER_MOTOR_OUTTAKE_POWER = -0.3;

    public static final double POWERCELL_TROUGHBEAM_CUTOFF = 2.7;

    public static final double POWERCELL_GENEVA_MECHANISM_MOTOR_POWER_INDEXING = 0.30;
    public static final double POWERCELL_GENEVA_MECHANISM_MOTOR_POWER_SHOOTING = 0.30;

    public static final double POWERCELL_GENEVA_MECHANISM_INDEXING_TIMEOUT = 2.0;
    public static final double POWERCELL_GENEVA_COUNT_THRESHOLD = 0.15;

    public static final double POWERCELL_FLYWHEEL_POINT_BLANK_MOTOR_VELOCITY = 0.4;
    public static final double POWERCELL_FLYWHEEL_INITIATIONLINE_MOTOR_VELOCITY = 0.5;
    public static final double POWERCELL_FLYWHEEL_MEDIUM_MOTOR_VELOCITY = 0.8;

    public static final double POWERCELL_LEFT_TURN_TURRET = -5.0;
    public static final double POWERCELL_MIN_TURRET_OFFSET= 1.0;

    //================================================== DriveTrain ==============================================================

    // Drivetrain PID keys/default values:
    public static final boolean DRIVETRAIN_USE_PID = true;
    public static final boolean DRIVETRAIN_USE_CROSS_COUPLING = false;
    public static final boolean DRIVETRAIN_USE_HEADING_CORRECTION = true;

    // Velocity PID (right)
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KP = 0.09;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KI = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KD = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KF = 0.0478; // .0478 ==> ~ 1023 / 21400 (100% control authority)
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KS = 17000.0; // 21400 was highest speed at full throttle FF on blocks

    // Velocity PID (left)
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KP = 0.09;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KI = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KD = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KF = 0.0478; // .0478 ==> ~ 1023 / 21400 (100% control authority)
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KS = 17000.0; // 21400 was highest speed at full throttle FF on blocks

    // Path PID (right)
    public static final double DRIVETRAIN_PATH_PID_RIGHT_KP = 0.0;
    public static final double DRIVETRAIN_PATH_PID_RIGHT_KI = 0.0;
    public static final double DRIVETRAIN_PATH_PID_RIGHT_KD = 0.0;
    public static final double DRIVETRAIN_PATH_PID_RIGHT_KF = 0.0;
    public static final double DRIVETRAIN_PATH_PID_RIGHT_KV = 1.0;
    public static final double DRIVETRAIN_PATH_PID_RIGHT_KCC = 0.0;
    public static final double DRIVETRAIN_PATH_RIGHT_HEADING_CORRECTION = 0.0;

    // gets the max speed in inches per second
    // (TalonSRX: 10 * (ticks per 100ms) * (inches per tick) * (10) == in / s)
    // (SparkMAX: (rotations per second) * (inches per rotation) == in / s)
    public static final double DRIVETRAIN_PATH_RIGHT_MAX_VELOCITY_INCHES_PER_SECOND = 10.0 * TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KS * HardwareConstants.DRIVETRAIN_RIGHT_PULSE_DISTANCE;
    // public static final double DRIVETRAIN_PATH_RIGHT_MAX_VELOCITY_INCHES_PER_SECOND = TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KS * HardwareConstants.DRIVETRAIN_LEFT_PULSE_DISTANCE;

    // Path PID (left)
    public static final double DRIVETRAIN_PATH_PID_LEFT_KP = 0.0;
    public static final double DRIVETRAIN_PATH_PID_LEFT_KI = 0.0;
    public static final double DRIVETRAIN_PATH_PID_LEFT_KD = 0.0;
    public static final double DRIVETRAIN_PATH_PID_LEFT_KF = 0.0;
    public static final double DRIVETRAIN_PATH_PID_LEFT_KV = 1.0;
    public static final double DRIVETRAIN_PATH_PID_LEFT_KCC = 0.0;
    public static final double DRIVETRAIN_PATH_LEFT_HEADING_CORRECTION = 0.0;

    // gets the max control speed in inches per second
    // (TalonSRX: 10 * (ticks per 100ms) * (inches per tick) * (10) == in / s)
    public static final double DRIVETRAIN_PATH_LEFT_MAX_VELOCITY_INCHES_PER_SECOND = 10.0 * TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KS * HardwareConstants.DRIVETRAIN_LEFT_PULSE_DISTANCE;
    // public static final double DRIVETRAIN_PATH_LEFT_MAX_VELOCITY_INCHES_PER_SECOND = TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KS * HardwareConstants.DRIVETRAIN_LEFT_PULSE_DISTANCE;

    // Position PID (right)
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KP = 0.0002;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KI = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KD = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KF = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KCC = 0.0001;

    // Position PID (left)
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KP = 0.0002;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KI = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KD = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KF = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KCC = 0.0001;

    // Brake PID (right)
    public static final double DRIVETRAIN_BRAKE_PID_RIGHT_KP = 0.0004;
    public static final double DRIVETRAIN_BRAKE_PID_RIGHT_KI = 0.0;
    public static final double DRIVETRAIN_BRAKE_PID_RIGHT_KD = 0.0;
    public static final double DRIVETRAIN_BRAKE_PID_RIGHT_KF = 0.0;

    // Brake PID (left)
    public static final double DRIVETRAIN_BRAKE_PID_LEFT_KP = 0.0004;
    public static final double DRIVETRAIN_BRAKE_PID_LEFT_KI = 0.0;
    public static final double DRIVETRAIN_BRAKE_PID_LEFT_KD = 0.0;
    public static final double DRIVETRAIN_BRAKE_PID_LEFT_KF = 0.0;

    // Drivetrain choices for one-stick drive
    public static final double DRIVETRAIN_K1 = 1.4;
    public static final double DRIVETRAIN_K2 = 0.5;

    // Drivetrain deadzone/max power levels
    public static final boolean DRIVETRAIN_VOLTAGE_COMPENSATION_ENABLED = true;
    public static final double DRIVETRAIN_VOLTAGE_COMPENSATION = 12.0;
    public static final boolean DRIVETRAIN_SUPPLY_CURRENT_LIMITING_ENABLED = true;
    public static final double DRIVETRAIN_SUPPLY_CURRENT_MAX = 40.0;
    public static final double DRIVETRAIN_SUPPLY_TRIGGER_CURRENT = 100.0;
    public static final double DRIVETRAIN_SUPPLY_TRIGGER_DURATION = 0.25;
    public static final double DRIVETRAIN_X_DEAD_ZONE = .05;
    public static final double DRIVETRAIN_Y_DEAD_ZONE = .05;
    public static final double DRIVETRAIN_MAX_POWER_LEVEL = 1.0;// max power level (velocity)
    public static final double DRIVETRAIN_LEFT_POSITIONAL_NON_PID_MULTIPLICAND = HardwareConstants.DRIVETRAIN_LEFT_PULSE_DISTANCE / 60.0;
    public static final double DRIVETRAIN_RIGHT_POSITIONAL_NON_PID_MULTIPLICAND = HardwareConstants.DRIVETRAIN_RIGHT_PULSE_DISTANCE / 60.0;
    public static final double DRIVETRAIN_MAX_POWER_POSITIONAL_NON_PID = 0.2;// max power level (positional, non-PID)

    public static final double DRIVETRAIN_CROSS_COUPLING_ZERO_ERROR_RANGE = 100.0; // (in ticks)
    public static final double DRIVETRAIN_PATH_MAX_POWER_LEVEL = 1.0;
    public static final double DRIVETRAIN_POSITIONAL_MAX_POWER_LEVEL = 0.90; // 0.85
    public static final double DRIVETRAIN_BRAKE_MAX_POWER_LEVEL = 0.6;
    public static final double DRIVETRAIN_VELOCITY_MAX_POWER_LEVEL = 1.0;

    public static final boolean DRIVETRAIN_REGULAR_MODE_SQUARING = false;
    public static final boolean DRIVETRAIN_SIMPLE_MODE_SQUARING = false;

    public static final double DRIVETRAIN_ENCODER_ODOMETRY_ANGLE_CORRECTION = 1.0; // account for turning weirdness (any degree offset in the angle)
}
