package com.example.hmiyado.sampo.domain.model

/**
 * Created by hmiyado on 2017/03/21.
 */
interface TerritoryScorer {
    fun calcScore(territories: List<Territory>, territoryValidPeriod: TerritoryValidityPeriod): Double
    fun calcScorePerTerritory(territory: Territory, territoryValidPeriod: TerritoryValidityPeriod): Double
}