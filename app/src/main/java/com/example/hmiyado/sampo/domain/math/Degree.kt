package com.example.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 度数法による角度の値を表す
 */
class Degree(
        value: Double
) {
    private val value: Double

    init {
        this.value = value % 360
    }

    operator fun minus(degree: Degree): Degree {
        return Degree(value - degree.value % 360)
    }

    operator fun plus(degree: Degree): Degree {
        return Degree(value + degree.value % 360)
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