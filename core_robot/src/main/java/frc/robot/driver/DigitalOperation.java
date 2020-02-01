package frc.robot.driver;

public enum DigitalOperation implements IOperation
{
    // Vision operations:
    VisionForceDisable,
    VisionDisable,
    VisionEnable,
    VisionEnableOffboardStream,
    VisionEnableOffboardProcessing,

    // Compressor operations:
    CompressorForceDisable,

    // DriveTrain operations:
    DriveTrainEnablePID,
    DriveTrainDisablePID,
    DriveTrainSimpleMode,
    DriveTrainUseBrakeMode,
    DriveTrainUsePositionalMode,
    DriveTrainUseSimplePathMode,
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
    PowerCellUpperHoodExtend,
    PowerCellUpperHoodRetract,
    PowerCellLowerHoodExtend,
    PowerCellLowerHoodRetract,
    PowerCellKick,
    PowerCellIntakeExtend,
    PowerCellIntakeRetract,
    PowerCellIntake,
    PowerCellOuttake,
}
