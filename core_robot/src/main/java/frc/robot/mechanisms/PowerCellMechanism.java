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
    private final ISparkMax rollerMotorInner;
    private final ISparkMax rollerMotorOuter;

    // private final IDoubleSolenoid outerHood;
    // private final IDoubleSolenoid innerHood;
    // private final IDoubleSolenoid kickerSolenoid;
    // private final ITalonSRX flyWheel;
    // private final ITalonSRX turret;

    // private final ITalonSRX genevaMotor;
    private final ITimer timer;

    private double lastIntakeTime;

    // private final ICounter carouselCounter;
    // private final IAnalogInput throughBeamSensor;

    private Driver driver;

    // private double flywheelPosition;
    // private double flywheelVelocity;
    // private double flywheelError;
    // private double turretPosition;
    // private double turretVelocity;
    // private double turretError;
    // private int carouselCount;

    private double startingTurretOffsetAngle;

    private boolean[] hasPowerCell;
    private int currentCarouselIndex;

    private CarouselState state;
    private int previousIndex;

    @Inject
    public PowerCellMechanism(IRobotProvider provider, IDashboardLogger logger, ITimer timer)
    {
        this.logger = logger;

        // this.throughBeamSensor = provider.getAnalogInput(ElectronicsConstants.POWERCELL_THROUGHBEAM_CHANNEL);

        this.intakeSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.POWERCELL_INTAKE_FORWARD_PCM, ElectronicsConstants.POWERCELL_INTAKE_REVERSE_PCM);
        // this.kickerSolenoid = provider.getDoubleSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.POWERCELL_KICKER_FORWARD_PCM, ElectronicsConstants.POWERCELL_KICKER_REVERSE_PCM);
        // this.outerHood = provider.getDoubleSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.POWERCELL_OUTER_HOOD_FORWARD_PCM, ElectronicsConstants.POWERCELL_OUTER_HOOD_REVERSE_PCM);
        // this.innerHood = provider.getDoubleSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.POWERCELL_INNER_HOOD_FORWARD_PCM, ElectronicsConstants.POWERCELL_INNER_HOOD_REVERSE_PCM);

        this.rollerMotorInner = provider.getSparkMax(ElectronicsConstants.POWERCELL_INNER_ROLLER_MOTOR_CAN_ID, SparkMaxMotorType.Brushed);
        this.rollerMotorInner.setInvertOutput(HardwareConstants.POWERCELL_ROLLER_MOTOR_INNER_INVERT_OUTPUT);
        this.rollerMotorInner.setControlMode(SparkMaxControlMode.PercentOutput);
        this.rollerMotorInner.setNeutralMode(MotorNeutralMode.Brake);

        this.rollerMotorOuter = provider.getSparkMax(ElectronicsConstants.POWERCELL_OUTER_ROLLER_MOTOR_CAN_ID, SparkMaxMotorType.Brushed);
        this.rollerMotorOuter.setInvertOutput(HardwareConstants.POWERCELL_ROLLER_MOTOR_OUTER_INVERT_OUTPUT);
        this.rollerMotorOuter.setControlMode(SparkMaxControlMode.PercentOutput);
        this.rollerMotorOuter.setNeutralMode(MotorNeutralMode.Brake);

        // this.flyWheel = provider.getTalonSRX(ElectronicsConstants.POWERCELL_FLYWHEEL_MASTER_CAN_ID);
        // this.flyWheel.setInvertOutput(HardwareConstants.POWERCELL_FLYWHEEL_MASTER_INVERT_OUTPUT);
        // this.flyWheel.setInvertSensor(HardwareConstants.POWERCELL_FLYWHEEL_MASTER_INVERT_SENSOR);
        // this.flyWheel.setNeutralMode(MotorNeutralMode.Coast);
        // this.flyWheel.setSensorType(TalonXFeedbackDevice.QuadEncoder);
        // this.flyWheel.setPosition(0);
        // this.flyWheel.setControlMode(TalonSRXControlMode.Velocity);
        // this.flyWheel.setPIDF(
        //     TuningConstants.POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KP,
        //     TuningConstants.POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KI,
        //     TuningConstants.POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KD,
        //     TuningConstants.POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KF,
        //     PowerCellMechanism.slotId);
        // this.flyWheel.configureVelocityMeasurements(TuningConstants.POWERCELL_FLYWHEEL_VELOCITY_PERIOD, TuningConstants.POWERCELL_FLYWHEEL_VELOCITY_WINDOWSIZE);
        // this.flyWheel.setVoltageCompensation(TuningConstants.POWERCELL_FLYWHEEL_MASTER_VELOCITY_VOLTAGE_COMPENSATION_ENABLED, TuningConstants.POWERCELL_FLYWHEEL_MASTER_VELOCITY_VOLTAGE_COMPENSATION_MAXVOLTAGE);

        // ITalonSRX flyWheelFollower = provider.getTalonSRX(ElectronicsConstants.POWERCELL_FLYWHEEL_FOLLOWER_CAN_ID);
        // flyWheelFollower.setNeutralMode(MotorNeutralMode.Coast);
        // flyWheelFollower.follow(this.flyWheel);
        // flyWheelFollower.setInvertOutput(HardwareConstants.POWERCELL_FLYWHEEL_FOLLOWER_INVERT_OUTPUT);
        // flyWheelFollower.setVoltageCompensation(TuningConstants.POWERCELL_FLYWHEEL_FOLLOWER_VELOCITY_VOLTAGE_COMPENSATION_ENABLED, TuningConstants.POWERCELL_FLYWHEEL_FOLLOWER_VELOCITY_VOLTAGE_COMPENSATION_MAXVOLTAGE);

        // this.turret = provider.getTalonSRX(ElectronicsConstants.POWERCELL_TURRET_MOTOR_CAN_ID);
        // this.turret.setInvertOutput(HardwareConstants.POWERCELL_TURRET_INVERT_OUTPUT);
        // this.turret.setInvertSensor(HardwareConstants.POWERCELL_TURRET_INVERT_SENSOR);
        // this.turret.setNeutralMode(MotorNeutralMode.Brake);
        // this.turret.setSensorType(TalonXFeedbackDevice.QuadEncoder);
        // this.turret.setPosition(0);
        // this.turret.setControlMode(TalonSRXControlMode.Position);
        // this.turret.setPIDF(
        //     TuningConstants.POWERCELL_TURRET_POSITION_PID_KP,
        //     TuningConstants.POWERCELL_TURRET_POSITION_PID_KI,
        //     TuningConstants.POWERCELL_TURRET_POSITION_PID_KD,
        //     TuningConstants.POWERCELL_TURRET_POSITION_PID_KF,
        //     PowerCellMechanism.slotId);
        // this.turret.setForwardLimitSwitch(TuningConstants.POWERCELL_TURRET_FORWARD_LIMIT_SWITCH_ENABLED, TuningConstants.POWERCELL_TURRET_FORWARD_LIMIT_SWITCH_NORMALLY_OPEN);
        // this.turret.setReverseLimitSwitch(TuningConstants.POWERCELL_TURRET_REVERSE_LIMIT_SWITCH_ENABLED, TuningConstants.POWERCELL_TURRET_REVERSE_LIMIT_SWITCH_NORMALLY_OPEN);

        // this.genevaMotor = provider.getTalonSRX(ElectronicsConstants.POWERCELL_GENEVA_MOTOR_CAN_ID);
        // this.genevaMotor.setInvertOutput(HardwareConstants.POWERCELL_GENEVA_MOTOR_INVERT_OUTPUT);
        // this.genevaMotor.setControlMode(TalonSRXControlMode.PercentOutput);
        // this.genevaMotor.setNeutralMode(MotorNeutralMode.Brake);

        this.timer = timer;
        this.lastIntakeTime = this.timer.get();

        // this.carouselCounter = provider.getCounter(ElectronicsConstants.POWERCELL_CAROUSEL_COUNTER_DIO);

        this.state = CarouselState.Stationary;
        this.previousIndex = 0;

        this.hasPowerCell = new boolean[5];
        this.currentCarouselIndex = 0;
    }

    @Override
    public void readSensors()
    {
        // boolean throughBeamBroken = false;
        // if (this.throughBeamSensor.getVoltage() > TuningConstants.POWERCELL_TROUGHBEAM_CUTOFF)
        // {
        //     throughBeamBroken = true;
        // }

        // this.turretPosition = this.turret.getPosition();
        // this.turretVelocity = this.turret.getVelocity();
        // this.turretError = this.turret.getError();

        // this.flywheelPosition = this.flyWheel.getPosition();
        // this.flywheelVelocity = this.flyWheel.getVelocity();
        // this.flywheelError = this.flyWheel.getError();

        // int newCarouselCount = this.carouselCounter.get();
        // if (newCarouselCount > this.carouselCount)
        // {
        //     this.currentCarouselIndex = (currentCarouselIndex + 1) % 5;

            // only register whether throughbeam is broken when we are switching to a new slot
        //     this.hasPowerCell[this.currentCarouselIndex] = throughBeamBroken;
        // }

        // this.carouselCount = newCarouselCount;

        // this.logger.logNumber(PowerCellMechanism.logName, "turretVelocity", this.turretVelocity);
        // this.logger.logNumber(PowerCellMechanism.logName, "turretPosition", this.turretPosition);
        // this.logger.logNumber(PowerCellMechanism.logName, "turretError", this.turretError);
        // this.logger.logNumber(PowerCellMechanism.logName, "flywheelVelocity", this.flywheelVelocity);
        // this.logger.logNumber(PowerCellMechanism.logName, "flywheelPosition", this.flywheelPosition);
        // this.logger.logNumber(PowerCellMechanism.logName, "flywheelError", this.flywheelError);
        // this.logger.logInteger(PowerCellMechanism.logName, "carouselCount", this.carouselCount);
        // this.logger.logInteger(PowerCellMechanism.logName, "currentCarouselIndex", this.currentCarouselIndex);
        // this.logger.logBoolean(PowerCellMechanism.logName, "throughBeamBroken", throughBeamBroken);
        // this.logger.logBooleanArray(PowerCellMechanism.logName, "hasPowerCell", this.hasPowerCell);
    }

    @Override
    public void update()
    {
        double startingTurretOffset = this.driver.getAnalog(AnalogOperation.PowerCellTurretOffset);
        if (startingTurretOffset != 0.0)
        {
            this.startingTurretOffsetAngle = startingTurretOffset;
        }

        if (this.driver.getDigital(DigitalOperation.PowerCellResetTurretFront))
        {
            this.startingTurretOffsetAngle = 0.0;
            // this.turret.setPosition(0);
            // this.turret.set(0.0);
        }

        // if (this.driver.getDigital(DigitalOperation.PowerCellHoodPointBlank))
        // {
        //     this.innerHood.set(DoubleSolenoidValue.Reverse);
        //     this.outerHood.set(DoubleSolenoidValue.Reverse);
        // }
        // else if (this.driver.getDigital(DigitalOperation.PowerCellHoodShort))
        // {
        //     this.innerHood.set(DoubleSolenoidValue.Forward);
        //     this.outerHood.set(DoubleSolenoidValue.Reverse);
        // }
        // else if (this.driver.getDigital(DigitalOperation.PowerCellHoodMedium))
        // {
        //     this.innerHood.set(DoubleSolenoidValue.Reverse);
        //     this.outerHood.set(DoubleSolenoidValue.Forward);
        // }
        // else if (this.driver.getDigital(DigitalOperation.PowerCellHoodLong))
        // {
        //     this.innerHood.set(DoubleSolenoidValue.Forward);
        //     this.outerHood.set(DoubleSolenoidValue.Forward);
        // }

        // if (this.driver.getDigital(DigitalOperation.PowerCellKick))
        // {
        //     this.hasPowerCell[this.currentCarouselIndex] = false;
        //     this.kickerSolenoid.set(DoubleSolenoidValue.Forward);
        // }
        // else
        // {
        //     this.kickerSolenoid.set(DoubleSolenoidValue.Reverse);
        // }

        if (this.driver.getDigital(DigitalOperation.PowerCellIntakeExtend))
        {
            this.intakeSolenoid.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.PowerCellIntakeRetract))
        {
            this.intakeSolenoid.set(DoubleSolenoidValue.Reverse);
        }

        boolean isIntaking = this.driver.getDigital(DigitalOperation.PowerCellIntake);
        if (isIntaking)
        {
            this.rollerMotorInner.set(TuningConstants.POWERCELL_INNER_ROLLER_MOTOR_INTAKE_POWER);
            this.rollerMotorOuter.set(TuningConstants.POWERCELL_OUTER_ROLLER_MOTOR_INTAKE_POWER);
        }
        else if (this.driver.getDigital(DigitalOperation.PowerCellOuttake))
        {
            this.rollerMotorInner.set(TuningConstants.POWERCELL_INNER_ROLLER_MOTOR_OUTTAKE_POWER);
            this.rollerMotorOuter.set(TuningConstants.POWERCELL_OUTER_ROLLER_MOTOR_OUTTAKE_POWER);
        }
        else
        {
            this.rollerMotorInner.set(TuningConstants.STHOPE_BLEASE);
            this.rollerMotorOuter.set(TuningConstants.STHOPE_BLEASE);
        }

        // double flyWheelVelocitySetpoint;
        // double flyWheelVelocityPercentage = this.driver.getAnalog(AnalogOperation.PowerCellFlywheelVelocity);
        // if (Math.abs(flyWheelVelocityPercentage) < 0.01)
        // {
        //     // instead of trying to ensure the wheel is going at a speed of 0, let's just disable the motor
        //     flyWheelVelocitySetpoint = 0.0;
        //     this.flyWheel.stop();
        // }
        // else
        // {
        //     flyWheelVelocitySetpoint = flyWheelVelocityPercentage * TuningConstants.POWERCELL_FLYWHEEL_ONE_VELOCITY_PID_KS;
        //     this.flyWheel.set(flyWheelVelocitySetpoint);
        // }

        // this.logger.logNumber(PowerCellMechanism.logName, "flyWheelVelocitySetpoint", flyWheelVelocitySetpoint);

        // double desiredTurretPosition = this.driver.getAnalog(AnalogOperation.PowerCellTurretPosition);
        // this.logger.logNumber(PowerCellMechanism.logName, "desiredTurretPosition", desiredTurretPosition);
        // if (desiredTurretPosition != HardwareConstants.POWERCELL_TURRET_MAGIC_DONT_MOVE_VALUE)
        // {
        //     desiredTurretPosition = this.getClosestAngleInRange(desiredTurretPosition, this.getTurretPosition(), HardwareConstants.POWERCELL_TURRET_MINIMUM_RANGE_VALUE, HardwareConstants.POWERCELL_TURRET_MAXIMUM_RANGE_VALUE);
        //     this.turret.set((desiredTurretPosition + startingTurretOffsetAngle) * HardwareConstants.POWERCELL_TURRET_DEGREES_TO_TICKS);
        // }

        // if (isIntaking && this.state == CarouselState.Stationary)  // if intaking and currently stationary, start indexing
        // {
        //     this.state = CarouselState.Indexing;
        // }

        // if (!isIntaking  && this.state == CarouselState.Indexing && this.lastIntakeTime - this.timer.get() < 2.0) // become stationary if stopped intaking for more than 2 sec and indexing
        // {
        //     this.state = CarouselState.Stationary;
        // }

        // if (this.driver.getDigital(DigitalOperation.PowerCellMoveOneSlot))
        // {
        //     this.previousIndex = this.getCurrentCarouselIndex();
        //     this.state = CarouselState.MovingToNext;
        // }

        // if (isIntaking && this.state == CarouselState.Indexing) // if intaking and currently indexing, keep track of time
        // {
        //     this.lastIntakeTime = this.timer.get();
        // }

        // if (this.getCurrentCarouselIndex() != this.previousIndex && this.state == CarouselState.MovingToNext)
        // {
        //     this.genevaMotor.set(TuningConstants.STHOPE_BLEASE);
        //     this.state = CarouselState.Stationary;
        // }

        // if (this.state == CarouselState.Indexing)
        // {
        //     this.genevaMotor.set(TuningConstants.POWERCELL_GENEVA_MECHANISM_MOTOR_POWER_INDEXING);
        // }

        // if (this.state == CarouselState.MovingToNext)
        // {
        //     this.genevaMotor.set(TuningConstants.POWERCELL_GENEVA_MECHANISM_MOTOR_POWER_SHOOTING);
        // }

        // if (this.state == CarouselState.Stationary)
        // {
        //     this.genevaMotor.set(TuningConstants.STHOPE_BLEASE);
        // }
    }

    @Override
    public void stop()
    {
        // this.genevaMotor.stop();
        this.rollerMotorInner.stop();
        this.rollerMotorOuter.stop();
        // this.lowerHood.set(DoubleSolenoidValue.Off);
        // this.upperHood.set(DoubleSolenoidValue.Off);
        // this.kickerSolenoid.set(DoubleSolenoidValue.Off);
        this.intakeSolenoid.set(DoubleSolenoidValue.Off);
        // this.turret.stop();
        // this.flyWheel.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public double getTurretPosition()
    {
        return 0.0; //((this.turretPosition * HardwareConstants.POWERCELL_TURRET_TICKS_TO_DEGREES) - this.startingTurretOffsetAngle);
    }

    public double getFlywheelVelocity()
    {
        return 0.0; //this.flywheelVelocity;
    }

    public int getCurrentCarouselIndex()
    {
        return this.currentCarouselIndex;
    }

    public boolean hasPowerCell(int index)
    {
        return this.hasPowerCell[index];
    }

    public boolean hasAnyPowerCell()
    {
        for (boolean slotHasPowerCell : this.hasPowerCell)
        {
            if (slotHasPowerCell)
            {
                return true;
            }
        }

        return false;
    }

    private double getClosestAngleInRange(double desiredAngle, double currentAngle, double minRangeValue, double maxRangeValue)
    {
        double multiplicand = Math.floor(currentAngle / 360.0);

        double[] closeRotations =
        {
            (desiredAngle + 360.0 * (multiplicand - 1.0)),
            (desiredAngle + 360.0 * multiplicand),
            (desiredAngle + 360.0 * (multiplicand + 1.0)),
        };

        double best = currentAngle;
        double bestDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < 3; i++)
        {
            double angle = closeRotations[i];
            if (Helpers.WithinRange(angle, minRangeValue, maxRangeValue))
            {
                double angleDistance = Math.abs(currentAngle - angle);
                if (angleDistance < bestDistance)
                {
                    best = angle;
                    bestDistance = angleDistance;
                }
            }
        }

        return best;
    }

    private enum CarouselState
    {
        Indexing,
        Stationary,
        MovingToNext,
    }
}