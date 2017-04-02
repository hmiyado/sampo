package com.example.hmiyado.sampo.domain.model

import org.threeten.bp.Instant

/**
 * Created by hmiyado on 2017/04/02.
 *
 * 地点においとける
 */
class Marker(
        val location: Location = Location(),
        val validityPeriod: ValidityPeriod = ValidityPeriod.create(),
        val defaultScore: Double = 100.0
) {
    val area = Area(location)

    fun getScore(now: Instant): Double {
        val total = validityPeriod.end.toEpochMilli() - validityPeriod.start.toEpochMilli()
        val residualPeriod = validityPeriod.end.toEpochMilli() - now.toEpochMilli()

        return if (residualPeriod <= 0) {
            0.0
        } else {
            defaultScore * residualPeriod / total
        }
    }
}