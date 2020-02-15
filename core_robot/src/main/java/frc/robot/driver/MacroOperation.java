package frc.robot.driver;

public enum MacroOperation implements IOperation
{
    AutonomousRoutine,

    // DriveTrain operations:
    PIDBrake,
    TurnInPlaceLeft,
    TurnInPlaceRight,

    // ColorSpinner operations
    ControlPanelSpin,

    // PowerCell operations
    AlignShotVision,
    SpinUpVisionDistance,
    SpinUpPointBlank,
    SpinUpMedium,
    TracerShot,
    FullHopperShot,
    TurretMoveLeft,
    TurretMoveRight,
    IncreaseFlyWheelSpeed,
    DecreaseFlyWheelSpeed,
    StopFlywheelPlease,

    // Testing operations
    FollowSomePath,
    FollowAnotherPath,
    FollowADifferentPath,
}
