package com.example.hmiyado.sampo.domain.model.Time.Year

import rx.internal.operators.OperatorThrottleFirst

/**
 * Created by hmiyado on 2016/08/02.
 */
class YearIterator(
        first: Year,
        private val last: Year,
        private val step: Int
): Iterator<Year> {
    private var next = first
    private var hasNext = if (step > 0) first <= last else first >= last

    override fun hasNext(): Boolean {
        return hasNext
    }

    override fun next(): Year {
        val value = next
        if ( value == last){
            hasNext = false
        } else {
            next += Year.Interval(step)
        }
        return value
    }
}