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
    private static final int slotId = 0;

    private final IDoubleSolenoid climberExtendSolenoid;
    private final IDoubleSolenoid climberGrabSolenoid;

    private final ITalonSRX winchMotorMaster;
    
    @Inject
    public ClimberMechanism(IRobotProvider provider)
    {
        this.climberExtendSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.CLIMBER_EXTEND_FORWARD_CAN_ID, ElectronicsConstants.CLIMBER_EXTEND_REVERSE_CAN_ID);
        this.climberGrabSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.CLIMBER_GRAB_FORWARD_CAN_ID, ElectronicsConstants.CLIMBER_GRAB_REVERSE_CAN_ID);

        this.winchMotorMaster = provider.getTalonSRX(ElectronicsConstants.WINCH_MASTER_CAN_ID);
        this.winchMotorMaster.setControlMode(TalonSRXControlMode.Position);
        this.winchMotorMaster.setNeutralMode(MotorNeutralMode.Brake);
        this.winchMotorMaster.setPIDF(
            TuningConstants.WINCH_POSITION_PID_KP, 
            TuningConstants.WINCH_POSITION_PID_KI, 
            TuningConstants.WINCH_POSITION_PID_KD, 
            TuningConstants.WINCH_POSITION_PID_KF, 
            ClimberMechanism.slotId);

        ITalonSRX winchMotorFollower = provider.getTalonSRX(ElectronicsConstants.WINCH_FOLLOWER_CAN_ID);
        winchMotorFollower.setNeutralMode(MotorNeutralMode.Brake);
        winchMotorFollower.follow(this.winchMotorMaster);
    }

    @Override
    public void readSensors()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void stop()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void setDriver(Driver driver)
    {
        // TODO Auto-generated method stub
    }
}