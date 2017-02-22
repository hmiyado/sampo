package com.example.hmiyado.sampo.domain.model.Time

/**
 * Created by hmiyado on 2016/07/29.
 */
class Hour(value: Long) : Time(value) {

    constructor(value: Int) : this(value.toLong())

    override fun toSecond(): Second {
        return Minute(value * 60).toSecond()
    }

    operator fun plus(hour: Hour): Hour {
        return Hour(value + hour.value)
    }

    operator fun minus(hour: Hour): Hour {
        return Hour(value - hour.value)
    }

    override fun toString(): String {
        return "Hour($value)"
    }
}
