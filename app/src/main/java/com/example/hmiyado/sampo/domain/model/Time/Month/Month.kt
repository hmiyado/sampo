package com.example.hmiyado.sampo.domain.model.Time.Month

import com.example.hmiyado.sampo.domain.exception.IllegalValueOfMonthException
import com.example.hmiyado.sampo.domain.model.Time.Day
import com.example.hmiyado.sampo.domain.model.Time.Second
import com.example.hmiyado.sampo.domain.model.Time.Year.Year

/**
 * Created by hmiyado on 2016/07/29.
 */
sealed class Month constructor(
        private val value: Int,
        val year: Year// うるう年があるので，年も必須
) : Comparable<Month> {
    companion object {
        val MAX_NUM_MONTHS_IN_YEAR = 12

        fun newInstance(value: Int, year: Year): Month {
            val (month, yearNext) = when {
                value > MAX_NUM_MONTHS_IN_YEAR -> {
                    val yearNext = year + Year.Interval(value / MAX_NUM_MONTHS_IN_YEAR)
                    val month = value % MAX_NUM_MONTHS_IN_YEAR
                    Pair(month, yearNext)
                }
                value < 1                      -> {
                    val yearNext = year - Year.Interval(value / MAX_NUM_MONTHS_IN_YEAR + 1)
                    val month = MAX_NUM_MONTHS_IN_YEAR - Math.abs(value) % MAX_NUM_MONTHS_IN_YEAR
                    Pair(month, yearNext)
                }
                else                           -> {
                    val yearNext = year
                    val month = value
                    Pair(month, yearNext)
                }

            }
            when (month) {
                1    -> return January(yearNext)
                2    -> return February(yearNext)
                3    -> return March(yearNext)
                4    -> return April(yearNext)
                5    -> return May(yearNext)
                6    -> return June(yearNext)
                7    -> return July(yearNext)
                8    -> return August(yearNext)
                9    -> return September(yearNext)
                10   -> return October(yearNext)
                11   -> return November(yearNext)
                12   -> return December(yearNext)
                else -> throw IllegalValueOfMonthException()
            }
        }

        fun newInstance(month: Month, year: Year): Month {
            return Month.newInstance(month.value, year)
        }
    }

    abstract val LastDay: Int

    class January(year: Year) : Month(1, year) {
        override val LastDay: Int
            get() = 31
    }

    class February(year: Year) : Month(2, year) {
        override val LastDay: Int
            get() = if (year.isLeapYear()) 29 else 28
    }

    class March(year: Year) : Month(3, year) {
        override val LastDay: Int
            get() = 31
    }

    class April(year: Year) : Month(4, year) {
        override val LastDay: Int
            get() = 30
    }

    class May(year: Year) : Month(5, year) {
        override val LastDay: Int
            get() = 31
    }

    class June(year: Year) : Month(6, year) {
        override val LastDay: Int
            get() = 30
    }

    class July(year: Year) : Month(7, year) {
        override val LastDay: Int
            get() = 31
    }

    class August(year: Year) : Month(8, year) {
        override val LastDay: Int
            get() = 31
    }

    class September(year: Year) : Month(9, year) {
        override val LastDay: Int
            get() = 30
    }

    class October(year: Year) : Month(10, year) {
        override val LastDay: Int
            get() = 31
    }

    class November(year: Year) : Month(11, year) {
        override val LastDay: Int
            get() = 30
    }

    class December(year: Year) : Month(12, year) {
        override val LastDay: Int
            get() = 31
    }

    data class Interval(val value: Int)

    fun getValue(): Int = value

    fun pastSecondsUntil(month: Month): Second {
        if (this > month) {
            return Second(month.pastSecondsUntil(this).toInt() * (-1))
        }

        val secondsOfDay = Day(1).toSecond().toInt()
        val sumOfSeconds = (this..month).fold(0, { sum, m ->
            sum + m.LastDay * secondsOfDay
        }) - month.LastDay * secondsOfDay
        return Second(sumOfSeconds)
    }

    override operator fun compareTo(other: Month): Int {
        when {
            year > other.year -> return 1
            year < other.year -> return -1
            else              -> return value - other.value
        }
    }

    operator fun rangeTo(other: Month): MonthRange {
        return MonthRange(this, other)
    }

    operator fun plus(monthInterval: Interval): Month {
        val sum = value + monthInterval.value
        return Month.newInstance(sum, year)
    }

    operator fun minus(monthInterval: Interval): Month {
        val diff = value - monthInterval.value
        return Month.newInstance(diff, year)
    }

    operator fun minus(month: Month): Interval {
        val yearDiff = year - month.year
        val monthDiff = value - month.value
        return Interval(yearDiff.value * MAX_NUM_MONTHS_IN_YEAR + monthDiff)
    }

    override fun toString(): String {
        return "Month($value)"
    }

    override fun equals(other: Any?): Boolean {
        return other is Month && other.value == value && other.year == year
    }
}
