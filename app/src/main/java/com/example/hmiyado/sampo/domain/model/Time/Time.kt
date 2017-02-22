package com.example.hmiyado.sampo.domain.model.Time

/**
 * Created by hmiyado on 2016/07/29.
 */


abstract class Time(
        protected val value: Long
) {
    abstract fun toSecond(): Second
    fun toInt(): Int {
        return value.toInt()
    }

    operator fun compareTo(time: Time): Int {
        return toSecond().toInt() - time.toSecond().toInt()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Time

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}
