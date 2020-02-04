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

    private final IDoubleSolenoid kickerSolenoid;
    private final ITalonSRX genevaMotor;

    private final ICounter carouselCounter;

    private Driver driver;

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

    @Inject
    public PowerCellMechanism(IRobotProvider provider, IDashboardLogger logger)
    {
        this.logger = logger;

        this.intakeSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.POWERCELL_INTAKE_FORWARD_PCM, ElectronicsConstants.POWERCELL_INTAKE_REVERSE_PCM);
        this.kickerSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.POWERCELL_KICKER_FORWARD_PCM, ElectronicsConstants.POWERCELL_KICKER_REVERSE_PCM);
        this.lowerHood = provider.getDoubleSolenoid(ElectronicsConstants.POWERCELL_LOWER_HOOD_FORWARD_PCM, ElectronicsConstants.POWERCELL_LOWER_HOOD_REVERSE_PCM);
        this.upperHood = provider.getDoubleSolenoid(ElectronicsConstants.POWERCELL_UPPER_HOOD_FORWARD_PCM, ElectronicsConstants.POWERCELL_UPPER_HOOD_REVERSE_PCM);

        this.rollerMotorInner = provider.getTalonSRX(ElectronicsConstants.POWERCELL_INNER_ROLLER_MOTOR_CAN_ID);
        this.rollerMotorInner.setInvertOutput(HardwareConstants.POWERCELL_ROLLER_MOTOR_INNER_INVERT_OUTPUT);
        this.rollerMotorInner.setControlMode(TalonSRXControlMode.PercentOutput);
        this.rollerMotorInner.setNeutralMode(MotorNeutralMode.Brake);

        this.rollerMotorOuter = provider.getTalonSRX(ElectronicsConstants.POWERCELL_OUTER_ROLLER_MOTOR_CAN_ID);
        this.rollerMotorOuter.setInvertOutput(HardwareConstants.POWERCELL_ROLLER_MOTOR_OUTER_INVERT_OUTPUT);
        this.rollerMotorOuter.setControlMode(TalonSRXControlMode.PercentOutput);
        this.rollerMotorOuter.setNeutralMode(MotorNeutralMode.Brake);

        this.flyWheel = provider.getTalonSRX(ElectronicsConstants.POWERCELL_FLYWHEEL_MASTER_CAN_ID);
        this.flyWheel.setInvertOutput(HardwareConstants.POWERCELL_FLYWHEEL_MASTER_INVERT_OUTPUT);
        this.flyWheel.setInvertSensor(HardwareConstants.POWERCELL_FLYWHEEL_MASTER_INVERT_SENSOR);
        this.flyWheel.setNeutralMode(MotorNeutralMode.Coast);
        this.flyWheel.setControlMode(TalonSRXControlMode.Velocity);
        this.flyWheel.setPIDF(
            TuningConstants.POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KP, 
            TuningConstants.POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KI, 
            TuningConstants.POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KD, 
            TuningConstants.POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KF, 
            PowerCellMechanism.slotId);
        this.flyWheel.configureVelocityMeasurements(TuningConstants.POWERCELL_FLYWHEEL_VELOCITY_PERIOD, TuningConstants.POWERCELL_FLYWHEEL_VELOCITY_WINDOWSIZE);
        this.flyWheel.setVoltageCompensation(TuningConstants.POWERCELL_FLYWHEEL_MASTER_VELOCITY_VOLTAGE_COMPENSATION_ENABLED, TuningConstants.POWERCELL_FLYWHEEL_MASTER_VELOCITY_VOLTAGE_COMPENSATION_MAXVOLTAGE);

        ITalonSRX flyWheelFollower = provider.getTalonSRX(ElectronicsConstants.POWERCELL_FLYWHEEL_FOLLOWER_CAN_ID);
        flyWheelFollower.setNeutralMode(MotorNeutralMode.Coast);
        flyWheelFollower.follow(this.flyWheel);
        flyWheelFollower.setInvertOutput(HardwareConstants.POWERCELL_FLYWHEEL_FOLLOWER_INVERT_OUTPUT);
        flyWheelFollower.setVoltageCompensation(TuningConstants.POWERCELL_FLYWHEEL_FOLLOWER_VELOCITY_VOLTAGE_COMPENSATION_ENABLED, TuningConstants.POWERCELL_FLYWHEEL_FOLLOWER_VELOCITY_VOLTAGE_COMPENSATION_MAXVOLTAGE);

        this.turret = provider.getTalonSRX(ElectronicsConstants.POWERCELL_TURRET_MOTOR_CAN_ID);
        this.turret.setInvertOutput(HardwareConstants.POWERCELL_TURRET_INVERT_OUTPUT);
        this.turret.setInvertSensor(HardwareConstants.POWERCELL_TURRET_INVERT_SENSOR);
        this.turret.setNeutralMode(MotorNeutralMode.Brake);
        this.turret.setControlMode(TalonSRXControlMode.Position);
        this.turret.setPIDF(
            TuningConstants.POWERCELL_TURRET_POSITION_PID_KP, 
            TuningConstants.POWERCELL_TURRET_POSITION_PID_KI, 
            TuningConstants.POWERCELL_TURRET_POSITION_PID_KD, 
            TuningConstants.POWERCELL_TURRET_POSITION_PID_KF, 
            PowerCellMechanism.slotId);

        this.genevaMotor = provider.getTalonSRX(ElectronicsConstants.POWERCELL_GENEVA_MOTOR_CAN_ID);
        this.genevaMotor.setInvertOutput(HardwareConstants.POWERCELL_GENEVA_MOTOR_INVERT_OUTPUT);
        this.genevaMotor.setControlMode(TalonSRXControlMode.PercentOutput);
        this.genevaMotor.setNeutralMode(MotorNeutralMode.Brake);

        this.carouselCounter = provider.getCounter(ElectronicsConstants.POWERCELL_CAROUSEL_COUNTER_DIO);
    }

    @Override
    public void readSensors()
    {
        this.turretPosition = this.turret.getPosition();
        this.turretVelocity = this.turret.getVelocity();
        this.turretError = this.turret.getError();

        this.flywheelPosition = this.flyWheel.getPosition();
        this.flywheelVelocity = this.flyWheel.getVelocity();
        this.flywheelError = this.flyWheel.getError();

        this.carouselCount = this.carouselCounter.get();

        this.logger.logNumber(PowerCellMechanism.logName, "turretVelocity", this.turretVelocity);
        this.logger.logNumber(PowerCellMechanism.logName, "turretPosition", this.turretPosition);
        this.logger.logNumber(PowerCellMechanism.logName, "turretError", this.turretError);
        this.logger.logNumber(PowerCellMechanism.logName, "flywheelVelocity", this.flywheelVelocity);
        this.logger.logNumber(PowerCellMechanism.logName, "flywheelPosition", this.flywheelPosition);
        this.logger.logNumber(PowerCellMechanism.logName, "flywheelError", this.flywheelError);
        this.logger.logInteger(PowerCellMechanism.logName, "carouselCount", this.carouselCount);
    }

    @Override
    public void update()
    {
        if (this.driver.getDigital(DigitalOperation.PowerCellUpperHoodExtend))
        {
            this.upperHood.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.PowerCellUpperHoodRetract))
        {
            this.upperHood.set(DoubleSolenoidValue.Reverse);
        }

        if (this.driver.getDigital(DigitalOperation.PowerCellLowerHoodExtend))
        {
            this.lowerHood.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.PowerCellLowerHoodRetract))
        {
            this.lowerHood.set(DoubleSolenoidValue.Reverse);
        }

        if (this.driver.getDigital(DigitalOperation.PowerCellKick))
        {
            this.kickerSolenoid.set(DoubleSolenoidValue.Forward);
        }
        else if(!(driver.getDigital(DigitalOperation.PowerCellKick)))
        {
            this.kickerSolenoid.set(DoubleSolenoidValue.Reverse);
        }

        if (this.driver.getDigital(DigitalOperation.PowerCellIntakeExtend))
        {
            this.intakeSolenoid.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.PowerCellIntakeRetract))
        {
            this.intakeSolenoid.set(DoubleSolenoidValue.Reverse);
        }

        if (this.driver.getDigital(DigitalOperation.PowerCellIntake))
        {
            this.rollerMotorInner.set(TuningConstants.POWERCELL_INNER_ROLLER_MOTOR_INTAKE_POWER);
            this.rollerMotorOuter.set(TuningConstants.POWERCELL_OUTER_ROLLER_MOTOR_INTAKE_POWER);
        }
        else if (this.driver.getDigital(DigitalOperation.PowerCellOuttake))
        {
            this.rollerMotorInner.set(TuningConstants.POWERCELL_INNER_ROLLER_MOTOR_OUTTAKE_POWER);
            this.rollerMotorOuter.set(TuningConstants.POWERCELL_OUTER_ROLLER_MOTOR_OUTTAKE_POWER);
        }

        double flyWheelspeed = this.driver.getAnalog(AnalogOperation.PowerCellFlywheelVelocity);
        this.flyWheel.set(flyWheelspeed);

        double turretAnalogPosition = this.driver.getAnalog(AnalogOperation.PowerCellTurretPosition);
        this.turret.set(turretAnalogPosition);

        double genevaPower = this.driver.getAnalog(AnalogOperation.PowerCellGenevaPower);
        this.genevaMotor.set(genevaPower);
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

    public double getTurretPosition()
    {
        return this.turretPosition;
    }

    public double getFlywheelVelocity()
    {
        return this.flywheelVelocity;
    }

    public int getCarouselCount()
    {
        return this.carouselCount;
    }
}