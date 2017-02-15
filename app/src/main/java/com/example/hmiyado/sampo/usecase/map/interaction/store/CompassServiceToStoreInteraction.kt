package com.example.hmiyado.sampo.usecase.map.interaction.store

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.compass.CompassService
import com.example.hmiyado.sampo.usecase.Interaction

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 現在向いている方角から状態へのインタラクション
 */
class CompassServiceToStoreInteraction(
        private val compassService: CompassService,
        private val store: Store
) : Interaction() {
    init {
        subscriptions += compassInteraction()
    }

    private fun compassInteraction() =
            compassService.getCompassService()
                    .doOnNext { store.setOrientation(it) }
                    .subscribe()

}