package frc.robot;

/**
 * Keys describing logging 
 */
public enum LoggingKey
{
    RobotState("r.state"),
    RobotTime("r.time"),
    DriverIsAuto("driver.isAuto"),
    DriverActiveMacros("driver.activeMacros"),
    DriverActiveShifts("driver.activeShifts"),
    AutonomousSelection("auto.selected"),
    DriveTrainLeftVelocity("dt.leftVelocity"),
    DriveTrainLeftTicks("dt.leftTicks"),
    DriveTrainLeftError("dt.leftError"),
    DriveTrainRightVelocity("dt.rightVelocity"),
    DriveTrainRightTicks("dt.rightTicks"),
    DriveTrainRightError("dt.rightError"),
    DriveTrainLeftVelocityGoal("dt.leftVelocityGoal"),
    DriveTrainRightVelocityGoal("dt.rightVelocityGoal"),
    DriveTrainLeftPositionGoal("dt.leftPositionGoal"),
    DriveTrainRightPositionGoal("dt.rightPositionGoal"),
    OffboardVisionX("rpi.x"),
    OffboardVisionY("rpi.y"),
    OffboardVisionDistance("rpi.distance"),
    OffboardVisionHorizontalAngle("rpi.horizontalAngle"),
    OffboardVisionEnableVision("rpi.enableVision"),
    OffboardVisionEnableStream("rpi.enableStream"),
    OffboardVisionEnableProcessing("rpi.enableProcessing"),
    PositionOdometryAngle("pos.odom_angle"),
    PositionOdometryX("pos.odom_x"),
    PositionOdometryY("pos.odom_y"),
    PositionNavxConnected("pos.navx_connected"),
    PositionNavxAngle("pos.navx_angle"),
    PositionNavxX("pos.navx_x"),
    PositionNavxY("pos.navx_y"),
    PositionNavxZ("pos.navx_z"),
    PositionStartingAngle("pos.startingAngle"),
    ControlPanelRed("color.red"),
    ControlPanelGreen("color.green"),
    ControlPanelBlue("color.blue"),
    ControlPanelIR("color.IR"),
    ControlPanelName("color.name"),
    ControlPanelConfidence("color.confidence"),
    ControlPanelProximity("color.proximity"),
    ControlPanelTargetColorMapped("color.targetColorMapped"),
    ControlPanelGameMessage("color.gameMessage"),
    ControlPanelExtend("color.extend"),
    PowerCellTurretVelocity("pc.turretVelocity"),
    PowerCellTurretPosition("pc.turretPosition"),
    PowerCellTurretError("pc.turretError"),
    PowerCellFlywheelVelocity("pc.flywheelVelocity"),
    PowerCellFlywheelPosition("pc.flywheelPosition"),
    PowerCellFlywheelError("pc.flywheelError"),
    PowerCellCarouselCount("pc.carouselCount"),
    PowerCellCarouselCurrentIndex("pc.carouselCurrentIndex"),
    PowerCellThroughBeamVoltage("pc.throughBeamVoltage"),
    PowerCellThroughBeamBroken("pc.throughBeamBroken"),
    PowerCellHasPowerCell("pc.hasPowerCell"),
    PowerCellFlywheelVelocitySetpoint("pc.flyWheelVelocitySetpoint"),
    PowerCellTurretPositionDesired("pc.turretPositionDesired"),
    PowerCellCarouselState("pc.carouselState"),
    PowerCellGenevaPower("pc.genevaPower"),
    PowerCellIsIntaking("pc.isIntaking"),
    PowerCellIntakeExtended("pc.intakeExtended"),;


    public final String value;
    LoggingKey(String value)
    {
        this.value = value;
    }
}
