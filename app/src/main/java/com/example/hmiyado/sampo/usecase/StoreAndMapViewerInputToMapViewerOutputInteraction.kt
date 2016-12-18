package com.example.hmiyado.sampo.usecase

import com.example.hmiyado.sampo.domain.store.Store

/**
 * Created by hmiyado on 2016/12/15.
 * ストアと地図ビューの入力から，地図ビューの出力する
 */
class StoreAndMapViewerInputToMapViewerOutputInteraction(
        private val store: Store,
        private val useMapViewerInput: UseMapViewerInput,
        private val useMapViewerOutput: UseMapViewerOutput
) {

    init {
        drawInteraction()
    }

    private fun drawInteraction() {
        useMapViewerOutput.setOnDrawSignal(store.getOnUpdateMapSignal(), useMapViewerInput.getOnDrawSignal())
    }

}