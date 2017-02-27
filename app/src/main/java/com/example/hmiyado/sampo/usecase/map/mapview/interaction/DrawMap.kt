package com.example.hmiyado.sampo.usecase.map.mapview.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.mapview.UseMapViewSink
import com.example.hmiyado.sampo.usecase.map.mapview.UseMapViewSource
import com.example.hmiyado.sampo.usecase.map.store.MapStore

/**
 * Created by hmiyado on 2016/12/15.
 * ストアと地図ビューの入力から，地図ビューの出力する
 */
class DrawMap(
        private val store: MapStore,
        private val useMapViewSource: UseMapViewSource,
        private val useMapViewSink: UseMapViewSink
) : Interaction() {

    init {
        drawInteraction()
    }

    private fun drawInteraction() {
        subscriptions += useMapViewSink.setOnDrawSignal(store.getOriginalLocation(), store.getScaleFactor(), store.getRotateAngle(), store.getFootmarks(), useMapViewSource.getOnDrawSignal())
    }
}