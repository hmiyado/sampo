package com.example.hmiyado.sampo.domain.model.Time

/**
 * Created by hmiyado on 2016/07/29.
 */
class Day(value: Long) : Time(value) {

    constructor(value: Int) : this(value.toLong())

    override fun toSecond(): Second {
        return Hour(value * 24).toSecond()
    }

    operator fun plus(day: Day): Day {
        return Day(value + day.value)
    }

    operator fun minus(day: Day): Day {
        return Day(value - day.value)
    }

    override fun toString(): String {
        return "Day($value)"
    }
}
