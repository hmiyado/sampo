package com.example.hmiyado.sampo.domain.math.function

/**
 * Created by hmiyado on 2017/03/28.
 */
class PolynomialFunction(
        val order: Double = 1.5
) {
    fun invoke(x: Double): Double {
        return Math.pow(x, order)
    }
}