package com.myers.calc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DfCalculatorTest {

    @Test
    void calculateDiscountFactor() {
        assertEquals(0.9615384615384615, DfCalculator.getDiscountFactor(0.04, 1));
    }

    @Test
    void calculateDiscountFactorException() {
        Exception ex = assertThrows(CalculatorException.class, () -> DfCalculator.getDiscountFactor(1, 1));
        assertEquals("Rate cannot be greater than 100%!", ex.getMessage());
    }
}
