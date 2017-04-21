package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.repository.location.LocationRepository
import com.hmiyado.sampo.repository.location.LocationSensor
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.event.EventBus
import com.hmiyado.sampo.usecase.event.LOCATION_UPDATED
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2016/12/27.
 *
 * 変化があった状態から，地点を保存する相互作用
 */
class SaveLocation(
        private val locationSensor: LocationSensor,
        private val locationRepository: LocationRepository
) : Interaction<Location>() {
    override fun buildProducer(): Observable<Location> {
        return locationSensor.getLocationObservable()
    }

    override fun buildConsumer(): Observer<Location> {
        return DefaultObserver({
            locationRepository.saveLocation(it)
            EventBus.dispatch(LOCATION_UPDATED)
        })
    }

}