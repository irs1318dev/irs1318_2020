package frc.robot.mechanisms;

import frc.robot.*;
import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.*;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ClimberMechanism implements IMechanism
{
    private final IDoubleSolenoid climberExtendSolenoid;
    private final IDoubleSolenoid climberGrabSolenoid;

    private final ITalonSRX winchMotorMaster;

    private Driver driver;

    private boolean isExtended;
    private boolean isReleased;

    @Inject
    public ClimberMechanism(IRobotProvider provider)
    {
        this.climberExtendSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.CLIMBER_EXTEND_FORWARD_CAN_ID, ElectronicsConstants.CLIMBER_EXTEND_REVERSE_CAN_ID);
        this.climberGrabSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.CLIMBER_GRAB_FORWARD_CAN_ID, ElectronicsConstants.CLIMBER_GRAB_REVERSE_CAN_ID);

        this.winchMotorMaster = provider.getTalonSRX(ElectronicsConstants.WINCH_MASTER_CAN_ID);
        this.winchMotorMaster.setInvertOutput(TuningConstants.WINCH_MASTER_INVERT_OUTPUT);
        this.winchMotorMaster.setControlMode(TalonSRXControlMode.PercentOutput);
        this.winchMotorMaster.setNeutralMode(MotorNeutralMode.Brake);

        ITalonSRX winchMotorFollower = provider.getTalonSRX(ElectronicsConstants.WINCH_FOLLOWER_CAN_ID);
        winchMotorFollower.setInvertOutput(TuningConstants.WINCH_FOLLOWER_INVERT_OUTPUT);
        winchMotorFollower.setNeutralMode(MotorNeutralMode.Brake);
        winchMotorFollower.follow(this.winchMotorMaster);

        this.isExtended = false;
        this.isReleased = false;
    }

    @Override
    public void readSensors()
    {
    }

    @Override
    public void update()
    {
        if (this.driver.getDigital(DigitalOperation.ClimberExtend)) 
        {
            this.isExtended = true;
            this.climberExtendSolenoid.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.ClimberRetract))
        {
            this.isExtended = false;
            this.climberExtendSolenoid.set(DoubleSolenoidValue.Reverse);
        }

        if (this.driver.getDigital(DigitalOperation.ClimberHookLock)) 
        {
            this.climberGrabSolenoid.set(DoubleSolenoidValue.Reverse);
        }
        else if (this.isExtended && this.driver.getDigital(DigitalOperation.ClimberHookRelease))
        {
            this.isReleased = true;
            this.climberGrabSolenoid.set(DoubleSolenoidValue.Forward);
        }

        if (this.isExtended) 
        {
            double speed = this.driver.getAnalog(AnalogOperation.ClimberWinch);
            this.winchMotorMaster.set(speed);
        }
    }

    @Override
    public void stop()
    {
        this.climberExtendSolenoid.set(DoubleSolenoidValue.Off);
        this.climberGrabSolenoid.set(DoubleSolenoidValue.Off);
        this.winchMotorMaster.set(0.0);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}