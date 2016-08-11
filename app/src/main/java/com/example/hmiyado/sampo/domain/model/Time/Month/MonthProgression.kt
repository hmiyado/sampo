package com.example.hmiyado.sampo.domain.model.Time.Month

/**
 * Created by hmiyado on 2016/08/02.
 */
open class MonthProgression constructor(
        val start: Month,
        val endInclusive: Month,
        val step: Int
): Iterable<Month> {
    companion object {
        fun fromClosedRange(
                rangeStart: Month,
                rangeEnd: Month,
                step: Int
        ): MonthProgression {
            return MonthProgression(rangeStart, rangeEnd, step)
        }
    }

    private fun getLastElement(): Month {
        var last = start
        while (last + Month.Interval(step) <= endInclusive) {
            last += Month.Interval(step)
        }
        return last
    }

    override fun iterator(): MonthIterator {
        return MonthIterator(start, getLastElement(), step)
    }

}