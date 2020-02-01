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

    public static final int PCM_A_MODULE = 0;
    public static final int PCM_B_MODULE = 1;

    public static final int JOYSTICK_DRIVER_PORT = 0;
    public static final int JOYSTICK_CO_DRIVER_PORT = 1;

    //================================================== Vision ==============================================================

    public static final int VISION_RING_LIGHT_PWM_CHANNEL = -1;

    //================================================== Indicator Lights ==============================================================

    public static final int INDICATOR_VISION_RELAY_CHANNEL = -1;

    //================================================== DriveTrain ==============================================================

    public static final int DRIVETRAIN_LEFT_MASTER_CAN_ID = 1;
    public static final int DRIVETRAIN_LEFT_FOLLOWER1_CAN_ID = 2;
    //public static final int DRIVETRAIN_LEFT_FOLLOWER2_CAN_ID = 3;
    public static final int DRIVETRAIN_RIGHT_MASTER_CAN_ID = 3;
    public static final int DRIVETRAIN_RIGHT_FOLLOWER1_CAN_ID = 4;
    //public static final int DRIVETRAIN_RIGHT_FOLLOWER2_CAN_ID = 6;

    //================================================== PowerCell =================================================================
    
    //shooter
    public static final int FLYWHEEL_MASTER_CAN_ID = 5;
    public static final int FLYWHEEL_FOLLOWER_CAN_ID = 6;
    public static final int TURRET_CAN_ID = 7;
    public static final int LOWER_HOOD_FORWARD_PCM = -1;
    public static final int LOWER_HOOD_REVERSE_PCM = -1;
    public static final int UPPER_HOOD_FORWARD_PCM = -1;
    public static final int UPPER_HOOD_REVERSE_PCM = -1;

    //hopper
    public static final int GENEVAMOTOR_CAN_ID = 8;
    public static final int KICKER_FORWARD_PCM = -1;
    public static final int KICKER_REVERSE_PCM = -1;

    //intake
    public static final int ROLLERMOTOR_INNER_CAN_ID = 9;
    public static final int ROLLERMOTOR_OUTER_CAN_ID = 10;
    public static final int INTAKE_FORWARD_PCM = -1;
    public static final int INTAKE_REVERSE_PCM = -1;

    public static final int CAROUSEL_COUNTER_DIO = -1;

    //================================================== Climber =================================================================

    public static final int CLIMBER_EXTEND_FORWARD_CAN_ID = -1;
    public static final int CLIMBER_EXTEND_REVERSE_CAN_ID = -1;

    public static final int CLIMBER_GRAB_FORWARD_CAN_ID = -1;
    public static final int CLIMBER_GRAB_REVERSE_CAN_ID = -1;

    public static final int WINCH_MASTER_CAN_ID = 11;
    public static final int WINCH_FOLLOWER_CAN_ID = 12;


    //================================================== Control panel =================================================================

    public static final int CONTROL_PANEL_SPINNER_CAN_ID = 13;

    public static final int EXTENDER_FORWARD_PCM = -1
    public static final int EXTENDER_REVERSE_PCM = -1

}
