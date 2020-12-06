package com.lokmanrazak.test.java;

import com.lokmanrazak.main.java.utilities.SimpleDistanceCalculator;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleDistanceCalculatorTests {
    private static final double MEAN_EARTH_RADIUS = 6371.009;

    @Test
    public void getDistance_givenSameCoordinates_returnZeroDistance() {
        SimpleDistanceCalculator dc = new SimpleDistanceCalculator(20, 20, MEAN_EARTH_RADIUS);
        double result = dc.getDistance(20, 20);

        assertEquals(result, 0);
    }

    @Test
    public void getDistance_givenValidArguments_returnCorrectResult() {
        SimpleDistanceCalculator dc = new SimpleDistanceCalculator(10, 10, MEAN_EARTH_RADIUS);
        double result = dc.getDistance(20, 10);

        assertTrue(1105 < result && result < 1115);
    }
}
