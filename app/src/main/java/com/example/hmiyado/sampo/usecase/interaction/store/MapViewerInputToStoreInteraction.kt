package com.example.hmiyado.sampo.usecase.interaction.store

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.mapview.UseMapViewInput
import rx.Subscription
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2016/12/10.
 *
 * 地図の入力と出力をつなぐ．
 */
class MapViewerInputToStoreInteraction(
        private val useMapViewInput: UseMapViewInput,
        private val store: Store
) : Interaction() {

    init {
        subscriptions += rotationInteraction()
        subscriptions += scaleInteraction()
    }

    private fun rotationInteraction(): Subscription {
        return useMapViewInput.getOnRotateSignal()
                .doOnNext { store.addRotateAngle(it.toDegree()) }
                .subscribe()

    }

    private fun scaleInteraction(): Subscription {
        return useMapViewInput.getOnScaleSignal()
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .doOnNext { store.productScaleFactor(it) }
                .subscribe()
    }

}