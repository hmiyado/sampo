package com.example.hmiyado.sampo.usecase

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.repository.location.LocationService

/**
 * Created by hmiyado on 2016/12/20.
 * 位置情報取得サービスから状態を更新する
 */
class LocationServiceToStoreInteraction(
        private val locationService: LocationService,
        private val store: Store
) {
    init {
        updateOriginalLocation()
    }

    private fun updateOriginalLocation() {
        locationService.getLocationObservable()
                .doOnNext { store.setOriginalLocation(it) }
                .subscribe()
    }
}