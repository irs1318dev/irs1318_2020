package frc.robot.driver;

public enum AnalogOperation implements IOperation
{
    // DriveTrain operations:
    DriveTrainMoveForward,
    DriveTrainTurn,
    DriveTrainLeftPosition,
    DriveTrainRightPosition,
    DriveTrainLeftVelocity,
    DriveTrainRightVelocity,
    DriveTrainHeadingCorrection,
    PositionStartingAngle,

    // Control Panel Operations
    ControlPanelSpinSpeed,

    // Climber Operation
    ClimberWinch,

    // Power Cell Operations
    PowerCellTurretPosition,
    PowerCellFlywheelVelocity,
    PowerCellTurretOffset,
    PowerCellGenevaPower,
}
