package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Territory
import com.example.hmiyado.sampo.domain.model.TerritoryScorer
import com.example.hmiyado.sampo.domain.model.TerritoryValidityPeriod
import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Function3

/**
 * Created by hmiyado on 2017/03/22.
 */
class UpdateTerritory(
        private val mapStore: MapStore,
        private val scorer: TerritoryScorer
) : Interaction<List<Territory>>() {

    override fun buildProducer(): Observable<List<Territory>> {
        return mapStore.getOriginalLocation()
                .withLatestFrom(mapStore.getTerritories(), mapStore.getTerritoryValidityPeriod(), Function3(this::updateTerritories))
    }

    private fun updateTerritories(location: Location, territories: List<Territory>, territoryValidityPeriod: TerritoryValidityPeriod): List<Territory> {
        val queryLatitudeId = Territory.findLatitudeIdBy(location.latitude)
        val queryLongitudeId = Territory.findLongitudeIdBy(location.longitude)
        val foundTerritory = territories.find { it.latitudeId == queryLatitudeId && it.longitudeId == queryLongitudeId }
        if (foundTerritory == null) {
            val newTerritory = Territory(queryLatitudeId, queryLongitudeId, listOf(location), scorer, territoryValidityPeriod)
            return territories.plus(newTerritory)
        } else {
            val newTerritory = foundTerritory.addLocation(location)
            return territories.minus(foundTerritory).plus(newTerritory)
        }
    }

    override fun buildConsumer(): Observer<List<Territory>> {
        return DefaultObserver(mapStore::setTerritories)
    }
}