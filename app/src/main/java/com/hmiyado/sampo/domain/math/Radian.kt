package com.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 弧度法による角度の値を表す
 */
class Radian(
        private val value: Double
) {
    companion object {
        val PI = Radian(Math.PI)
        val ZERO = Radian(0.0)

        fun min(r1: Radian, r2: Radian): Radian {
            return if (r1 < r2) {
                Radian(r1.value)
            } else {
                Radian(r2.value)
            }
        }
    }

    operator fun minus(radian: Radian): Radian {
        return Radian(value - radian.value)
    }

    operator fun plus(radian: Radian): Radian {
        return Radian(value + radian.value)
    }

    operator fun times(x: Double) = Radian(value * x)

    operator fun div(x: Double): Radian = Radian(value / x)

    operator fun div(x: Int): Radian = Radian(value / x)

    operator fun div(x: Radian) = Radian(value / x.value)

    operator fun compareTo(radian: Radian): Int = Math.signum(value - radian.value).toInt()

    operator fun unaryMinus(): Radian = Radian(-value)

    override fun equals(other: Any?): Boolean {
        return other is Radian && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString() = "${value / Math.PI} PI[rad]"

    fun toDegree(): Degree {
        return value.toDegree()
    }

    fun toFloat(): Float {
        return value.toFloat()
    }

    fun toDouble(): Double {
        return value
    }
}