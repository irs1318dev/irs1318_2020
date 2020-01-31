package frc.robot.mechanisms;

import frc.robot.*;
import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.common.Driver;
import frc.robot.common.robotprovider.IDashboardLogger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.robot.driver.*;
import frc.robot.driver.common.*;

public class PowerCellMechanism implements IMechanism
{
    private static final int slotId = 0;

    private final IDoubleSolenoid intakeSolenoid;

    private final ITalonSRX rollerMotorInner;
    private final ITalonSRX rollerMotorOuter;

    private final IDoubleSolenoid kickerSolenoid;
    private final ITalonSRX genevaMotor;
    //sensor?

    private final IDoubleSolenoid lowerHood;
    private final IDoubleSolenoid upperHood;
    private final ITalonSRX flyWheel;
    private final ITalonSRX turret;

    public PowerCellMechanism(IRobotProvider provider)
    {
        this.intakeSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.INTAKE_FORWARD_PCM, ElectronicsConstants.INTAKE_REVERSE_PCM);
        this.kickerSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.KICKER_FORWARD_PCM, ElectronicsConstants.KICKER_REVERSE_PCM);
        this.lowerHood = provider.getDoubleSolenoid(ElectronicsConstants.LOWER_HOOD_FORWARD_PCM, ElectronicsConstants.LOWER_HOOD_REVERSE_PCM);
        this.upperHood = provider.getDoubleSolenoid(ElectronicsConstants.UPPER_HOOD_FORWARD_PCM, ElectronicsConstants.UPPER_HOOD_REVERSE_PCM);

        this.rollerMotorInner = provider.getTalonSRX(ElectronicsConstants.ROLLERMOTOR_INNER_CAN_ID);
        this.rollerMotorInner.setControlMode(TalonSRXControlMode.PercentOutput);
        this.rollerMotorInner.setNeutralMode(MotorNeutralMode.Brake);

        this.rollerMotorOuter = provider.getTalonSRX(ElectronicsConstants.ROLLERMOTOR_OUTER_CAN_ID);
        this.rollerMotorOuter.setControlMode(TalonSRXControlMode.PercentOutput);
        this.rollerMotorOuter.setNeutralMode(MotorNeutralMode.Brake);

        this.flyWheel = provider.getTalonSRX(ElectronicsConstants.FLYWHEEL_MASTER_CAN_ID);
        this.flyWheel.setNeutralMode(MotorNeutralMode.Coast);
        this.flyWheel.setControlMode(TalonSRXControlMode.Velocity);
        this.flyWheel.setPIDF(
            TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KP, 
            TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KI, 
            TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KD, 
            TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KF, 
            PowerCellMechanism.slotId);

        ITalonSRX flyWheelFollower = provider.getTalonSRX(ElectronicsConstants.FLYWHEEL_FOLLOWER_CAN_ID);
        flyWheelFollower.setNeutralMode(MotorNeutralMode.Coast);
        flyWheelFollower.follow(this.flyWheel);

        this.turret = provider.getTalonSRX(ElectronicsConstants.TURRET_CAN_ID);
        this.turret.setNeutralMode(MotorNeutralMode.Brake);
        this.turret.setControlMode(TalonSRXControlMode.Position);
        this.turret.setPIDF(
            TuningConstants.TURRET_POSITION_PID_KP, 
            TuningConstants.TURRET_POSITION_PID_KI, 
            TuningConstants.TURRET_POSITION_PID_KD, 
            TuningConstants.TURRET_POSITION_PID_KF, 
            PowerCellMechanism.slotId);

        this.genevaMotor = provider.getTalonSRX(ElectronicsConstants.GENEVAMOTOR_CAN_ID);
        this.genevaMotor.setControlMode(TalonSRXControlMode.PercentOutput);
        this.genevaMotor.setNeutralMode(MotorNeutralMode.Brake);

    }
    @Override
    public void readSensors() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDriver(Driver driver) {
        // TODO Auto-generated method stub

    }

    //hopper stuff
    

    
}