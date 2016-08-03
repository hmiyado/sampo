package com.example.hmiyado.sampo.kotlin.Time.Year

/**
 * Created by hmiyado on 2016/08/02.
 */
class YearRange(
        start: Year,
        endInclusive: Year
): YearProgression(start, endInclusive, 1), ClosedRange<Year> {
}