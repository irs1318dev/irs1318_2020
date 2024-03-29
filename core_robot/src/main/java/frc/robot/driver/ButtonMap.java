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
            Shift.OperatorDebug,
            Shift.OperatorDebug,
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
            AnalogOperation.PowerCellFlywheelVelocity,
            TuningConstants.MAGIC_NULL_VALUE),

        new AnalogOperationDescription(
            AnalogOperation.PowerCellTurretPosition,
            UserInputDevice.Operator,
            AnalogAxis.PS4_LSX,
            AnalogAxis.PS4_LSY,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ElectronicsConstants.INVERT_X_AXIS,
            ElectronicsConstants.INVERT_Y_AXIS,
            -0.25,
            0.25,
            1.0,
            TuningConstants.POWERCELL_TURRET_MAGIC_DONT_MOVE_VALUE,
            (x, y) ->
            {
                if (!TuningConstants.POWERCELL_TURRET_USE_PID)
                {
                    return -1.0 * x;
                }

                // angle should always be a non-negative numnber
                double angle = Helpers.convertToPolarAngle(x, y);
                if (angle < 0.0)
                {
                    return TuningConstants.POWERCELL_TURRET_MAGIC_DONT_MOVE_VALUE;
                }

                // change to straight forward being 0, left being 90, down being 180, right being 270, opposite of the POV.
                return (angle + 270.0) % 360.0;
            }),

        new AnalogOperationDescription(
            AnalogOperation.PowerCellGenevaPower,
            UserInputDevice.Operator,
            AnalogAxis.PS4_RSX,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ElectronicsConstants.INVERT_X_AXIS,
            -0.05,
            0.05,
            1.0,
            TuningConstants.MAGIC_NULL_VALUE),
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
            // Shift.OperatorDebug,
            // Shift.OperatorDebug,
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
            UserInputDeviceButton.PS4_LEFT_STICK_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.PowerCellMoveToPreviousSlot,
            UserInputDevice.Operator,
            AnalogAxis.PS4_RSX,
            -1.0,
            -0.25,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.PowerCellMoveToNextSlot,
            UserInputDevice.Operator,
            AnalogAxis.PS4_RSX,
            0.25,
            1.0,
            Shift.OperatorDebug,
            Shift.None,
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

        // Climber Macros
        new MacroOperationDescription(
            MacroOperation.ClimberExtend,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_Y_BUTTON,
            Shift.DriverDebug,
            Shift.DriverDebug,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(
                ConcurrentTask.AllTasks(
                    new IntakePositionTask(false),
                    new FlywheelHoodTask(DigitalOperation.PowerCellHoodPointBlank),
                    new TurretMoveTask(1.0, true, 90.0)),
                new ClimberPositionTask(0.5, true)),
            new IOperation[]
            {
                DigitalOperation.PowerCellIntakeExtend,
                DigitalOperation.PowerCellIntakeRetract,
                DigitalOperation.ClimberExtend,
                DigitalOperation.ClimberRetract,
                DigitalOperation.PowerCellHoodPointBlank,
                DigitalOperation.PowerCellHoodShort,
                DigitalOperation.PowerCellHoodMedium,
                DigitalOperation.PowerCellHoodLong,
                AnalogOperation.PowerCellTurretPosition,
            }),

        // ControlPanel Macros
        new MacroOperationDescription(
            MacroOperation.ControlPanelSpin,
            UserInputDevice.Operator,
            AnalogAxis.PS4_LT,
            0.5,
            1.0,
            Shift.OperatorDebug,
            Shift.None,
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
            0.1,
            1.0,
            ButtonType.Toggle,
            () -> new FullHopperShotTask(),
            new IOperation[]
            {
                DigitalOperation.PowerCellKick,
                DigitalOperation.PowerCellMoveToNextSlot,
                DigitalOperation.PowerCellMoveToPreviousSlot,
            }),
        // new MacroOperationDescription(
        //     MacroOperation.TracerShot,
        //     UserInputDevice.Operator,
        //     UserInputDeviceButton.PS4_RIGHT_BUTTON,
        //     Shift.OperatorDebug,
        //     Shift.None,
        //     ButtonType.Toggle,
        //     () -> new TracerShotTask(),
        //     new IOperation[]
        //     {
        //         DigitalOperation.PowerCellKick,
        //         DigitalOperation.PowerCellMoveToNextSlot,
        //         DigitalOperation.PowerCellMoveToPreviousSlot,
        //     }),
        new MacroOperationDescription(
            MacroOperation.SpinUpVisionDistance,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_SQUARE_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(
                new IntakePositionTask(true),
                // new FlyWheelVisionSpinTask()),
                new FlywheelFixedSpinTask(TuningConstants.POWERCELL_FLYWHEEL_MEDIUM_MOTOR_VELOCITY)),
            new IOperation[]
            {
                DigitalOperation.PowerCellIntakeExtend,
                DigitalOperation.PowerCellIntakeRetract,
                // DigitalOperation.PowerCellHoodPointBlank,
                // DigitalOperation.PowerCellHoodShort,
                // DigitalOperation.PowerCellHoodMedium,
                // DigitalOperation.PowerCellHoodLong,
                AnalogOperation.PowerCellFlywheelVelocity,
                // DigitalOperation.VisionEnable,
            },
            new IOperation[]
            {
                DigitalOperation.PowerCellIntakeExtend,
                DigitalOperation.PowerCellIntakeRetract,
                AnalogOperation.PowerCellFlywheelVelocity,
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
                    new FlywheelHoodTask(DigitalOperation.PowerCellHoodMedium),
                    new FlywheelFixedSpinTask(TuningConstants.POWERCELL_FLYWHEEL_INITIATIONLINE_BACK_MOTOR_VELOCITY))),
            new IOperation[]
            {
                DigitalOperation.PowerCellIntakeExtend,
                DigitalOperation.PowerCellIntakeRetract,
                DigitalOperation.PowerCellHoodPointBlank,
                DigitalOperation.PowerCellHoodShort,
                DigitalOperation.PowerCellHoodMedium,
                DigitalOperation.PowerCellHoodLong,
                AnalogOperation.PowerCellFlywheelVelocity,
            },
            new IOperation[]
            {
                DigitalOperation.PowerCellIntakeExtend,
                DigitalOperation.PowerCellIntakeRetract,
                AnalogOperation.PowerCellFlywheelVelocity,
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
            },
            new IOperation[]
            {
                AnalogOperation.PowerCellTurretPosition,
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
                    new FlywheelHoodTask(DigitalOperation.PowerCellHoodShort),
                    new FlywheelFixedSpinTask(TuningConstants.POWERCELL_FLYWHEEL_INITIATIONLINE_FRONT_MOTOR_VELOCITY))),
            new IOperation[]
            {
                DigitalOperation.PowerCellIntakeExtend,
                DigitalOperation.PowerCellIntakeRetract,
                DigitalOperation.PowerCellHoodPointBlank,
                DigitalOperation.PowerCellHoodShort,
                DigitalOperation.PowerCellHoodMedium,
                DigitalOperation.PowerCellHoodLong,
                AnalogOperation.PowerCellFlywheelVelocity,
            },
            new IOperation[]
            {
                DigitalOperation.PowerCellIntakeExtend,
                DigitalOperation.PowerCellIntakeRetract,
                AnalogOperation.PowerCellFlywheelVelocity,
            }),
        new MacroOperationDescription(
            MacroOperation.TurretMoveLeft,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_SQUARE_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Toggle,
            () -> new TurretMoveTask(0.1, false, 1.0),
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
            () -> new TurretMoveTask(0.1, false, -1.0),
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
            () -> new FlywheelModifySpeedTask(0.05),
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
            () -> new FlywheelModifySpeedTask(-0.05),
            new IOperation[]
            {
                AnalogOperation.PowerCellFlywheelVelocity,
            }),
        new MacroOperationDescription(
            MacroOperation.StopFlywheelPlease,
            UserInputDevice.Operator,
            UserInputDeviceButton.PS4_LEFT_STICK_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Simple,
            () -> ConcurrentTask.AllTasks(
                new FlywheelHoodTask(DigitalOperation.PowerCellHoodPointBlank),
                new FlywheelFixedSpinTask(0.0)),
            new IOperation[]
            {
                AnalogOperation.PowerCellFlywheelVelocity,
                DigitalOperation.PowerCellKick,
                DigitalOperation.PowerCellMoveToNextSlot,
                DigitalOperation.PowerCellMoveToPreviousSlot,
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
