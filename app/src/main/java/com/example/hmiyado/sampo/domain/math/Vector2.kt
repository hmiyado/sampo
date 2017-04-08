package com.example.hmiyado.sampo.domain.math

import java.lang.Math.sqrt

/**
 * Created by hmiyado on 2017/04/07.
 */
data class Vector2(
        val x: Double = 0.0,
        val y: Double = 0.0
) {
    companion object {
        val ZERO = Vector2(0.0, 0.0)
    }

    val length = sqrt(x.square() + y.square())

    constructor(x: Float, y: Float) : this(x.toDouble(), y.toDouble())
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())

    operator fun plus(other: Vector2): Vector2 = Vector2(x + other.x, y + other.y)

    operator fun minus(other: Vector2): Vector2 = Vector2(x - other.x, y - other.x)

    fun rotate(r: Radian): Vector2 {
        val cos = Math.cos(r.toDouble())
        val sin = Math.sin(r.toDouble())
        return Vector2(
                x * cos - y * sin,
                x * sin + y * cos
        )
    }

    fun rotate(d: Degree) = rotate(d.toRadian())
}