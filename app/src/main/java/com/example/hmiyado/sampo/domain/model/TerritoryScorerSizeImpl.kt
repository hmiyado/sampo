package com.example.hmiyado.sampo.domain.model

/**
 * Created by hmiyado on 2017/03/21.
 */
object TerritoryScorerSizeImpl : TerritoryScorer {
    override fun calcScore(locations: List<Location>, territoryValidPeriod: TerritoryValidityPeriod): Double {
        return locations.filter { territoryValidPeriod.isValid(it.timeStamp) }.size.toDouble()
    }
}