package com.hmiyado.sampo.domain.model

import com.hmiyado.sampo.domain.math.function.CeiledProportionalFunction
import com.hmiyado.sampo.domain.math.function.LogisticFunction
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import java.util.*

/**
 * Created by hmiyado on 2017/03/28.
 */
object SampoScorerBaseImpl : SampoScorer {
    override val maxTerritoryScore: Double
        get() = residentialCapacity + transientCeil + stayBonus
    const val residentialCapacity = 1080.0
    const val transientCeil = 200.0

    // 長期滞在中と見なされる時間単位
    // たとえば，この値が5分なら，5分間の間
    val RESIDENTIAL_DURATION: Duration = Duration.ofMinutes(5)

    // 長期滞在ボーナススコア関数　最大で1地点あたり1.5点
    val residentialScoreFunction = LogisticFunction(carryingCapacity = residentialCapacity, growthRate = 0.015, initialPopulation = 1.0)::invoke
    // 滞在スコア関数 1地点あたり1点
    val transientScoreFunction = CeiledProportionalFunction(ceil = transientCeil, ratio = 1000.0)::invoke
    // 滞在ボーナス 1地点あたり10点．ただし， Territory につき1回だけである
    val stayBonus = 10.0
    // 広さボーナス関数
    val areaScoreFunction = { area: Double -> Math.pow(area, 0.25) }

    // マーカーの影響力の減少率
    val markerImpactDecreasingRate = 0.5
    // マーカーの初期の（最大の）影響力
    val markerDefaultImpact = 1.0

    override fun Marker.calcImpact(now: Instant): Double {
        val total = validityPeriod.end.toEpochMilli() - validityPeriod.begin.toEpochMilli()
        val residualPeriod = validityPeriod.end.toEpochMilli() - now.toEpochMilli()

        return if (residualPeriod <= 0) {
            0.0
        } else {
            markerDefaultImpact * residualPeriod / total
        }
    }

    fun calcAreaImpact(territories: List<Territory>): Double {
        return areaScoreFunction(territories.size.toDouble())
    }

    fun calcTerritoriesScore(territories: List<Territory>, markers: List<Marker>, validPeriod: ValidityPeriod): Double {
        return territories.map {
            it.calcScore(markers.filter { marker -> it.area == marker.area }, validPeriod)
        }.sum()
    }

    override fun calcScore(territories: List<Territory>, markers: List<Marker>, validPeriod: ValidityPeriod): Double {
        return calcAreaImpact(territories) * calcTerritoriesScore(territories, markers, validPeriod)
    }

    fun calcTransientScore(timeStamps: SortedSet<Instant>): Double {
        return transientScoreFunction(timeStamps.size.toDouble())
    }

    fun calcResidentialScore(timeStamps: SortedSet<Instant>): Double {
        val residence = timeStamps.fold(Triple(0.0, 1.0, Instant.EPOCH), { triple, instant ->
            val maxResidentialSpan = triple.first
            val temporalResidentialSpan = triple.second
            val previousTimestamp = triple.third
            val newResidentialSpan = if (Duration.between(previousTimestamp, instant) <= RESIDENTIAL_DURATION) {
                temporalResidentialSpan + 1
            } else {
                1.0
            }
            Triple(
                    Math.max(maxResidentialSpan, newResidentialSpan),
                    newResidentialSpan,
                    instant
            )
        }).first
        return residentialScoreFunction(residence)
    }

    fun calcMarkersImpact(markers: List<Marker>, validPeriod: ValidityPeriod): Double {
        // 影響力は基本 1 倍
        return 1 + markers
                .map { it.calcImpact(validPeriod.begin) }
                .sorted()
                .mapIndexed { index, score -> score * Math.pow(markerImpactDecreasingRate, index.toDouble()) }
                .sum()
    }

    override fun Territory.calcScore(validPeriod: ValidityPeriod): Double {
        val timeStamps = locations.map { it.timeStamp }.filter { validPeriod.isValid(it) }.toSortedSet()

        return stayBonus + calcTransientScore(timeStamps) + calcResidentialScore(timeStamps)
    }

    override fun Territory.calcScore(markers: List<Marker>, validPeriod: ValidityPeriod): Double {
        val markersImpact = calcMarkersImpact(markers, validPeriod)

        return this.calcScore(validPeriod) * markersImpact
    }
}