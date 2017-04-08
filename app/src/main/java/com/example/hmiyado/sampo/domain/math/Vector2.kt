package com.example.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2017/04/07.
 */
data class Vector2(
        val x: Double = 0.0,
        val y: Double = 0.0
) {
    constructor(x: Float, y: Float) : this(x.toDouble(), y.toDouble())

    operator fun plus(other: Vector2): Vector2 = Vector2(x + other.x, y + other.y)

    operator fun minus(other: Vector2): Vector2 = Vector2(x - other.x, y - other.x)
}