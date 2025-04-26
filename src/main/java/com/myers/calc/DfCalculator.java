package com.myers.calc;

public class DfCalculator {

    public static double getDiscountFactor(double rate, double period) {
        if(rate >= 1.0) {
            throw new CalculatorException("Rate cannot be greater than 100%!");
        }
        return Math.pow(1.0 / (1.0 + rate), period);
    }
}
