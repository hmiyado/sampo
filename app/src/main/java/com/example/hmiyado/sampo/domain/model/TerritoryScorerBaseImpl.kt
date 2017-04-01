package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.math.function.CeiledProportionalFunction
import com.example.hmiyado.sampo.domain.math.function.LogisticFunction
import org.threeten.bp.Duration
import org.threeten.bp.Instant

/**
 * Created by hmiyado on 2017/03/28.
 */
object TerritoryScorerBaseImpl : TerritoryScorer {
    // 長期滞在中と見なされる時間単位
    // たとえば，この値が5分なら，5分間の間
    val RESIDENTIAL_DURATION: Duration = Duration.ofMinutes(5)

    // 長期滞在ボーナススコア関数　最大で1地点あたり1.5点
    val residentialScoreFunction = LogisticFunction(carryingCapacity = 1080.0, growthRate = 0.015, initialPopulation = 1.0)::invoke
    // 滞在スコア関数 1地点あたり1点
    val transientScoreFunction = CeiledProportionalFunction(ceil = 200.0, ratio = 1000.0)::invoke
    // 滞在ボーナス 1地点あたり10点．ただし， Territory につき1回だけである
    val stayBonus = 10.0
    // 広さボーナス関数
    val areaScoreFunction = { area: Double -> Math.pow(area, 0.25) }

    override fun calcScore(territories: List<Territory>, territoryValidPeriod: TerritoryValidityPeriod): Double {
        return areaScoreFunction(territories.size.toDouble()) * territories.map { calcScorePerTerritory(it, territoryValidPeriod) }.sum()
    }

    override fun calcScorePerTerritory(territory: Territory, territoryValidPeriod: TerritoryValidityPeriod): Double {
        val sortedValidLocations = territory.locations.sortedBy { it.timeStamp }.filter { territoryValidPeriod.isValid(it.timeStamp) }
        val transient = sortedValidLocations.size
        val residence = sortedValidLocations.fold(Triple(0.0, 1.0, Instant.EPOCH), { triple, temporalLocation ->
            val maxResidentialSpan = triple.first
            val temporalResidentialSpan = triple.second
            val previousTimestamp = triple.third
            val newResidentialSpan = if (Duration.between(previousTimestamp, temporalLocation.timeStamp) <= RESIDENTIAL_DURATION) {
                temporalResidentialSpan + 1
            } else {
                1.0
            }
            Triple(
                    Math.max(maxResidentialSpan, newResidentialSpan),
                    newResidentialSpan,
                    temporalLocation.timeStamp
            )
        }).first

        return stayBonus + transientScoreFunction(transient.toDouble()) + residentialScoreFunction(residence)
    }
}