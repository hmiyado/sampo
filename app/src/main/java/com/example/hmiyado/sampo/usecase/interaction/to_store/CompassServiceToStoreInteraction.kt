package com.example.hmiyado.sampo.usecase.interaction.to_store

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.repository.compass.CompassService

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 現在向いている方角から状態へのインタラクション
 */
class CompassServiceToStoreInteraction(
        private val compassService: CompassService,
        private val store: Store
) {
    init {
        compassInteraction()
    }

    private fun compassInteraction() {
        compassService.getCompassService()
                .doOnNext { store.setOrientation(it) }
                .subscribe()
    }
}