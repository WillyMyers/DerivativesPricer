package com.myers.calc;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DatesCalculator {

    public static List<LocalDate> getPaymentDates(LocalDate start, LocalDate end) {
        return start.plusYears(1).datesUntil(end.plusYears(1), Period.ofYears(1)).toList();
    }

    //TODO - take a basis to divide by the correct amount
    public static double getYearFraction(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end) / 365.0;
    }
}
