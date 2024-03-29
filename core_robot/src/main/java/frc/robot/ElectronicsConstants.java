package frc.robot;

/**
 * All constants describing how the electronics are plugged together.
 * 
 * @author Will
 * 
 */
public class ElectronicsConstants
{
    // change INVERT_X_AXIS to true if positive on the joystick isn't to the right, and negative isn't to the left
    public static final boolean INVERT_X_AXIS = false;

    // change INVERT_Y_AXIS to true if positive on the joystick isn't forward, and negative isn't backwards.
    public static final boolean INVERT_Y_AXIS = true;

    // change INVERT_THROTTLE_AXIS to true if positive on the joystick isn't forward, and negative isn't backwards.
    public static final boolean INVERT_THROTTLE_AXIS = true;

    // change INVERT_TRIGGER_AXIS to true if positive on the joystick isn't forward, and negative isn't backwards.
    public static final boolean INVERT_TRIGGER_AXIS = false;

    public static final int PCM_A_MODULE = 0; // Module A
    public static final int PCM_B_MODULE = 1; // Module B

    public static final int JOYSTICK_DRIVER_PORT = 0;
    public static final int JOYSTICK_CO_DRIVER_PORT = 1;

    //================================================== Vision ==============================================================

    public static final int VISION_RING_LIGHT_DIO = 1;

    //================================================== Indicator Lights ==============================================================

    public static final int INDICATOR_LIGHT_SPUN_UP_DIO = -1;

    //================================================== DriveTrain ==============================================================

    public static final int DRIVETRAIN_LEFT_MASTER_CAN_ID = 1;
    public static final int DRIVETRAIN_LEFT_MASTER_PDP_SLOT = 1;
    public static final int DRIVETRAIN_LEFT_FOLLOWER_CAN_ID = 2;
    public static final int DRIVETRAIN_LEFT_FOLLOWER_PDP_SLOT = 0;
    public static final int DRIVETRAIN_RIGHT_MASTER_CAN_ID = 3;
    public static final int DRIVETRAIN_RIGHT_MASTER_PDP_SLOT = 14;
    public static final int DRIVETRAIN_RIGHT_FOLLOWER_CAN_ID = 4;
    public static final int DRIVETRAIN_RIGHT_FOLLOWER_PDP_SLOT = 15;

    //================================================== PowerCell =================================================================

    // shooter
    public static final int POWERCELL_FLYWHEEL_MASTER_CAN_ID = 5;
    public static final int POWERCELL_FLYWHEEL_MASTER_PDP_SLOT = 3; // 3 & 12 ??
    public static final int POWERCELL_FLYWHEEL_FOLLOWER_CAN_ID = 6;
    public static final int POWERCELL_FLYWHEEL_FOLLOWER_PDP_SLOT = 12; // 3 & 12 ??
    public static final int POWERCELL_TURRET_MOTOR_CAN_ID = 7;
    public static final int POWERCELL_TURRET_MOTOR_PDP_SLOT = 9;
    public static final int POWERCELL_OUTER_HOOD_FORWARD_PCM = 2; // Module B 
    public static final int POWERCELL_OUTER_HOOD_REVERSE_PCM = 3; // Module B
    public static final int POWERCELL_INNER_HOOD_FORWARD_PCM = 1; // Module B
    public static final int POWERCELL_INNER_HOOD_REVERSE_PCM = 0; // Module B
    public static final int POWERCELL_KICKER_FORWARD_PCM = 3; // Module A
    public static final int POWERCELL_KICKER_REVERSE_PCM = 2; // Module A

    // hopper
    public static final int POWERCELL_GENEVA_MOTOR_CAN_ID = 8;
    public static final int POWERCELL_GENEVA_MOTOR_PDP_SLOT = 10;

    // intake
    public static final int POWERCELL_INNER_ROLLER_MOTOR_CAN_ID = 9;
    public static final int POWERCELL_INNER_ROLLER_MOTOR_PDP_SLOT = 4;
    public static final int POWERCELL_OUTER_ROLLER_MOTOR_CAN_ID = 10;
    public static final int POWERCELL_OUTER_ROLLER_MOTOR_PDP_SLOT = 11;
    public static final int POWERCELL_INTAKE_FORWARD_PCM = 5; // Module A
    public static final int POWERCELL_INTAKE_REVERSE_PCM = 4; // Module A

    public static final int POWERCELL_CAROUSEL_COUNTER_DIO = 0;

    public static final int POWERCELL_THROUGHBEAM_ANALOG_INPUT = 0;

    //================================================== Climber =================================================================

    public static final int CLIMBER_EXTEND_FORWARD_PCM = 7; // Module B
    public static final int CLIMBER_EXTEND_REVERSE_PCM = 6; // Module B

    public static final int CLIMBER_GRAB_FORWARD_PCM = 0; // Module A
    public static final int CLIMBER_GRAB_REVERSE_PCM = 1; // Module A

    public static final int CLIMBER_WINCH_MASTER_CAN_ID = 11;
    public static final int CLIMBER_WINCH_MASTER_PDP_SLOT = 2; // 2 & 13 ??
    public static final int CLIMBER_WINCH_FOLLOWER_CAN_ID = 12;
    public static final int CLIMBER_WINCH_FOLLOWER_PDP_SLOT = 13; // 2 & 13 ??

    //================================================== Control panel =================================================================

    public static final int CONTROLPANEL_SPINNER_CAN_ID = 13;
    public static final int CONTROLPANEL_SPINNER_PDP_SLOT = 5;

    public static final int CONTROLPANEL_EXTENDER_FORWARD_PCM = 7; // Module A
    public static final int CONTROLPANEL_EXTENDER_REVERSE_PCM = 6; // Module A
}
