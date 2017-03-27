package com.example.hmiyado.sampo.domain.math.function

/**
 * Created by hmiyado on 2017/03/28.
 *
 * https://en.wikipedia.org/wiki/Logistic_function
 */
class LogisticFunction(
        initialPopulation: Double = 1.0,
        growthRate: Double = 1.1,
        carryingCapacity: Double = 100.0
) {
    private val P0 = initialPopulation
    private val K = carryingCapacity
    private val r = growthRate

    fun invoke(t: Double): Double {
        val exp = Math.exp(r * t)
        if (exp.isInfinite()) {
            return K
        } else {
            return K * P0 * exp / (K + P0 * (exp - 1))
        }
    }
}