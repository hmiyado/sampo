package com.example.hmiyado.sampo.domain.model

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

    // 長期滞在ボーナススコア関数
    val residentialScoreFunction = LogisticFunction(carryingCapacity = 500.0)::invoke
    // 滞在スコア関数
    val transientScoreFunction = LogisticFunction()::invoke
    // 滞在ボーナス
    val stayBonus = 10.0

    override fun calcScore(locations: List<Location>, territoryValidPeriod: TerritoryValidityPeriod): Double {
        val sortedValidLocations = locations.sortedBy { it.timeStamp }.filter { territoryValidPeriod.isValid(it.timeStamp) }
        val transient = sortedValidLocations.size
        val residence = sortedValidLocations.fold(Triple(0.0, 0.0, Instant.EPOCH), { triple, temporalLocation ->
            val maxResidentialSpan = triple.first
            val temporalResidentialSpan = triple.second
            val previousTimestamp = triple.third
            val newResidentialSpan = if (Duration.between(previousTimestamp, temporalLocation.timeStamp) <= RESIDENTIAL_DURATION) {
                temporalResidentialSpan + 1
            } else {
                0.0
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