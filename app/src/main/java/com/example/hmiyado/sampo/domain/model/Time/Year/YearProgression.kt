package com.example.hmiyado.sampo.domain.model.Time.Year

/**
 * Created by hmiyado on 2016/08/02.
 */
open class YearProgression constructor(
        val start: Year,
        val endInclusive: Year,
        val step: Int): Iterable<Year> {
    companion object {
        fun fromClosedRange(
                rangeStart: Year,
                rangeEnd: Year,
                step: Int
        ): YearProgression {
            return YearProgression(rangeStart, rangeEnd, step)
        }
    }

    private fun getLastElement(): Year {
        var last = start
        while ( last + Year.Interval(step) <= endInclusive){
            last += Year.Interval(step)
        }
        return last
    }


    override fun iterator(): YearIterator {
        return YearIterator(start, getLastElement(), step)
    }
}
