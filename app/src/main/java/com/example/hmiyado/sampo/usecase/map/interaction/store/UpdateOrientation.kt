package com.example.hmiyado.sampo.usecase.map.interaction.store

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.compass.CompassService
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import timber.log.Timber

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 現在向いている方角から状態へのインタラクション
 */
class UpdateOrientation(
        private val compassService: CompassService,
        private val store: MapStore
) : Interaction() {
    init {
        subscriptions += compassInteraction()
    }

    private fun compassInteraction() =
            compassService.getCompassService()
                    .subscribe(
                            { store.setOrientation(it) },
                            { Timber.e(it, "error on get compass service") }
                    )

}