package frc.robot.driver;

public enum MacroOperation implements IOperation
{
    // DriveTrain operations:
    AutonomousRoutine,
    PIDBrake,
    TurnInPlaceLeft,
    TurnInPlaceRight,
    FollowSomePath,
    FollowAnotherPath,
    FollowADifferentPath,

    // Vision operations:
    VisionCenterAndAdvance,
    VisionFastCenterAndAdvance,
    DriveForwardTurnRight,
}
