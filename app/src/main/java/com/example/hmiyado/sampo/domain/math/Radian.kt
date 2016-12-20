package com.example.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 弧度法による角度の値を表す
 */
class Radian(
        private val value: Double
) {
    operator fun minus(radian: Radian): Radian {
        return Radian(value - radian.value)
    }

    operator fun plus(radian: Radian): Radian {
        return Radian(value + radian.value)
    }

    override fun equals(other: Any?): Boolean {
        return other is Radian && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "$value[rad]"
    }

    fun toDegree(): Degree {
        return value.toDegree()
    }

    fun toFloat(): Float {
        return value.toFloat()
    }
}