package com.example.hmiyado.sampo.usecase.map.mapview.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.mapview.UseMapViewSink
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/15.
 * ストアから地図出力へのインタラクション
 */
class UpdateMap(
        private val store: MapStore,
        private val useMapViewSink: UseMapViewSink
) : Interaction() {

    init {
        subscriptions += mapInteraction()
    }

    private fun mapInteraction(): Subscription {
        return useMapViewSink.setOnUpdateMapSignal(store.getOriginalLocation(), store.getScaleFactor(), store.getRotateAngle())
    }
}