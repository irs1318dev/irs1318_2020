package frc.robot.mechanisms;

import frc.robot.*;
import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.common.Driver;
import frc.robot.common.robotprovider.IDashboardLogger;
import frc.robot.common.robotprovider.IColorMatch;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.*;

public class PowerCellMechanism implements IMechanism {
    //1 Double solenoids, 2 motors (talon srx)
    //1 geneva mechanism (775) pneumatic kicker (piston - double Solenoid) - timed with rotation (ratio)
    private static final int slotId = 0;
    //intake stuff

    private final IDoubleSolenoid intakeSolenoid;

    //inverse follow?
    private final ITalonSRX rollerMotorInner;
    private final ITalonSRX rollerMotorOuter;
    //private final ITalonSRX rollerMotorOuter;

    //hopper stuff
    
    private final IDoubleSolenoid kickerSolenoid;

    private final ITalonSRX genevaMotor;
    //sensor?

    //Shooter

    private final IDoubleSolenoid lowerHood;
    private final IDoubleSolenoid upperHood;
    
    //inverse follow
    private final ITalonSRX flyWheel1;
    private final ITalonSRX turret;

    public PowerCellMechanism(IRobotProvider provider)
    {
        this.intakeSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.INTAKE_FORWARD_PCM, ElectronicsConstants.INTAKE_REVERSE_PCM);
        this.kickerSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.KICKER_FORWARD_PCM, ElectronicsConstants.KICKER_REVERSE_PCM);
        this.lowerHood = provider.getDoubleSolenoid(ElectronicsConstants.LOWER_HOOD_FORWARD_PCM, ElectronicsConstants.LOWER_HOOD_REVERSE_PCM);
        this.upperHood = provider.getDoubleSolenoid(ElectronicsConstants.UPPER_HOOD_FORWARD_PCM, ElectronicsConstants.UPPER_HOOD_REVERSE_PCM);

        this.rollerMotorInner = provider.getTalonSRX(ElectronicsConstants.ROLLERMOTOR_INNER_CAN_ID);
        this.rollerMotorOuter = provider.getTalonSRX(ElectronicsConstants.ROLLERMOTOR_OUTER_CAN_ID);
        this.rollerMotorInner.setControlMode(TalonSRXControlMode.PercentOutput);
        this.rollerMotorInner.setNeutralMode(MotorNeutralMode.Brake);
        this.rollerMotorOuter.setControlMode(TalonSRXControlMode.PercentOutput);
        this.rollerMotorOuter.setNeutralMode(MotorNeutralMode.Brake);

        this.flyWheel1 = provider.getTalonSRX(ElectronicsConstants.FLYWHEEL_MASTER_CAN_ID);
        ITalonSRX flyWheel2 = provider.getTalonSRX(ElectronicsConstants.FLYWHEEL_FOLLOWER_CAN_ID);
        this.flyWheel1.setNeutralMode(MotorNeutralMode.Coast);
        this.flyWheel1.setControlMode(TalonSRXControlMode.Velocity);
        this.flyWheel1.setPIDF(TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KP, 
                            TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KI, 
                            TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KD, 
                            TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KF, 
                            PowerCellMechanism.slotId);
        flyWheel2.follow(this.flyWheel1);

        this.turret = provider.getTalonSRX(ElectronicsConstants.TURRET_CAN_ID);
        this.turret.setNeutralMode(MotorNeutralMode.Brake);
        this.turret.setControlMode(TalonSRXControlMode.Position);
        this.turret.setPIDF(TuningConstants.TURRET_POSITION_PID_KP, 
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