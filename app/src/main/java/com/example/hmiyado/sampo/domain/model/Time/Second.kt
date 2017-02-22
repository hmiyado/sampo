package com.example.hmiyado.sampo.domain.model.Time

/**
 * Created by hmiyado on 2016/07/29.
 */

class Second(value: Long) : Time(value) {
    constructor(value: Int) : this(value.toLong())

    override fun toSecond(): Second {
        return Second(value)
    }

    operator fun minus(time: Time): Second {
        val diff = value - time.toSecond().value
        return Second(diff)
    }

    operator fun plus(time: Time): Second {
        val sum = value + time.toSecond().value
        return Second(sum)
    }

    operator fun compareTo(second: Second): Int {
        return (this - second).toInt()
    }

    override fun toString(): String {
        return "Second($value)"
    }

    fun toLong(): Long {
        return value
    }
}