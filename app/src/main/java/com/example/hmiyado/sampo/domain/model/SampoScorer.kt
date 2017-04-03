package com.example.hmiyado.sampo.domain.model

import org.threeten.bp.Instant

/**
 * Created by hmiyado on 2017/03/21.
 */
interface SampoScorer {
    fun Marker.calcImpact(now: Instant): Double

    /**
     * ある Territory のスコアを計算する．
     */
    fun Territory.calcScore(markers: List<Marker>, validPeriod: ValidityPeriod): Double

    fun calcScore(territories: List<Territory>, markers: List<Marker>, validPeriod: ValidityPeriod): Double

}