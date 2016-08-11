package com.example.hmiyado.sampo.domain.model.Time.Year

/**
 * Created by hmiyado on 2016/08/02.
 */
class YearRange(
        start: Year,
        endInclusive: Year
): YearProgression(start, endInclusive, 1), ClosedRange<Year> {
}