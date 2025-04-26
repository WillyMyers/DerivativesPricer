package com.myers.model;

import java.time.LocalDate;

public record InterestRateSwap(PayReceive fixedLegDirection,
                               double notional,
                               double fixedRate,
                               LocalDate startDate,
                               LocalDate endDate) {
}
