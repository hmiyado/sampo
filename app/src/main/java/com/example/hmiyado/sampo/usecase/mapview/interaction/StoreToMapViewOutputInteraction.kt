package com.example.hmiyado.sampo.usecase.mapview.interaction

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.usecase.mapview.UseMapViewOutput

/**
 * Created by hmiyado on 2016/12/15.
 * ストアから地図出力へのインタラクション
 */
class StoreToMapViewOutputInteraction(
        private val store: Store,
        private val useMapViewOutput: UseMapViewOutput
) {

    init {
        mapInteraction()
    }

    private fun mapInteraction() {
        useMapViewOutput.setOnUpdateMapSignal(store.getMapSignal())
    }
}