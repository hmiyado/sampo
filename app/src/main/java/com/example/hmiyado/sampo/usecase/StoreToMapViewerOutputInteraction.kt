package com.example.hmiyado.sampo.usecase

import com.example.hmiyado.sampo.domain.store.Store

/**
 * Created by hmiyado on 2016/12/15.
 * ストアから地図出力へのインタラクション
 */
class StoreToMapViewerOutputInteraction(
        private val store: Store,
        private val useMapViewerOutput: UseMapViewerOutput
) {

    init {
        mapInteraction()
    }

    private fun mapInteraction() {
        useMapViewerOutput.setOnUpdateMapSignal(store.getOnUpdateMapSignal())
    }
}