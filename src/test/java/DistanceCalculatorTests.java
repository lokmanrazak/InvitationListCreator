package com.lokmanrazak.test.java;

import com.lokmanrazak.main.java.utilities.DistanceCalculator;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DistanceCalculatorTests {
    private static final double MEAN_EARTH_RADIUS = 6371.009;

    @Test
    public void getDistance_givenSameCoordinates_returnZeroDistance() {
        DistanceCalculator dc = new DistanceCalculator(20, 20, MEAN_EARTH_RADIUS);
        double result = dc.getDistance(20, 20);

        assertEquals(result, 0);
    }

    @Test
    public void getDistance_givenValidArguments_returnCorrectResult() {
        DistanceCalculator dc = new DistanceCalculator(10, 10, MEAN_EARTH_RADIUS);
        double result = dc.getDistance(20, 10);

        assertTrue(1105 < result && result < 1115);
    }
}
