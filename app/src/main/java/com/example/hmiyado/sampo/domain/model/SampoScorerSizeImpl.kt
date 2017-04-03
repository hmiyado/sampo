package com.example.hmiyado.sampo.domain.model

import org.threeten.bp.Instant

/**
 * Created by hmiyado on 2017/03/21.
 */
object SampoScorerSizeImpl : SampoScorer {
    override fun Marker.calcImpact(now: Instant): Double {
        return 0.0
    }

    override fun Territory.calcScore(markers: List<Marker>, validPeriod: ValidityPeriod): Double {
        return locations.size.toDouble()
    }

    override fun calcScore(territories: List<Territory>, markers: List<Marker>, validPeriod: ValidityPeriod): Double {
        return territories.map { it.calcScore(markers.filter { marker -> it.area == marker.area }, validPeriod) }.sum()
    }
}