package com.hmiyado.sampo.usecase.result.interaction

import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.repository.location.LocationRepository
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2017/04/23.
 */
class LoadLocation(
        private val locationRepository: LocationRepository,
        private val mapStore: MapStore
) : Interaction<List<Location>>() {
    override fun buildProducer(): Observable<List<Location>> {
        return Observable.just(locationRepository.loadLocationList())
    }

    override fun buildConsumer(): Observer<List<Location>> {
        return DefaultObserver({
            it.sortedBy { it.timeStamp }.forEach {
                mapStore.setOriginalLocation(it)
            }
        })
    }
}