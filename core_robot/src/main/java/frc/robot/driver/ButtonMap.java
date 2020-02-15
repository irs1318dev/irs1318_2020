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
            UserInputDeviceButton.XBONE_LEFT_BUTTON),
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
            AnalogAxis.XBONE_LT,
            ElectronicsConstants.INVERT_TRIGGER_AXIS,
            .1),

        // PowerCell operations
        new AnalogOperationDescription( // for testing:
            AnalogOperation.PowerCellTurretPosition,
            UserInputDevice.Operator,
            AnalogAxis.XBONE_RSX,
            ElectronicsConstants.INVERT_X_AXIS,
            .1,
            -1.0),
        // new AnalogOperationDescription(
        //     AnalogOperation.PowerCellTurretPosition,
        //     UserInputDevice.Operator,
        //     AnalogAxis.XBONE_RSX,
        //     AnalogAxis.XBONE_RSY,
        //     Shift.OperatorDebug,
        //     Shift.OperatorDebug,
        //     ElectronicsConstants.INVERT_X_AXIS,
        //     ElectronicsConstants.INVERT_Y_AXIS,
        //     0.05,
        //     1.0,
        //     HardwareConstants.POWERCELL_TURRET_MAGIC_DONT_MOVE_VALUE,
        //     (x, y) ->
        //     {
        //         double angle = Helpers.convertToPolarAngle(x, y);
        //         if (angle == -1.0)
        //         {
        //             return HardwareConstants.POWERCELL_TURRET_MAGIC_DONT_MOVE_VALUE;
        //         }

        //         // change to straight forward being 0, left being 90, down being 180, right being 270, opposite of the POV.
        //         return (angle + 270.0) % 360.0;
        //     }),
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
            UserInputDeviceButton.XBONE_SELECT_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ControlPanelRetract,
            UserInputDevice.Operator,
            UserInputDeviceButton.XBONE_START_BUTTON,
            ButtonType.Click),

        // PowerCell operations
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
            UserInputDeviceButton.XBONE_LEFT_STICK_BUTTON,
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
            AnalogAxis.XBONE_RT,
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
            UserInputDeviceButton.XBONE_RIGHT_BUTTON,
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
            UserInputDeviceButton.XBONE_X_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> new FlyWheelVisionSpinTask(),
            new IOperation[]
            {
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
            UserInputDeviceButton.XBONE_Y_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> ConcurrentTask.AllTasks(
                new FlyWheelHoodTask(DigitalOperation.PowerCellHoodPointBlank),
                new FlyWheelFixedSpinTask(TuningConstants.POWERCELL_FLYWHEEL_POINT_BLANK_MOTOR_VELOCITY)),
            new IOperation[]
            {
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
            UserInputDeviceButton.XBONE_A_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> new TurretVisionCenteringTask(),
            new IOperation[]
            {
                AnalogOperation.PowerCellTurretPosition,
                DigitalOperation.VisionEnable,
            }),
        new MacroOperationDescription(
            MacroOperation.SpinUpMedium,
            UserInputDevice.Operator,
            UserInputDeviceButton.XBONE_B_BUTTON,
            Shift.OperatorDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> ConcurrentTask.AllTasks(
                new FlyWheelHoodTask(DigitalOperation.PowerCellHoodMedium),
                new FlyWheelFixedSpinTask(TuningConstants.POWERCELL_FLYWHEEL_MEDIUM_MOTOR_VELOCITY)),
            new IOperation[]
            {
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
            UserInputDeviceButton.XBONE_X_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Toggle,
            () -> new TurretMoveTask(0.1, 5.0),
            new IOperation[]
            {
                AnalogOperation.PowerCellTurretPosition,
            }),
        new MacroOperationDescription(
            MacroOperation.TurretMoveRight,
            UserInputDevice.Operator,
            UserInputDeviceButton.XBONE_B_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Toggle,
            () -> new TurretMoveTask(0.1, -5.0),
            new IOperation[]
            {
                AnalogOperation.PowerCellTurretPosition,
            }),
        new MacroOperationDescription(
            MacroOperation.IncreaseFlyWheelSpeed,
            UserInputDevice.Operator,
            UserInputDeviceButton.XBONE_Y_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Toggle,
            () -> new ChangeFlyWheelSpeedTask(0.1, 5.0),
            new IOperation[]
            {
                AnalogOperation.PowerCellFlywheelVelocity,
            }),
        new MacroOperationDescription(
            MacroOperation.DecreaseFlyWheelSpeed,
            UserInputDevice.Operator,
            UserInputDeviceButton.XBONE_A_BUTTON,
            Shift.OperatorDebug,
            Shift.OperatorDebug,
            ButtonType.Toggle,
            () -> new ChangeFlyWheelSpeedTask(0.1, -5.0),
            new IOperation[]
            {
                AnalogOperation.PowerCellFlywheelVelocity,
            }),
        new MacroOperationDescription(
            MacroOperation.StopFlywheelPlease,
            UserInputDevice.Operator,
            UserInputDeviceButton.XBONE_RIGHT_STICK_BUTTON,
            ButtonType.Simple,
            () -> new WaitTask(0.0),
            new IOperation[]
            {
                AnalogOperation.PowerCellFlywheelVelocity,
                DigitalOperation.PowerCellKick,
                DigitalOperation.PowerCellMoveOneSlot,
            }),
 
        // Testing macros:
        new MacroOperationDescription(
            MacroOperation.FollowSomePath,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            Shift.DriverDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> new FollowPathTask("/Paths/straight_path.csv"),
            new IOperation[]
            {
                DigitalOperation.DriveTrainUsePositionalMode,
                DigitalOperation.DriveTrainUseBrakeMode,
                AnalogOperation.DriveTrainLeftPosition,
                AnalogOperation.DriveTrainRightPosition,
                AnalogOperation.DriveTrainLeftVelocity,
                AnalogOperation.DriveTrainRightVelocity,
                AnalogOperation.DriveTrainHeadingCorrection,
                DigitalOperation.DriveTrainUsePathMode,
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
            MacroOperation.FollowAnotherPath,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            Shift.DriverDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> new FollowPathTask("/Paths/curved_path.csv"),
            new IOperation[]
            {
                DigitalOperation.DriveTrainUsePositionalMode,
                DigitalOperation.DriveTrainUseBrakeMode,
                AnalogOperation.DriveTrainLeftPosition,
                AnalogOperation.DriveTrainRightPosition,
                AnalogOperation.DriveTrainLeftVelocity,
                AnalogOperation.DriveTrainRightVelocity,
                AnalogOperation.DriveTrainHeadingCorrection,
                DigitalOperation.DriveTrainUsePathMode,
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
            MacroOperation.FollowADifferentPath,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_Y_BUTTON,
            Shift.DriverDebug,
            Shift.None,
            ButtonType.Toggle,
            () -> new FollowPathTask("/Paths/backwards_path.csv"),
            new IOperation[]
            {
                DigitalOperation.DriveTrainUsePositionalMode,
                DigitalOperation.DriveTrainUseBrakeMode,
                AnalogOperation.DriveTrainLeftPosition,
                AnalogOperation.DriveTrainRightPosition,
                AnalogOperation.DriveTrainLeftVelocity,
                AnalogOperation.DriveTrainRightVelocity,
                AnalogOperation.DriveTrainHeadingCorrection,
                DigitalOperation.DriveTrainUsePathMode,
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
