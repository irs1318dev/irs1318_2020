package frc.robot.mechanisms;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import frc.robot.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.Driver;
import frc.robot.mechanisms.DriveTrainMechanism;

public class DriveTrainMechanismTest
{
    @Test
    public void testSetPower_Zero()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        TestProvider testProvider = new TestProvider();
        ITalonFX leftMotor = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_LEFT_MASTER_CAN_ID);
        ITalonFX rightMotor = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_RIGHT_MASTER_CAN_ID);
        ITalonFX leftFollowerMotor1 = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_LEFT_FOLLOWER1_CAN_ID);
        ITalonFX rightFollowerMotor1 = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_RIGHT_FOLLOWER1_CAN_ID);
        // ITalonFX leftFollowerMotor2 = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_LEFT_FOLLOWER2_CAN_ID);
        // ITalonFX rightFollowerMotor2 = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_RIGHT_FOLLOWER2_CAN_ID);

        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(DigitalOperation.DriveTrainDisablePID);
        doReturn(false).when(driver).getDigital(DigitalOperation.DriveTrainEnablePID);
        doReturn(0.0).when(driver).getAnalog(AnalogOperation.DriveTrainLeftPosition);
        doReturn(0.0).when(driver).getAnalog(AnalogOperation.DriveTrainRightPosition);
        doReturn(false).when(driver).getDigital(DigitalOperation.DriveTrainUsePositionalMode);
        doReturn(false).when(driver).getDigital(DigitalOperation.DriveTrainSwapFrontOrientation);
        doReturn(false).when(driver).getDigital(DigitalOperation.DriveTrainSimpleMode);
        doReturn(0.0).when(driver).getAnalog(AnalogOperation.DriveTrainMoveForward);
        doReturn(0.0).when(driver).getAnalog(AnalogOperation.DriveTrainTurn);
        doReturn(0.0).when(leftMotor).getError();
        doReturn(0.0).when(leftMotor).getVelocity();
        doReturn(0).when(leftMotor).getPosition();
        doReturn(0.0).when(rightMotor).getError();
        doReturn(0.0).when(rightMotor).getVelocity();
        doReturn(0).when(rightMotor).getPosition();

        DriveTrainMechanism driveTrainMechanism = new DriveTrainMechanism(logger, testProvider, timer);
        driveTrainMechanism.setDriver(driver);
        driveTrainMechanism.readSensors();
        driveTrainMechanism.update();

        // from constructor:
        verify(leftMotor).setNeutralMode(eq(MotorNeutralMode.Brake));
        verify(leftMotor).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_LEFT_MASTER_INVERT_OUTPUT));
        verify(leftMotor).setInvertSensor(eq(HardwareConstants.DRIVETRAIN_LEFT_INVERT_SENSOR));
        verify(leftMotor).setSensorType(TalonXFeedbackDevice.IntegratedSensor);
        verify(leftMotor).setFeedbackFramePeriod(5);
        verify(leftMotor).setPIDFFramePeriod(5);
        verify(leftMotor).configureVelocityMeasurements(eq(10), eq(32));
        verify(leftMotor).setSelectedSlot(eq(0));
        verify(leftFollowerMotor1).setNeutralMode(eq(MotorNeutralMode.Brake));
        verify(leftFollowerMotor1).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_LEFT_FOLLOWER1_INVERT_OUTPUT));
        verify(leftFollowerMotor1).follow(eq(leftMotor));
        // verify(leftFollowerMotor2).setNeutralMode(eq(MotorNeutralMode.Brake));
        // verify(leftFollowerMotor2).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_LEFT_FOLLOWER2_INVERT_OUTPUT));
        // verify(leftFollowerMotor2).follow(eq(leftMotor));
        verify(rightMotor).setNeutralMode(eq(MotorNeutralMode.Brake));
        verify(rightMotor).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_RIGHT_MASTER_INVERT_OUTPUT));
        verify(rightMotor).setInvertSensor(eq(HardwareConstants.DRIVETRAIN_RIGHT_INVERT_SENSOR));
        verify(rightMotor).setSensorType(TalonXFeedbackDevice.IntegratedSensor);
        verify(rightMotor).setFeedbackFramePeriod(5);
        verify(rightMotor).setPIDFFramePeriod(5);
        verify(rightMotor).configureVelocityMeasurements(eq(10), eq(32));
        verify(rightMotor).setSelectedSlot(eq(0));
        verify(rightFollowerMotor1).setNeutralMode(eq(MotorNeutralMode.Brake));
        verify(rightFollowerMotor1).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_RIGHT_FOLLOWER1_INVERT_OUTPUT));
        verify(rightFollowerMotor1).follow(eq(rightMotor));
        // verify(rightFollowerMotor2).setNeutralMode(eq(MotorNeutralMode.Brake));
        // verify(rightFollowerMotor2).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_RIGHT_FOLLOWER2_INVERT_OUTPUT));
        // verify(rightFollowerMotor2).follow(eq(rightMotor));

        // from setDriver:
        verify(leftMotor).setPIDF(
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KP),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KI),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KD),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KF),
            eq(0));
        verify(rightMotor).setPIDF(
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KP),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KI),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KD),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KF),
            eq(0));
        verify(leftMotor).setControlMode(eq(TalonSRXControlMode.Velocity));
        verify(rightMotor).setControlMode(eq(TalonSRXControlMode.Velocity));

        // from readSensors:
        verify(leftMotor).getError();
        verify(leftMotor).getVelocity();
        verify(leftMotor).getPosition();
        verify(rightMotor).getError();
        verify(rightMotor).getVelocity();
        verify(rightMotor).getPosition();

        // from update:
        verify(leftMotor).set(eq(0.0));
        verify(rightMotor).set(eq(0.0));

        verifyNoMoreInteractions(leftMotor);
        verifyNoMoreInteractions(rightMotor);
        verifyNoMoreInteractions(leftFollowerMotor1);
        verifyNoMoreInteractions(rightFollowerMotor1);
        // verifyNoMoreInteractions(leftFollowerMotor2);
        // verifyNoMoreInteractions(rightFollowerMotor2);
    }

    @Test
    public void testStop()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        TestProvider testProvider = new TestProvider();
        ITalonFX leftMotor = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_LEFT_MASTER_CAN_ID);
        ITalonFX rightMotor = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_RIGHT_MASTER_CAN_ID);
        ITalonFX leftFollowerMotor1 = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_LEFT_FOLLOWER1_CAN_ID);
        ITalonFX rightFollowerMotor1 = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_RIGHT_FOLLOWER1_CAN_ID);
        // ITalonFX leftFollowerMotor2 = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_LEFT_FOLLOWER2_CAN_ID);
        // ITalonFX rightFollowerMotor2 = testProvider.getTalonFX(ElectronicsConstants.DRIVETRAIN_RIGHT_FOLLOWER2_CAN_ID);

        Driver driver = mock(Driver.class);

        doReturn(false).when(driver).getDigital(DigitalOperation.DriveTrainDisablePID);
        doReturn(false).when(driver).getDigital(DigitalOperation.DriveTrainEnablePID);
        doReturn(0.0).when(driver).getAnalog(AnalogOperation.DriveTrainLeftPosition);
        doReturn(0.0).when(driver).getAnalog(AnalogOperation.DriveTrainRightPosition);
        doReturn(false).when(driver).getDigital(DigitalOperation.DriveTrainUsePositionalMode);
        doReturn(false).when(driver).getDigital(DigitalOperation.DriveTrainSwapFrontOrientation);
        doReturn(false).when(driver).getDigital(DigitalOperation.DriveTrainSimpleMode);
        doReturn(0.0).when(driver).getAnalog(AnalogOperation.DriveTrainMoveForward);
        doReturn(0.0).when(driver).getAnalog(AnalogOperation.DriveTrainTurn);
        doReturn(0.0).when(leftMotor).getError();
        doReturn(0.0).when(leftMotor).getVelocity();
        doReturn(0).when(leftMotor).getPosition();
        doReturn(0.0).when(rightMotor).getError();
        doReturn(0.0).when(rightMotor).getVelocity();
        doReturn(0).when(rightMotor).getPosition();

        DriveTrainMechanism driveTrainMechanism = new DriveTrainMechanism(logger, testProvider, timer);
        driveTrainMechanism.setDriver(driver);
        driveTrainMechanism.stop();

        // from constructor:
        verify(leftMotor).setNeutralMode(eq(MotorNeutralMode.Brake));
        verify(leftMotor).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_LEFT_MASTER_INVERT_OUTPUT));
        verify(leftMotor).setInvertSensor(eq(HardwareConstants.DRIVETRAIN_LEFT_INVERT_SENSOR));
        verify(leftMotor).setSensorType(TalonXFeedbackDevice.IntegratedSensor);
        verify(leftMotor).setFeedbackFramePeriod(5);
        verify(leftMotor).setPIDFFramePeriod(5);
        verify(leftMotor).configureVelocityMeasurements(eq(10), eq(32));
        verify(leftMotor).setSelectedSlot(eq(0));
        verify(leftFollowerMotor1).setNeutralMode(eq(MotorNeutralMode.Brake));
        verify(leftFollowerMotor1).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_LEFT_FOLLOWER1_INVERT_OUTPUT));
        verify(leftFollowerMotor1).follow(eq(leftMotor));
        // verify(leftFollowerMotor2).setNeutralMode(eq(MotorNeutralMode.Brake));
        // verify(leftFollowerMotor2).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_LEFT_FOLLOWER2_INVERT_OUTPUT));
        // verify(leftFollowerMotor2).follow(eq(leftMotor));
        verify(rightMotor).setNeutralMode(eq(MotorNeutralMode.Brake));
        verify(rightMotor).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_RIGHT_MASTER_INVERT_OUTPUT));
        verify(rightMotor).setInvertSensor(eq(HardwareConstants.DRIVETRAIN_RIGHT_INVERT_SENSOR));
        verify(rightMotor).setSensorType(TalonXFeedbackDevice.IntegratedSensor);
        verify(rightMotor).setFeedbackFramePeriod(5);
        verify(rightMotor).setPIDFFramePeriod(5);
        verify(rightMotor).configureVelocityMeasurements(eq(10), eq(32));
        verify(rightMotor).setSelectedSlot(eq(0));
        verify(rightFollowerMotor1).setNeutralMode(eq(MotorNeutralMode.Brake));
        verify(rightFollowerMotor1).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_RIGHT_FOLLOWER1_INVERT_OUTPUT));
        verify(rightFollowerMotor1).follow(eq(rightMotor));
        // verify(rightFollowerMotor2).setNeutralMode(eq(MotorNeutralMode.Brake));
        // verify(rightFollowerMotor2).setInvertOutput(eq(HardwareConstants.DRIVETRAIN_RIGHT_FOLLOWER2_INVERT_OUTPUT));
        // verify(rightFollowerMotor2).follow(eq(rightMotor));

        // from setDriver:
        verify(leftMotor).setPIDF(
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KP),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KI),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KD),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KF),
            eq(0));
        verify(rightMotor).setPIDF(
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KP),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KI),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KD),
            eq(TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KF),
            eq(0));
        verify(leftMotor).setControlMode(eq(TalonSRXControlMode.Velocity));
        verify(rightMotor).setControlMode(eq(TalonSRXControlMode.Velocity));

        // from stop:
        verify(leftMotor).stop();
        verify(rightMotor).stop();
        verify(leftMotor).reset();
        verify(rightMotor).reset();

        verifyNoMoreInteractions(leftMotor);
        verifyNoMoreInteractions(rightMotor);
        verifyNoMoreInteractions(leftFollowerMotor1);
        verifyNoMoreInteractions(rightFollowerMotor1);
        // verifyNoMoreInteractions(leftFollowerMotor2);
        // verifyNoMoreInteractions(rightFollowerMotor2);
    }
}
