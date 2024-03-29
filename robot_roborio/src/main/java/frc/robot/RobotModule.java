package frc.robot;

import java.io.*;

import javax.inject.Singleton;

import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.*;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

public class RobotModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        this.bind(IRobotProvider.class).to(RobotProvider.class);
        this.bind(ITimer.class).to(TimerWrapper.class);
        this.bind(IButtonMap.class).to(ButtonMap.class);
        this.bind(ISmartDashboardLogger.class).to(SmartDashboardLogger.class);
        this.bind(IFile.class).to(FileWrapper.class);
    }

    @Singleton
    @Provides
    public MechanismManager getMechanismManager(Injector injector)
    {
        return new MechanismManager(TuningConstants.GetActiveMechanisms(injector));
    }

    @Singleton
    @Provides
    public LoggingManager getLoggingManager()
    {
        return new LoggingManager(injector -> TuningConstants.GetLogger(injector));
    }
}
