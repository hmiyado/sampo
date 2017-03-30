package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Territory
import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.BiFunction

/**
 * Created by hmiyado on 2017/03/22.
 */
class UpdateTerritory(
        private val mapStore: MapStore
) : Interaction<List<Territory>>() {
    companion object {
        fun updateTerritories(location: Location, territories: List<Territory>): List<Territory> {
            val queryLatitudeId = Territory.findLatitudeIdBy(location.latitude)
            val queryLongitudeId = Territory.findLongitudeIdBy(location.longitude)
            val foundTerritory = territories.find { it.latitudeId == queryLatitudeId && it.longitudeId == queryLongitudeId }
            if (foundTerritory == null) {
                val newTerritory = Territory(queryLatitudeId, queryLongitudeId, listOf(location))
                return territories.plus(newTerritory)
            } else {
                val newTerritory = foundTerritory.addLocation(location)
                return territories.minus(foundTerritory).plus(newTerritory)
            }
        }
    }

    override fun buildProducer(): Observable<List<Territory>> {
        return mapStore.getOriginalLocation()
                .withLatestFrom(mapStore.getTerritories(), BiFunction(UpdateTerritory.Companion::updateTerritories))
    }

    override fun buildConsumer(): Observer<List<Territory>> {
        return DefaultObserver(mapStore::setTerritories)
    }
}