package com.hmiyado.sampo.domain.model

import org.threeten.bp.Instant

/**
 * Created by hmiyado on 2017/03/21.
 */
interface SampoScorer {
    /**
     * [Territory.calcScore] が取りうる最大のスコア
     */
    val maxTerritoryScore: Double

    /**
     * マーカーの影響力を計算する
     */
    fun Marker.calcImpact(now: Instant): Double

    /**
     * Territory のスコアを計算する
     */
    fun Territory.calcScore(validPeriod: ValidityPeriod): Double

    /**
     * ある Territory とマーカーのスコアを計算する．
     */
    fun Territory.calcScore(markers: List<Marker>, validPeriod: ValidityPeriod): Double

    /**
     * Territory と マーカー 全体のスコアを計算する
     */
    fun calcScore(territories: List<Territory>, markers: List<Marker>, validPeriod: ValidityPeriod): Double

}