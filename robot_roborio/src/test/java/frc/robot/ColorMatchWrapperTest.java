package frc.robot;

import org.junit.jupiter.api.Test;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import frc.robot.common.robotprovider.ColorMatchResult;
import frc.robot.common.robotprovider.ColorMatchWrapper;

import com.google.inject.Guice;

public class ColorMatchWrapperTest
{
    /**
     * Make sure the wiring is in place.
     */
    @Test
    public void testColorMatch()
    {
        ColorMatchWrapper colorMatch = new ColorMatchWrapper();
        colorMatch.addColorMatch("red", TuningConstants.COLOR_MATCH_RED_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_RED_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_RED_TARGET_BLUE_PERCENTAGE);
        colorMatch.addColorMatch("green", TuningConstants.COLOR_MATCH_GREEN_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_GREEN_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_GREEN_TARGET_BLUE_PERCENTAGE);
        colorMatch.addColorMatch("blue",TuningConstants.COLOR_MATCH_BLUE_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_BLUE_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_BLUE_TARGET_BLUE_PERCENTAGE);
        colorMatch.addColorMatch("yellow", TuningConstants.COLOR_MATCH_YELLOW_TARGET_RED_PERCENTAGE, TuningConstants.COLOR_MATCH_YELLOW_TARGET_GREEN_PERCENTAGE, TuningConstants.COLOR_MATCH_YELLOW_TARGET_BLUE_PERCENTAGE);

        ColorMatchResult result = colorMatch.matchClosestColor(0.119, 0.421, 0.458);
        if (result.getName() != "blue")
        {
            throw new RuntimeException("should match blue!");
        }
    }
}
