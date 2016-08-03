package com.example.hmiyado.sampo.kotlin.Month

/**
 * Created by hmiyado on 2016/08/02.
 */
class MonthIterator(
        first: Month,
        private val last: Month,
        private val step: Int
): Iterator<Month> {
    private var next = first
    private var hasNext = if (step > 0) first <= last else first >= last

    override fun hasNext(): Boolean {
        return hasNext
    }

    override fun next(): Month {
        val value = next
        if ( value == last) {
            hasNext = false
        } else {
            next += Month.Interval(step)
        }
        return value
    }

}