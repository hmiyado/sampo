package com.example.hmiyado.sampo.kotlin.Time

/**
 * Created by hmiyado on 2016/07/29.
 */
class Day(value: Int) : Time(value) {
    override fun toSecond(): Second {
        return Hour(value * 24).toSecond()
    }

    operator fun plus(day: Day): Day {
        return Day(value + day.value)
    }

    operator fun minus(day: Day): Day {
        return Day(value - day.value)
    }

    override fun equals(other: Any?): Boolean {
        return other is Day && value == other.value
    }

    override fun toString(): String {
        return "Day($value)"
    }
}