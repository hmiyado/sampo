package com.example.hmiyado.sampo.domain.model.Time

/**
 * Created by hmiyado on 2016/07/29.
 */
class Minute(value: Long) : Time(value) {
    constructor(value: Int) : this(value.toLong())

    override fun toSecond(): Second {
        return Second(value * 60)
    }

    operator fun plus(minute: Minute): Minute {
        return Minute(value + minute.value)
    }

    operator fun minus(minute: Minute): Minute {
        return Minute(value - minute.value)
    }

    override fun toString(): String {
        return "Minute($value)"
    }
}
