package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.location.LocationSensor
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.store.MapStore

/**
 * Created by hmiyado on 2016/12/20.
 * 位置情報取得サービスから状態を更新する
 */
class UpdateLocation(
        private val locationSensor: LocationSensor,
        private val store: MapStore
) : Interaction() {
    init {
        subscriptions += updateOriginalLocation()
    }

    private fun updateOriginalLocation() =
            locationSensor.getLocationObservable()
                    .doOnNext { store.setOriginalLocation(it) }
                    .subscribe()

}