package com.example.hmiyado.sampo.usecase.mapview.interaction

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.usecase.mapview.UseMapViewInput
import com.example.hmiyado.sampo.usecase.mapview.UseMapViewOutput

/**
 * Created by hmiyado on 2016/12/15.
 * ストアと地図ビューの入力から，地図ビューの出力する
 */
class StoreAndMapViewInputToMapViewOutputInteraction(
        private val store: Store,
        private val useMapViewInput: UseMapViewInput,
        private val useMapViewOutput: UseMapViewOutput
) {

    init {
        drawInteraction()
    }

    private fun drawInteraction() {
        useMapViewOutput.setOnDrawSignal(store.getMapSignal(), useMapViewInput.getOnDrawSignal())
    }

}