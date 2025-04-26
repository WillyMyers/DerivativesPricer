package com.myers.calc;

import com.myers.model.Curve;
import com.myers.model.CurvePoint;
import com.myers.model.InterestRateSwap;
import com.myers.model.PayReceive;

import java.time.LocalDate;
import java.util.List;

public class BasicSwapPricer {

    //TODO - we are assuming the same float rate but in reality this should take a curve
    public static double priceSwap(InterestRateSwap swap, Curve curve) {
        double floatPv = 0.0;
        double fixedPv = 0.0;

        List<LocalDate> paymentDates = DatesCalculator.getPaymentDates(swap.startDate(), swap.endDate());
        double period = 1.0;
        LocalDate previousDate = swap.startDate();

        for (LocalDate paymentDate : paymentDates) {
            CurvePoint curvePoint = getCurvePoint(curve, paymentDate);
            fixedPv += getPv(swap.notional(), swap.fixedRate(), paymentDate, previousDate, period);
            floatPv += getPv(swap.notional(), curvePoint.rate(), paymentDate, previousDate, period);
            previousDate = paymentDate;
            period++;
        }
        return swap.fixedLegDirection().equals(PayReceive.PAY) ?
                floatPv - fixedPv : fixedPv - floatPv;
    }

    private static CurvePoint getCurvePoint(Curve curve, LocalDate paymentDate) {
        return curve.points()
                .stream()
                .filter(pt -> paymentDate.isAfter(pt.start().minusDays(1)) && paymentDate.isBefore(pt.end().plusDays(1)))
                .findFirst()
                .orElseThrow(() -> new CalculatorException("No point available on curve that matches " + paymentDate));
    }

    private static double getPv(double notional, double rate, LocalDate paymentDate, LocalDate previousDate, double period) {
        double yearFraction = DatesCalculator.getYearFraction(previousDate, paymentDate);
        double discountFactor = DfCalculator.getDiscountFactor(rate, period);
        return notional * rate * yearFraction * discountFactor;
    }
}
