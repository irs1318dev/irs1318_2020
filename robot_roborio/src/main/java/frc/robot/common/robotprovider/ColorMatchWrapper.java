package frc.robot.common.robotprovider;

import java.util.HashMap;
import java.util.Map;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;

public class ColorMatchWrapper implements IColorMatch
{
    private final Map<String, Color> map;
    private final ColorMatch wrappedObject;

    public ColorMatchWrapper()
    {
        this.map = new HashMap<String, Color>();
        this.wrappedObject = new ColorMatch();
    }

    @Override
    public void addColorMatch(String name, double red, double green, double blue)
    {
        Color color = ColorMatch.makeColor(red, green, blue);
        this.map.put(name, color);
        this.wrappedObject.addColorMatch(color);
    }

    @Override
    public ColorMatchResult matchClosestColor(double red, double green, double blue)
    {
        com.revrobotics.ColorMatchResult result = this.wrappedObject.matchClosestColor(ColorMatch.makeColor(red, green, blue));

        String match = "";
        Color color = ColorMatch.makeColor(result.color.red, result.color.green, result.color.blue);
        for (String key : this.map.keySet())
        {
            if (color == this.map.get(key))
            {
                match = key;
                break;
            }
        }

        return new ColorMatchResult(match, result.confidence);
    }
}
