package frc.robot.common.robotprovider;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NetworkTableEntryWrapper implements INetworkTableEntry
{
    final NetworkTableEntry wrappedObject;

    NetworkTableEntryWrapper(NetworkTableEntry object)
    {
        this.wrappedObject = object;
    }

    public double getDouble(double defaultValue)
    {
        return this.wrappedObject.getDouble(defaultValue);
    }
}