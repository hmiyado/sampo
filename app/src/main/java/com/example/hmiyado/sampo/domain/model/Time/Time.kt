package com.example.hmiyado.sampo.domain.model.Time

/**
 * Created by hmiyado on 2016/07/29.
 */


abstract class Time(
        protected val value: Int
) {
    abstract fun toSecond(): Second
    fun toInt(): Int {
        return value
    }

    operator fun compareTo(time: Time): Int {
        return toSecond().toInt() - time.toSecond().toInt()
    }
}
