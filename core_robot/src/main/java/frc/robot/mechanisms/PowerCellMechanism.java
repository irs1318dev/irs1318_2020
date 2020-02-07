package frc.robot.mechanisms;

import frc.robot.*;
import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.*;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PowerCellMechanism implements IMechanism
{
    private static final String logName = "pc";
    private static final int slotId = 0;

    private final IDashboardLogger logger;

    private final IDoubleSolenoid intakeSolenoid;

    private final ITalonSRX rollerMotorInner;
    private final ITalonSRX rollerMotorOuter;

    private Driver driver;

    private final IDoubleSolenoid kickerSolenoid;
    private final ITalonSRX genevaMotor;
    //sensor?

    private final ICounter carouselCounter;

    private double flywheelPosition;
    private double flywheelVelocity;
    private double turretPosition;
    private double turretVelocity;
    private double flywheelError;
    private double turretError;
    private int carouselCount;

    private final IDoubleSolenoid lowerHood;
    private final IDoubleSolenoid upperHood;
    private final ITalonSRX flyWheel;
    private final ITalonSRX turret;

    private boolean[] indexes;

    @Inject
    public PowerCellMechanism(IRobotProvider provider, IDashboardLogger logger)
    {
        this.logger = logger;

        this.indexes = new boolean[] {false, false, false, false, false};

        this.intakeSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.INTAKE_FORWARD_PCM, ElectronicsConstants.INTAKE_REVERSE_PCM);
        this.kickerSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.KICKER_FORWARD_PCM, ElectronicsConstants.KICKER_REVERSE_PCM);
        this.lowerHood = provider.getDoubleSolenoid(ElectronicsConstants.LOWER_HOOD_FORWARD_PCM, ElectronicsConstants.LOWER_HOOD_REVERSE_PCM);
        this.upperHood = provider.getDoubleSolenoid(ElectronicsConstants.UPPER_HOOD_FORWARD_PCM, ElectronicsConstants.UPPER_HOOD_REVERSE_PCM);

        this.rollerMotorInner = provider.getTalonSRX(ElectronicsConstants.ROLLERMOTOR_INNER_CAN_ID);
        this.rollerMotorInner.setInvertOutput(TuningConstants.ROLLER_MOTOR_INNER_INVERT_OUTPUT);
        this.rollerMotorInner.setControlMode(TalonSRXControlMode.PercentOutput);
        this.rollerMotorInner.setNeutralMode(MotorNeutralMode.Brake);

        this.rollerMotorOuter = provider.getTalonSRX(ElectronicsConstants.ROLLERMOTOR_OUTER_CAN_ID);
        this.rollerMotorOuter.setInvertOutput(TuningConstants.ROLLER_MOTOR_OUTER_INVERT_OUTPUT);
        this.rollerMotorOuter.setControlMode(TalonSRXControlMode.PercentOutput);
        this.rollerMotorOuter.setNeutralMode(MotorNeutralMode.Brake);

        this.flyWheel = provider.getTalonSRX(ElectronicsConstants.FLYWHEEL_MASTER_CAN_ID);
        this.flyWheel.setInvertOutput(TuningConstants.FLYWHEEL_MASTER_INVERT_OUTPUT);
        this.flyWheel.setInvertSensor(TuningConstants.FLYWHEEL_MASTER_INVERT_SENSOR);
        this.flyWheel.setNeutralMode(MotorNeutralMode.Coast);
        this.flyWheel.setControlMode(TalonSRXControlMode.Velocity);
        this.flyWheel.setPIDF(
            TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KP, 
            TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KI, 
            TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KD, 
            TuningConstants.FLYWHEEL_ONE_VELOCITY_PID_KF, 
            PowerCellMechanism.slotId);
        this.flyWheel.configureVelocityMeasurements(TuningConstants.FLYWHEEL_VELOCITY_PERIOD, TuningConstants.FLYWHEEL_VELOCITY_WINDOWSIZE);
        this.flyWheel.setVoltageCompensation(TuningConstants.FLYWHEEL_MASTER_VELOCITY_VOLTAGE_COMPENSATION_ENABLED, TuningConstants.FLYWHEEL_MASTER_VELOCITY_VOLTAGE_COMPENSATION_MAXVOLTAGE);

        ITalonSRX flyWheelFollower = provider.getTalonSRX(ElectronicsConstants.FLYWHEEL_FOLLOWER_CAN_ID);
        flyWheelFollower.setNeutralMode(MotorNeutralMode.Coast);
        flyWheelFollower.follow(this.flyWheel);
        flyWheelFollower.setInvertOutput(TuningConstants.FLYWHEEL_FOLLOWER_INVERT_OUTPUT);
        flyWheelFollower.setVoltageCompensation(TuningConstants.FLYWHEEL_FOLLOWER_VELOCITY_VOLTAGE_COMPENSATION_ENABLED, TuningConstants.FLYWHEEL_FOLLOWER_VELOCITY_VOLTAGE_COMPENSATION_MAXVOLTAGE);

        this.turret = provider.getTalonSRX(ElectronicsConstants.TURRET_CAN_ID);
        this.turret.setInvertOutput(TuningConstants.TURRET_INVERT_OUTPUT);
        this.turret.setInvertSensor(TuningConstants.TURRET_INVERT_SENSOR);
        this.turret.setNeutralMode(MotorNeutralMode.Brake);
        this.turret.setControlMode(TalonSRXControlMode.Position);
        this.turret.setPIDF(
            TuningConstants.TURRET_POSITION_PID_KP, 
            TuningConstants.TURRET_POSITION_PID_KI, 
            TuningConstants.TURRET_POSITION_PID_KD, 
            TuningConstants.TURRET_POSITION_PID_KF, 
            PowerCellMechanism.slotId);

        this.genevaMotor = provider.getTalonSRX(ElectronicsConstants.GENEVAMOTOR_CAN_ID);
        this.genevaMotor.setInvertOutput(TuningConstants.GENEVA_INVERT_OUTPUT);
        this.genevaMotor.setControlMode(TalonSRXControlMode.PercentOutput);
        this.genevaMotor.setNeutralMode(MotorNeutralMode.Brake);

        this.carouselCounter = provider.getCounter(ElectronicsConstants.CAROUSEL_COUNTER_DIO);
    }

    @Override
    public void readSensors()
    {
        
        this.turretPosition = turret.getPosition();
        this.turretVelocity = turret.getVelocity();
        this.flywheelPosition = flyWheel.getPosition();
        this.flywheelVelocity = flyWheel.getVelocity();
        this.turretError = turret.getError();
        this.flywheelError = flyWheel.getError();
        this.carouselCount = carouselCounter.get();


        this.logger.logNumber(PowerCellMechanism.logName, "turret velocity", turretVelocity);
        this.logger.logNumber(PowerCellMechanism.logName, "turret position", turretPosition);
        this.logger.logNumber(PowerCellMechanism.logName, "flywheel velocity", flywheelVelocity);
        this.logger.logNumber(PowerCellMechanism.logName, "flywheel position", flywheelPosition);
        this.logger.logNumber(PowerCellMechanism.logName, "flywheel error", flywheelError);
        this.logger.logNumber(PowerCellMechanism.logName, "turret error", turretError);
        this.logger.logInteger(PowerCellMechanism.logName, "carousel count", carouselCount);
    }

    @Override
    public void update()
    {
        if(driver.getDigital(DigitalOperation.PowerCellUpperHoodExtend)){
            this.upperHood.set(DoubleSolenoidValue.Forward);
        }
        else if(driver.getDigital(DigitalOperation.PowerCellUpperHoodRetract)){
            this.upperHood.set(DoubleSolenoidValue.Reverse);
        }
        if(driver.getDigital(DigitalOperation.PowerCellLowerHoodExtend)){
            this.lowerHood.set(DoubleSolenoidValue.Forward);
        }
        else if(driver.getDigital(DigitalOperation.PowerCellLowerHoodRetract)){
            this.lowerHood.set(DoubleSolenoidValue.Reverse);
        }
        if(driver.getDigital(DigitalOperation.PowerCellKick)){
            this.kickerSolenoid.set(DoubleSolenoidValue.Forward);
        }
        else if(!(driver.getDigital(DigitalOperation.PowerCellKick))){
            this.kickerSolenoid.set(DoubleSolenoidValue.Reverse);
        }
        if(driver.getDigital(DigitalOperation.PowerCellIntakeExtend)){
            this.intakeSolenoid.set(DoubleSolenoidValue.Forward);
        }
        else if(driver.getDigital(DigitalOperation.PowerCellIntakeRetract)){
            this.intakeSolenoid.set(DoubleSolenoidValue.Reverse);
        }
        if(driver.getDigital(DigitalOperation.PowerCellIntake)){
            this.rollerMotorInner.set(TuningConstants.ROLLER_MOTOR_INNER_POWER_LEVEL_INTAKE);
            this.rollerMotorOuter.set(TuningConstants.ROLLER_MOTOR_OUTER_POWER_LEVEL_INTAKE);
        }
        else if(driver.getDigital(DigitalOperation.PowerCellOuttake)){
            this.rollerMotorInner.set(TuningConstants.ROLLER_MOTOR_INNER_POWER_LEVEL_OUTTAKE);
            this.rollerMotorOuter.set(TuningConstants.ROLLER_MOTOR_OUTER_POWER_LEVEL_OUTTAKE);
        }
        double flyWheelspeed = driver.getAnalog(AnalogOperation.PowerCellFlywheelVelocity);
        this.flyWheel.set(flyWheelspeed);
        double turretAnalogPosition = driver.getAnalog(AnalogOperation.PowerCellTurretPosition);
        this.turret.set(turretAnalogPosition);
    }

    @Override
    public void stop()
    {
        this.genevaMotor.set(0.0);
        this.rollerMotorInner.set(0.0);
        this.rollerMotorOuter.set(0.0);
        this.lowerHood.set(DoubleSolenoidValue.Off);
        this.upperHood.set(DoubleSolenoidValue.Off);
        this.kickerSolenoid.set(DoubleSolenoidValue.Off);
        this.intakeSolenoid.set(DoubleSolenoidValue.Off);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
    public int getcurrentSlot(){

    }

    public boolean isTherePowerCell(int index){
        
    }
    // get functions...

}