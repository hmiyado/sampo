package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.Area
import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.domain.model.Territory
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.store.MapStore
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
            val queryArea = Area(location)
            val foundTerritory = territories.find { it.area == queryArea }
            if (foundTerritory == null) {
                val newTerritory = Territory(queryArea, listOf(location))
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