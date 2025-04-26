package com.myers.model;

import java.time.LocalDate;

public record CurvePoint(LocalDate start, LocalDate end, double rate) {
}
