package frc.robot.driver;

public enum MacroOperation implements IOperation
{
    AutonomousRoutine,

    // DriveTrain operations:
    PIDBrake,
    TurnInPlaceLeft,
    TurnInPlaceRight,

    // Climber operations
    ClimberExtend,

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
    TurretOffsetTask,

    // Testing operations
    FollowSomePath,
    FollowAnotherPath,
    FollowADifferentPath,
}
