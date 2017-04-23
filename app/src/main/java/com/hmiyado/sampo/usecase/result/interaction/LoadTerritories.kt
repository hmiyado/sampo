package com.hmiyado.sampo.usecase.result.interaction

import com.hmiyado.sampo.domain.model.Territory
import com.hmiyado.sampo.repository.location.LocationRepository
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.interaction.UpdateTerritory
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2017/04/23.
 */
class LoadTerritories(
        private val locationRepository: LocationRepository,
        private val mapStore: MapStore
) : Interaction<List<Territory>>() {
    override fun buildProducer(): Observable<List<Territory>> {
        return Observable.just(locationRepository.loadLocationList())
                .map {
                    it.fold(emptyList<Territory>(), { territories, location ->
                        UpdateTerritory.updateTerritories(location, territories)
                    })
                }
    }

    override fun buildConsumer(): Observer<List<Territory>> {
        return DefaultObserver({
            mapStore.setTerritories(it)
        })
    }
}