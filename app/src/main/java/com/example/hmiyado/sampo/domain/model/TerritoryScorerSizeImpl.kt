package com.example.hmiyado.sampo.domain.model

/**
 * Created by hmiyado on 2017/03/21.
 */
object TerritoryScorerSizeImpl : TerritoryScorer {
    override fun calcScore(territories: List<Territory>, territoryValidPeriod: TerritoryValidityPeriod): Double {
        return territories.map { calcScorePerTerritory(it, territoryValidPeriod) }.sum()
    }

    override fun calcScorePerTerritory(territory: Territory, territoryValidPeriod: TerritoryValidityPeriod): Double {
        return territory.locations.filter { territoryValidPeriod.isValid(it.timeStamp) }.size.toDouble()
    }
}