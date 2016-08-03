package com.example.hmiyado.sampo.kotlin.Time

/**
 * Created by hmiyado on 2016/07/29.
 */
class Hour(value: Int) : Time(value) {
    override fun toSecond(): Second {
        return Minute(value * 60).toSecond()
    }

    operator fun plus(hour: Hour): Hour {
        return Hour(value + hour.value)
    }

    operator fun minus(hour: Hour): Hour {
        return Hour(value - hour.value)
    }

    override fun equals(other: Any?): Boolean {
        return other is Hour && value == other.value
    }

    override fun toString(): String {
        return "Hour($value)"
    }
}
