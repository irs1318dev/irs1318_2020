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

    // Vision operations:
    VisionCenterAndAdvance,
    VisionFastCenterAndAdvance,
    DriveForwardTurnRight,
}
