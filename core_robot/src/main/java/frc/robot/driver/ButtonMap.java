package frc.robot.driver;

import javax.inject.Singleton;

import frc.robot.*;
import frc.robot.common.Helpers;
import frc.robot.driver.common.*;
import frc.robot.driver.common.buttons.*;
import frc.robot.driver.common.descriptions.*;
import frc.robot.driver.controltasks.*;

@Singleton
public class ButtonMap implements IButtonMap
{
    private static ShiftDescription[] ShiftButtonSchema = new ShiftDescription[]
    {
        new ShiftDescription(
            Shift.DriverDebug,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_LEFT_BUTTON),
        new ShiftDescription(
            Shift.OperatorDebug,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_LEFT_BUTTON),
    };

    public static AnalogOperationDescription[] AnalogOperationSchema = new AnalogOperationDescription[]
    {
        // DriveTrain operations
        new AnalogOperationDescription(
            AnalogOperation.DriveTrainMoveForward,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LSY,
            ElectronicsConstants.INVERT_Y_AXIS,
            TuningConstants.DRIVETRAIN_Y_DEAD_ZONE),
        new AnalogOperationDescription(
            AnalogOperation.DriveTrainTurn,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_RSX,
            ElectronicsConstants.INVERT_X_AXIS,
            TuningConstants.DRIVETRAIN_X_DEAD_ZONE),

        // ControlPanel operations
        new AnalogOperationDescription(
            AnalogOperation.ControlPanelSpinSpeed,
            UserInputDevice.Operator,
            AnalogAxis.PS4_LT,
            ElectronicsConstants.INVERT_TRIGGER_AXIS,
            -1.0,
            .1),

        // Climber operations
        new AnalogOperationDescription(
            AnalogOperation.ClimberWinch,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_RT,
            Shift.DriverDebug,
            Shift.DriverDebug,
            ElectronicsConstants.INVERT_TRIGGER_AXIS,
            -1.0,
            .1),

        // PowerCell operations
        new AnalogOperationDescription(
            AnalogOperation.PowerCellTurretPosition,
            UserInputDevice.Operator,
            AnalogAxis.PS4_RSX,
            AnalogAxis.PS4_RSY,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ElectronicsConstants.INVERT_X_AXIS,
            ElectronicsConstants.INVERT_Y_AXIS,
            -0.05,
            0.05,
            1.0,
            HardwareConstants.POWERCELL_TURRET_MAGIC_DONT_MOVE_VALUE,
            (x, y) ->
            {
                if (!TuningConstants.POWERCELL_TURRET_USE_PID)
                {
                    return -1.0 * x;
                }

                double angle = Helpers.convertToPolarAngle(x, y);
                if (angle == -1.0)
                {
                    return HardwareConstants.POWERCELL_TURRET_MAGIC_DONT_MOVE_VALUE;
                }

                // change to straight forward being 0, left being 90, down being 180, right being 270, opposite of the POV.
                return (angle + 270.0) % 360.0;
            }),
    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]
    {
        // Climber operations
        new DigitalOperationDescription(
            DigitalOperation.ClimberHookLock,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_X_BUTTON,
            Shift.DriverDebug,
            Shift.DriverDebug,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ClimberHookRelease,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            Shift.DriverDebug,
            Shift.DriverDebug,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ClimberRetract,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            Shift.DriverDebug,
            Shift.DriverDebug,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ClimberExtend,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_Y_BUTTON,
            Shift.DriverDebug,
            Shift.DriverDebug,
            ButtonType.Click),

        // ControlPanel operations
        new DigitalOperationDescription(
            DigitalOperation.ControlPanelExtend,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_SHARE_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ControlPanelRetract,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_OPTIONS_BUTTON,
            ButtonType.Click),

        // PowerCell operations
        new DigitalOperationDescription(
            DigitalOperation.PowerCellKick,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_RIGHT_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Simple),
        new DigitalOperationDescription(
            DigitalOperation.PowerCellIntakeExtend,
            UserInputDevice.Operator,
            0,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.PowerCellIntakeRetract,
            UserInputDevice.Operator,
            180,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.PowerCellIntake,
            UserInputDevice.Operator,
            90,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Simple),
        new DigitalOperationDescription(
            DigitalOperation.PowerCellOuttake,
            UserInputDevice.Operator,
            270,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Simple),
        new DigitalOperationDescription(
            DigitalOperation.PowerCellHoodPointBlank,
            UserInputDevice.Operator,
            180,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.PowerCellHoodShort,
            UserInputDevice.Operator,
            90,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Simple),
        new DigitalOperationDescription(
            DigitalOperation.PowerCellHoodMedium,
            UserInputDevice.Operator,
            270,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.PowerCellHoodLong,
            UserInputDevice.Operator,
            0,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Simple),
        new DigitalOperationDescription(
            DigitalOperation.PowerCellResetTurretFront,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_RIGHT_STICK_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Click),
    };

