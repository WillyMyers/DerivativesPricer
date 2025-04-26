package com.myers.calc;

import com.myers.model.Curve;
import com.myers.model.CurvePoint;
import com.myers.model.InterestRateSwap;
import com.myers.model.PayReceive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicSwapPricerTest {

    private final LocalDate start = LocalDate.parse("2021-01-01");
    private final LocalDate end = LocalDate.parse("2031-01-01");
    Curve curve;

    @BeforeEach
    void setUp() {
        List<CurvePoint> points = new ArrayList<>();
        List<LocalDate> dates = DatesCalculator.getPaymentDates(start, end);
        double rate = 0.045;
        for (LocalDate paymentDate : dates) {
            points.add(new CurvePoint(paymentDate.minusYears(1), paymentDate, rate));
            rate += 0.001;
        }
        curve = new Curve(points);
    }

    @Test
    void testSwapPrice() {
        InterestRateSwap swap = new InterestRateSwap(PayReceive.PAY,
                10_000_000,
                0.0485,
                start,
                end);

        assertEquals(3709.8083611391485, BasicSwapPricer.priceSwap(swap, curve));
    }

    @Test
    void testSwapPricerException() {
        InterestRateSwap swap = new InterestRateSwap(PayReceive.PAY,
                10_000_000,
                0.045,
                start,
                end.plusYears(5));

        Exception ex = assertThrows(CalculatorException.class, ()-> BasicSwapPricer.priceSwap(swap, curve));
        assertEquals("No point available on curve that matches 2032-01-01", ex.getMessage());
    }
}
