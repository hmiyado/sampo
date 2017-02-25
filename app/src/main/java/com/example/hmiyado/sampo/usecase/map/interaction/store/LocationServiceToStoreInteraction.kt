package com.example.hmiyado.sampo.usecase.map.interaction.store

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.location.LocationService
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.store.MapStore

/**
 * Created by hmiyado on 2016/12/20.
 * 位置情報取得サービスから状態を更新する
 */
class LocationServiceToStoreInteraction(
        private val locationService: LocationService,
        private val store: MapStore
) : Interaction() {
    init {
        subscriptions += updateOriginalLocation()
    }

    private fun updateOriginalLocation() =
            locationService.getLocationObservable()
                    .doOnNext { store.setOriginalLocation(it) }
                    .subscribe()

}