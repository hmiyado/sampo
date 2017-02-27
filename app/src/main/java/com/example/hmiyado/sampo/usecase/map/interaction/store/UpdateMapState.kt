package com.example.hmiyado.sampo.usecase.map.interaction.store

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.mapview.UseMapViewSource
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import rx.Subscription
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2016/12/10.
 *
 * 地図の入力と出力をつなぐ．
 */
class UpdateMapState(
        private val useMapViewSource: UseMapViewSource,
        private val store: MapStore
) : Interaction() {

    init {
        subscriptions += rotationInteraction()
        subscriptions += scaleInteraction()
    }

    private fun rotationInteraction(): Subscription {
        return useMapViewSource.getOnRotateSignal()
                .doOnNext { store.setRotateAngle(it.toDegree()) }
                .subscribe()

    }

    private fun scaleInteraction(): Subscription {
        return useMapViewSource.getOnScaleSignal()
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .doOnNext { store.setScaleFactor(it) }
                .subscribe()
    }

}