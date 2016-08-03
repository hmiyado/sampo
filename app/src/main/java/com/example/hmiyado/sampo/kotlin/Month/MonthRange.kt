package com.example.hmiyado.sampo.kotlin.Month

/**
 * Created by hmiyado on 2016/08/02.
 */
class MonthRange(
        start: Month,
        endInclusive: Month
): MonthProgression(start, endInclusive, 1), ClosedRange<Month> {
    override fun equals(other: Any?): Boolean {
        return other is MonthRange && start == other.start && endInclusive == other.endInclusive
    }
}