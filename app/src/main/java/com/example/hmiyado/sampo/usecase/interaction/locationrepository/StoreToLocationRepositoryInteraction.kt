package com.example.hmiyado.sampo.usecase.interaction.locationrepository

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.repository.location.LocationRepository

/**
 * Created by hmiyado on 2016/12/27.
 *
 * 変化があった状態から，地点を保存する相互作用
 */
class StoreToLocationRepositoryInteraction(
        private val store: Store,
        private val locationRepository: LocationRepository
) {
    init {

    }

    private fun saveLocationInteraction() {
        // TODO
    }
}