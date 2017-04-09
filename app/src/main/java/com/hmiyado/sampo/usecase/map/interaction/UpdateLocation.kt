package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.repository.location.LocationSensor
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2016/12/20.
 * 位置情報取得サービスから状態を更新する
 */
class UpdateLocation(
        private val locationSensor: LocationSensor,
        private val store: MapStore
) : Interaction<Location>() {
    override fun buildProducer(): Observable<Location> {
        return locationSensor.getLocationObservable()
    }

    override fun buildConsumer(): Observer<Location> {
        return DefaultObserver(store::setOriginalLocation)
    }
}