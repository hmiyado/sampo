package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.repository.location.LocationRepository
import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2016/12/27.
 *
 * 変化があった状態から，地点を保存する相互作用
 */
class SaveLocation(
        private val store: MapStore,
        private val locationRepository: LocationRepository
) : Interaction<Location>() {
    override fun buildProducer(): Observable<Location> {
        return store.getOriginalLocation()
    }

    override fun buildConsumer(): Observer<Location> {
        return DefaultObserver(locationRepository::saveLocation)
    }

}