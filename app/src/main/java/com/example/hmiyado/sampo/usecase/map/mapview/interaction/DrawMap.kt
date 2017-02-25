package com.example.hmiyado.sampo.usecase.map.mapview.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.mapview.UseMapViewInput
import com.example.hmiyado.sampo.usecase.map.mapview.UseMapViewOutput
import com.example.hmiyado.sampo.usecase.map.store.MapStore

/**
 * Created by hmiyado on 2016/12/15.
 * ストアと地図ビューの入力から，地図ビューの出力する
 */
class DrawMap(
        private val store: MapStore,
        private val useMapViewInput: UseMapViewInput,
        private val useMapViewOutput: UseMapViewOutput
) : Interaction() {

    init {
        drawInteraction()
    }

    private fun drawInteraction() {
        subscriptions += useMapViewOutput.setOnDrawSignal(store.getOriginalLocation(), store.getScaleFactor(), store.getRotateAngle(), store.getFootmarks(), useMapViewInput.getOnDrawSignal())
    }
}