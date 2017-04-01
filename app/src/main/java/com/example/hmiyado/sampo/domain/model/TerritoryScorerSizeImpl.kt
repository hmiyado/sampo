package com.example.hmiyado.sampo.domain.model

/**
 * Created by hmiyado on 2017/03/21.
 */
object TerritoryScorerSizeImpl : TerritoryScorer {
    override fun calcScore(territories: List<Territory>, validPeriod: ValidityPeriod): Double {
        return territories.map { calcScorePerTerritory(it, validPeriod) }.sum()
    }

    override fun calcScorePerTerritory(territory: Territory, validPeriod: ValidityPeriod): Double {
        return territory.locations.filter { validPeriod.isValid(it.timeStamp) }.size.toDouble()
    }
}