package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.repository.location.LocationRepository
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.event.Event
import com.hmiyado.sampo.usecase.event.EventBus
import com.hmiyado.sampo.usecase.event.LOCATION_UPDATED
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.BiFunction

/**
 * Created by hmiyado on 2016/12/20.
 * 位置情報取得サービスから状態を更新する
 */
class UpdateLocation(
        private val locationRepository: LocationRepository,
        private val store: MapStore
) : Interaction<List<Location>>() {
    override fun buildProducer(): Observable<List<Location>> {
        return EventBus.onEvent
                .filter { it == LOCATION_UPDATED }
                .withLatestFrom(store.getOriginalLocation(), BiFunction<Event, Location, Location> { _, location -> location })
                .map {
                    locationRepository.loadLocationList(it.timeStamp)
                }
    }

    override fun buildConsumer(): Observer<List<Location>> {
        return DefaultObserver({
            it.forEach {
                store.setOriginalLocation(it)
            }
        })
    }
}