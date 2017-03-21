package com.example.hmiyado.sampo.domain.model

/**
 * Created by hmiyado on 2017/03/21.
 */
interface TerritoryScorer {
    fun calcScore(locations: List<Location>, territoryValidPeriod: TerritoryValidityPeriod): Double
}