    public static MacroOperationDescription[] MacroSchema = new MacroOperationDescription[]
    {
        // DriveTrain macros
        new MacroOperationDescription(
            MacroOperation.PIDBrake,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LT,
            0.5,
            1.0,
            ButtonType.Simple,
            () -> new PIDBrakeTask(),
            new IOperation[]
            {
                DigitalOperation.DriveTrainUsePositionalMode,
                DigitalOperation.DriveTrainUseBrakeMode,
                AnalogOperation.DriveTrainLeftPosition,
                AnalogOperation.DriveTrainRightPosition,
            }),
        new MacroOperationDescription(
            MacroOperation.TurnInPlaceRight,
            UserInputDevice.Driver,
            90,
            Shift.DriverDebug,
            Shift.DriverDebug,
            ButtonType.Toggle,
            () -> new NavxTurnTask(true, -180, TuningConstants.NAVX_FAST_TURN_TIMEOUT, true, true),
            new IOperation[]
            {
                DigitalOperation.DriveTrainUsePositionalMode,
                DigitalOperation.DriveTrainUseBrakeMode,
                AnalogOperation.DriveTrainLeftPosition,
                AnalogOperation.DriveTrainRightPosition,
                AnalogOperation.DriveTrainLeftVelocity,
                AnalogOperation.DriveTrainRightVelocity,
                AnalogOperation.DriveTrainTurn,
                AnalogOperation.DriveTrainMoveForward,
                DigitalOperation.DriveTrainSimpleMode,
            },
            new IOperation[]
            {
                DigitalOperation.DriveTrainUsePositionalMode,
                DigitalOperation.DriveTrainUseBrakeMode,
                AnalogOperation.DriveTrainLeftPosition,
                AnalogOperation.DriveTrainRightPosition,
            }),
        new MacroOperationDescription(
            MacroOperation.TurnInPlaceLeft,
            UserInputDevice.Driver,
            270,
            Shift.DriverDebug,
            Shift.DriverDebug,
            ButtonType.Toggle,
            () -> new NavxTurnTask(true, 180, TuningConstants.NAVX_FAST_TURN_TIMEOUT, true, true),
            new IOperation[]
            {
                DigitalOperation.DriveTrainUsePositionalMode,
                DigitalOperation.DriveTrainUseBrakeMode,
                AnalogOperation.DriveTrainLeftPosition,
                AnalogOperation.DriveTrainRightPosition,
                AnalogOperation.DriveTrainTurn,
                AnalogOperation.DriveTrainMoveForward,
                DigitalOperation.DriveTrainSimpleMode,
            },
            new IOperation[]
            {
                DigitalOperation.DriveTrainUsePositionalMode,
                DigitalOperation.DriveTrainUseBrakeMode,
                AnalogOperation.DriveTrainLeftPosition,
                AnalogOperation.DriveTrainRightPosition,
            }),

        // ControlPanel Macros
        new MacroOperationDescription(
            MacroOperation.ControlPanelSpin,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_LEFT_STICK_BUTTON,
            ButtonType.Toggle,
            () -> new ColorSpinTask(),
            new IOperation[]
            {
                AnalogOperation.ControlPanelSpinSpeed
            }),

        // PowerCell Macros
        new MacroOperationDescription(
            MacroOperation.FullHopperShot,
            UserInputDevice.Operator,
            AnalogAxis.PS4_RT,
            0.5,
            1.0,
            ButtonType.Toggle,
            () -> new FullHopperShotTask(),
            new IOperation[]
            {
                DigitalOperation.PowerCellKick,
                DigitalOperation.PowerCellMoveOneSlot,
            }),
        new MacroOperationDescription(
            MacroOperation.TracerShot,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_RIGHT_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> new TracerShotTask(),
            new IOperation[]
            {
                DigitalOperation.PowerCellKick,
                DigitalOperation.PowerCellMoveOneSlot,
            }),
        new MacroOperationDescription(
            MacroOperation.SpinUpVisionDistance,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_SQUARE_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(
                new IntakePositionTask(true),
                new FlyWheelVisionSpinTask()),
            new IOperation[]
            {
                DigitalOperation.PowerCellIntakeExtend,
                DigitalOperation.PowerCellIntakeRetract,
                DigitalOperation.PowerCellHoodPointBlank,
                DigitalOperation.PowerCellHoodShort,
                DigitalOperation.PowerCellHoodMedium,
                DigitalOperation.PowerCellHoodLong,
                AnalogOperation.PowerCellFlywheelVelocity,
                DigitalOperation.VisionEnable
            }),
        new MacroOperationDescription(
            MacroOperation.SpinUpPointBlank,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_TRIANGLE_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(
                new IntakePositionTask(true),
                ConcurrentTask.AllTasks(
                    new FlyWheelHoodTask(DigitalOperation.PowerCellHoodPointBlank),
                    new FlyWheelFixedSpinTask(TuningConstants.POWERCELL_FLYWHEEL_POINT_BLANK_MOTOR_VELOCITY))),
            new IOperation[]
            {
                DigitalOperation.PowerCellIntakeExtend,
                DigitalOperation.PowerCellIntakeRetract,
                DigitalOperation.PowerCellHoodPointBlank,
                DigitalOperation.PowerCellHoodShort,
                DigitalOperation.PowerCellHoodMedium,
                DigitalOperation.PowerCellHoodLong,
                AnalogOperation.PowerCellFlywheelVelocity,
                DigitalOperation.VisionEnable,
            }),
        new MacroOperationDescription(
            MacroOperation.AlignShotVision,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_X_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> new TurretVisionCenteringTask(false, true),
            new IOperation[]
            {
                AnalogOperation.PowerCellTurretPosition,
                DigitalOperation.VisionEnable,
            }),
        new MacroOperationDescription(
            MacroOperation.SpinUpMedium,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_CIRCLE_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(
                new IntakePositionTask(true),
                ConcurrentTask.AllTasks(
                    new FlyWheelHoodTask(DigitalOperation.PowerCellHoodLong),
                    new FlyWheelFixedSpinTask(TuningConstants.POWERCELL_FLYWHEEL_MEDIUM_MOTOR_VELOCITY))),
            new IOperation[]
            {
                DigitalOperation.PowerCellIntakeExtend,
                DigitalOperation.PowerCellIntakeRetract,
                DigitalOperation.PowerCellHoodPointBlank,
                DigitalOperation.PowerCellHoodShort,
                DigitalOperation.PowerCellHoodMedium,
                DigitalOperation.PowerCellHoodLong,
                AnalogOperation.PowerCellFlywheelVelocity,
                DigitalOperation.VisionEnable,
            }),
        new MacroOperationDescription(
            MacroOperation.TurretMoveLeft,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_SQUARE_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Toggle,
            () -> new TurretMoveTask(0.1, 1.0),
            new IOperation[]
            {
                AnalogOperation.PowerCellTurretPosition,
            }),
        new MacroOperationDescription(
            MacroOperation.TurretMoveRight,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_CIRCLE_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Toggle,
            () -> new TurretMoveTask(0.1, -1.0),
            new IOperation[]
            {
                AnalogOperation.PowerCellTurretPosition,
            }),
        new MacroOperationDescription(
            MacroOperation.IncreaseFlyWheelSpeed,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_TRIANGLE_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Toggle,
            () -> new ChangeFlyWheelSpeedTask(0.1, 0.05),
            new IOperation[]
            {
                AnalogOperation.PowerCellFlywheelVelocity,
            }),
        new MacroOperationDescription(
            MacroOperation.DecreaseFlyWheelSpeed,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_X_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Toggle,
            () -> new ChangeFlyWheelSpeedTask(0.1, -0.05),
            new IOperation[]
            {
                AnalogOperation.PowerCellFlywheelVelocity,
            }),
        new MacroOperationDescription(
            MacroOperation.StopFlywheelPlease,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_RIGHT_STICK_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Simple,
            () -> new FlyWheelHoodTask(DigitalOperation.PowerCellHoodPointBlank),
            new IOperation[]
            {
                AnalogOperation.PowerCellFlywheelVelocity,
                DigitalOperation.PowerCellKick,
                DigitalOperation.PowerCellMoveOneSlot,
                DigitalOperation.PowerCellHoodPointBlank,
                DigitalOperation.PowerCellHoodShort,
                DigitalOperation.PowerCellHoodMedium,
                DigitalOperation.PowerCellHoodLong,
            }),
    };

    @Override
    public ShiftDescription[] getShiftSchema()
    {
        return ButtonMap.ShiftButtonSchema;
    }

    @Override
    public AnalogOperationDescription[] getAnalogOperationSchema()
    {
        return ButtonMap.AnalogOperationSchema;
    }

    @Override
    public DigitalOperationDescription[] getDigitalOperationSchema()
    {
        return ButtonMap.DigitalOperationSchema;
    }

    @Override
    public MacroOperationDescription[] getMacroOperationSchema()
    {
        return ButtonMap.MacroSchema;
    }
}
