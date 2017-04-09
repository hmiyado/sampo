package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.BiFunction

/**
 * Created by hmiyado on 2017/03/25.
 */
class UpdateFootmarks(
        val mapStore: MapStore
) : Interaction<List<Location>>() {
    override fun buildProducer(): Observable<List<Location>> {
        return mapStore.getOriginalLocation()
                .filter { it != Location.empty() }
                .withLatestFrom(mapStore.getFootmarks(), BiFunction(this::updatedFootmarks))
    }

    private fun updatedFootmarks(location: Location, footmarks: List<Location>): List<Location> {
        return footmarks.plus(location)
    }

    override fun buildConsumer(): Observer<List<Location>> {
        return DefaultObserver<List<Location>>(mapStore::setFootmarks)
    }
}