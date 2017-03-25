package com.example.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 度数法による角度の値を表す
 */
class Degree(
        value: Double
) {
    private val value = value % 360

    constructor(value: Int) : this(value.toDouble())

    operator fun minus(degree: Degree): Degree {
        return Degree(value - degree.value % 360)
    }

    operator fun plus(degree: Degree): Degree {
        return Degree(value + degree.value % 360)
    }

    operator fun times(number: Int): Degree {
        return Degree(value * number)
    }

    operator fun times(number: Double): Degree {
        return Degree(value * number)
    }

    operator fun div(degree: Degree): Double {
        return value / degree.value
    }

    operator fun div(number: Double): Degree {
        return Degree(value / number)
    }

    operator fun div(number: Int): Degree {
        return Degree(value / number)
    }

    override fun equals(other: Any?): Boolean {
        return other is Degree && value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "$value°"
    }

    fun toRadian(): Radian {
        return Radian(value * Math.PI / 180)
    }

    fun toFloat(): Float {
        return value.toFloat()
    }

    fun toDouble(): Double {
        return value
    }
}