package com.example.hmiyado.sampo.kotlin.Time

/**
 * Created by hmiyado on 2016/07/29.
 */
class Minute(value: Int) : Time(value) {
    override fun toSecond(): Second {
        return Second(value * 60)
    }

    operator fun plus(minute: Minute): Minute {
        return Minute(value + minute.value)
    }

    operator fun minus(minute: Minute): Minute {
        return Minute(value - minute.value)
    }

    override fun equals(other: Any?): Boolean {
        return other is Minute && value == other.value
    }

    override fun toString(): String {
        return "Minute($value)"
    }
}
