package frc.robot.driver;

public enum MacroOperation implements IOperation
{
    AutonomousRoutine,

    // DriveTrain operations:
    PIDBrake,
    TurnInPlaceLeft,
    TurnInPlaceRight,
    FollowSomePath,
    FollowAnotherPath,
    FollowADifferentPath,

    // ColorSpinner operations
    Spin,

    // PowerCell operations
    VisionAlignShooting,
    SpinUpPointBlank,
    TracerShot,
    FullHopperShot,
}
