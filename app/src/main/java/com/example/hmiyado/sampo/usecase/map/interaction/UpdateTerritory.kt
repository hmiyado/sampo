package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.domain.math.toDegree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Territory
import com.example.hmiyado.sampo.domain.model.TerritoryScorer
import com.example.hmiyado.sampo.domain.model.TerritoryValidityPeriod
import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.UseCase
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import rx.Observable
import rx.Observer

/**
 * Created by hmiyado on 2017/03/22.
 */
class UpdateTerritory(
        private val mapStore: MapStore,
        private val scorer: TerritoryScorer
) : UseCase<List<Territory>>() {

    override fun buildProducer(): Observable<List<Territory>> {
        return mapStore.getOriginalLocation()
                .withLatestFrom(mapStore.getTerritories(), mapStore.getTerritoryValidityPeriod(), this::updateTerritories)
    }

    private fun updateTerritories(location: Location, territories: List<Territory>, territoryValidityPeriod: TerritoryValidityPeriod): List<Territory> {
        val queryLatitudeId = Territory.findLatitudeIdBy(location.latitude.toDegree())
        val queryLongitudeId = Territory.findLongitudeIdBy(location.longitude.toDegree())
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