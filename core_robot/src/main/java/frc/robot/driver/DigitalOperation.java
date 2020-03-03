package frc.robot.driver;

public enum DigitalOperation implements IOperation
{
    // Vision operations:
    VisionForceDisable,
    VisionEnable,
    VisionDisableOffboardStream,
    VisionDisableOffboardProcessing,

    // Compressor operations:
    CompressorForceDisable,

    // DriveTrain operations:
    DriveTrainEnablePID,
    DriveTrainDisablePID,
    DriveTrainSimpleMode,
    DriveTrainUseBrakeMode,
    DriveTrainUsePositionalMode,
    DriveTrainUsePathMode,
    DriveTrainSwapFrontOrientation,

    // Control Panel Operations
    ControlPanelExtend,
    ControlPanelRetract,

    // Climber Operations
    ClimberHookRelease,
    ClimberHookLock,
    ClimberExtend,
    ClimberRetract,

    // Power Cell Operations
    PowerCellHoodPointBlank,
    PowerCellHoodShort,
    PowerCellHoodMedium,
    PowerCellHoodLong,
    PowerCellKick,
    PowerCellIntakeExtend,
    PowerCellIntakeRetract,
    PowerCellIntake,
    PowerCellOuttake,
    PowerCellMoveToNextSlot,
    PowerCellMoveToPreviousSlot,
    PowerCellResetTurretFront,
}
