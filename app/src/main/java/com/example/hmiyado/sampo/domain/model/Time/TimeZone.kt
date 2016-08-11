package com.example.hmiyado.sampo.domain.model.Time

/**
 * Created by hmiyado on 2016/07/29.
 */
open class TimeZone(val isPlus: Boolean, val hour: Hour, val minute: Minute) {
    companion object {
        fun empty(): TimeZone = object : TimeZone(true, Hour(-10000), Minute(-10000)) {
            override fun isEmpty(): Boolean {
                return true
            }
        }

        val Utc = TimeZone(true, Hour(0), Minute(0))
    }

    fun toSecond(): Second {
        val sign = if(isPlus) 1 else -1
        return Second( sign * ( hour.toSecond().toInt() + minute.toSecond().toInt()))
    }

    operator fun compareTo(timeZone: TimeZone): Int {
        return (toSecond() - timeZone.toSecond()).toInt()
    }

    override fun toString(): String {
        return "TimeZone: ${if (isPlus) {
            "+"
        } else {
            "-"
        }}${hour}:${minute}"
    }

    open fun isEmpty(): Boolean {
        return false
    }
}
