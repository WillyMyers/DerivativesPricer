package com.myers.calc;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatesCalculatorTest {

    private final LocalDate start = LocalDate.parse("2021-01-01");
    private final LocalDate oneYearEnd = LocalDate.parse("2022-01-01");
    private final LocalDate end = LocalDate.parse("2031-01-01");

    @Test
    void testGetPaymentDates() {
        List<LocalDate> result = DatesCalculator.getPaymentDates(start, end);
        assertFalse(result.contains(start));
        assertTrue(result.contains(end));
        assertEquals(10, result.size());
    }

    @Test
    void testGetNoPaymentDates() {
        List<LocalDate> result = DatesCalculator.getPaymentDates(start, start);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetYearFraction() {
        assertEquals(1.0, DatesCalculator.getYearFraction(start, oneYearEnd));
    }

    @Test
    void testGetYearFractionLeapYear() {
        assertEquals(1.0027397260273974, DatesCalculator.getYearFraction(LocalDate.parse("2020-01-01"), LocalDate.parse("2021-01-01")));
    }
}
