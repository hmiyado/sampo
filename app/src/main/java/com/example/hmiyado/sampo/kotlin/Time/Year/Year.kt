package com.example.hmiyado.sampo.kotlin.Time.Year

import com.example.hmiyado.sampo.kotlin.Time.Day
import com.example.hmiyado.sampo.kotlin.Month.Month
import com.example.hmiyado.sampo.kotlin.Time.Second

/**
 * Created by hmiyado on 2016/07/29.
 */
class Year(
        private val value: Int
): Comparable<Year>{
    companion object {

    }

    /**
     * @return Second 西暦0年から西暦value-1年までに経過した秒数
     */
//    override fun toSecond(): Second {
//        return (0..value - 1)
//                .map {
//                    val lastMonth = Month(12, Year(it))
//                    lastMonth.toSecond() + Day(lastMonth.getNumberOfDays()).toSecond()
//                }
//                .fold(Second(0), { secondsOfYear, sumOfSeconds ->
//                    return secondsOfYear + sumOfSeconds
//                })
//    }

    data class Interval(val value: Int)

    private fun seconds(): Second {
        if (isLeapYear()) {
            return Day(366).toSecond()
        } else {
            return Day(365).toSecond()
        }
    }

    fun pastSecondsUntil(year: Year): Second {
        if ( this > year) {
            return Second( -1 * year.pastSecondsUntil(this).toInt())
        }

        val secondsOfYears = (this..year)
                .map { y ->
                    y.seconds().toInt()
                }.sum() - year.seconds().toInt()
        return Second(secondsOfYears)
    }

    operator fun plus(yearInterval: Interval): Year {
        return Year(value + yearInterval.value)
    }
    operator fun minus(yearInterval: Interval): Year {
        return Year(value - yearInterval.value)
    }

    operator fun minus(year: Year): Interval {
        return Interval(value - year.value)
    }

    override operator fun compareTo(year: Year): Int {
        return value - year.value
    }
    operator fun rangeTo(other: Year): YearRange {
        return YearRange(this, other)
    }

    fun isLeapYear(): Boolean {
        return value % 4 == 0 && value % 100 != 0 || value % 400 == 0
    }

    override fun toString(): String {
        return "Year($value)"
    }

    override fun equals(other: Any?): Boolean {
        return other is Year && other.value == value
    }
}
