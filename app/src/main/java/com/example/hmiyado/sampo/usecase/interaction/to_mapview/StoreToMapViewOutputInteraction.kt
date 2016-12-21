package com.example.hmiyado.sampo.usecase.interaction.to_mapview

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.usecase.MapView.UseMapViewOutput

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