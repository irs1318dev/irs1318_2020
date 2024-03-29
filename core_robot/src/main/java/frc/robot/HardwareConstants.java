package frc.robot;

/**
 * All constants describing the physical structure of the robot (distances and sizes of things).
 * 
 * @author Will
 * 
 */
public class HardwareConstants
{
    //================================================= Vision ======================================================

    // Vision Alignment 
    public static final double CAMERA_PITCH = 22.5; // in degrees
    public static final double CAMERA_X_OFFSET = 0.0; // in inches
    public static final double CAMERA_Z_OFFSET = 21.0; // in inches
    public static final double VISIONTARGET_Z_OFFSET = 90.25; // in inches
    public static final double CAMERA_TO_TARGET_Z_OFFSET = HardwareConstants.VISIONTARGET_Z_OFFSET - HardwareConstants.CAMERA_Z_OFFSET;
    public static final double CAMERA_YAW = 0.0; // in degrees

    //================================================== PowerCell ===============================================================

    public static final boolean POWERCELL_TURRET_INVERT_OUTPUT = true;
    public static final boolean POWERCELL_TURRET_INVERT_SENSOR = false;
    public static final boolean POWERCELL_FLYWHEEL_MASTER_INVERT_OUTPUT = true;
    public static final boolean POWERCELL_FLYWHEEL_MASTER_INVERT_SENSOR = true;
    public static final boolean POWERCELL_FLYWHEEL_FOLLOWER_INVERT_OUTPUT = true;
    public static final boolean POWERCELL_ROLLER_MOTOR_OUTER_INVERT_OUTPUT = false;
    public static final boolean POWERCELL_ROLLER_MOTOR_INNER_INVERT_OUTPUT = false;
    public static final boolean POWERCELL_GENEVA_MOTOR_INVERT_OUTPUT = true;

    public static final double POWERCELL_TURRET_DEGREES_TO_TICKS = 4096.0 / 360.0; // (360 degrees => 4096 ticks)
    public static final double POWERCELL_TURRET_TICKS_TO_DEGREES = 360.0 / 4096.0;
    public static final double POWERCELL_TURRET_MINIMUM_RANGE_VALUE = -275.0;
    public static final double POWERCELL_TURRET_MAXIMUM_RANGE_VALUE = 95.0;

    public static final int POWERCELL_CAROUSEL_SLOT_COUNT = 5;

    //================================================== Climber =================================================================

    public static final boolean CLIMBER_WINCH_MASTER_INVERT_OUTPUT = false;
    public static final boolean CLIMBER_WINCH_FOLLOWER_INVERT_OUTPUT = false;

    //================================================== ControlPanel ============================================================

    public static final boolean CONTROLPANEL_SPINNER_INVERT_OUTPUT = false;

    //================================================== DriveTrain ==============================================================
    // Note: Pulse Distance is the distance moved per tick

    public static final double DRIVETRAIN_LEFT_ENCODER_PULSES_PER_REVOLUTION = 2048.0;
    public static final double DRIVETRAIN_LEFT_GEAR_RATIO = 8.45864662;
    public static final double DRIVETRAIN_LEFT_WHEEL_DIAMETER = 6.0; // (in inches)
    public static final double DRIVETRAIN_LEFT_WHEEL_CIRCUMFERENCE = Math.PI * HardwareConstants.DRIVETRAIN_LEFT_WHEEL_DIAMETER;
    public static final double DRIVETRAIN_LEFT_PULSE_DISTANCE = HardwareConstants.DRIVETRAIN_LEFT_WHEEL_CIRCUMFERENCE / (HardwareConstants.DRIVETRAIN_LEFT_GEAR_RATIO * HardwareConstants.DRIVETRAIN_LEFT_ENCODER_PULSES_PER_REVOLUTION);
    public static final double DRIVETRAIN_LEFT_TICKS_PER_INCH = (HardwareConstants.DRIVETRAIN_LEFT_GEAR_RATIO * HardwareConstants.DRIVETRAIN_LEFT_ENCODER_PULSES_PER_REVOLUTION) / HardwareConstants.DRIVETRAIN_LEFT_WHEEL_CIRCUMFERENCE;

    public static final double DRIVETRAIN_RIGHT_ENCODER_PULSES_PER_REVOLUTION = 2048.0;
    public static final double DRIVETRAIN_RIGHT_GEAR_RATIO = 8.45864662;
    public static final double DRIVETRAIN_RIGHT_WHEEL_DIAMETER = 6.0; // (in inches)
    public static final double DRIVETRAIN_RIGHT_WHEEL_CIRCUMFERENCE = Math.PI * HardwareConstants.DRIVETRAIN_RIGHT_WHEEL_DIAMETER;
    public static final double DRIVETRAIN_RIGHT_PULSE_DISTANCE = HardwareConstants.DRIVETRAIN_RIGHT_WHEEL_CIRCUMFERENCE / (HardwareConstants.DRIVETRAIN_RIGHT_GEAR_RATIO * HardwareConstants.DRIVETRAIN_RIGHT_ENCODER_PULSES_PER_REVOLUTION);
    public static final double DRIVETRAIN_RIGHT_TICKS_PER_INCH = (HardwareConstants.DRIVETRAIN_RIGHT_GEAR_RATIO * HardwareConstants.DRIVETRAIN_RIGHT_ENCODER_PULSES_PER_REVOLUTION) / HardwareConstants.DRIVETRAIN_RIGHT_WHEEL_CIRCUMFERENCE;

    // measure from outside of wheel:
    public static final double DRIVETRAIN_WHEEL_SEPARATION_DISTANCE = 24.75; // (in inches)

    // DriveTrain motor/sensor orientations
    public static final boolean DRIVETRAIN_LEFT_MASTER_INVERT_OUTPUT = false;
    public static final boolean DRIVETRAIN_LEFT_FOLLOWER1_INVERT_OUTPUT = false;
    //public static final boolean DRIVETRAIN_LEFT_FOLLOWER2_INVERT_OUTPUT = false;
    public static final boolean DRIVETRAIN_LEFT_INVERT_SENSOR = true;
    public static final boolean DRIVETRAIN_RIGHT_MASTER_INVERT_OUTPUT = true;
    public static final boolean DRIVETRAIN_RIGHT_FOLLOWER1_INVERT_OUTPUT = true;
    //public static final boolean DRIVETRAIN_RIGHT_FOLLOWER2_INVERT_OUTPUT = true;
    public static final boolean DRIVETRAIN_RIGHT_INVERT_SENSOR = true;
}
