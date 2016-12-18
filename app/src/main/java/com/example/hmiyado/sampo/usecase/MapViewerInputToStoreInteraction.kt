package com.example.hmiyado.sampo.usecase

import com.example.hmiyado.sampo.domain.store.Store
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2016/12/10.
 *
 * 地図の入力と出力をつなぐ．
 */
class MapViewerInputToStoreInteraction(
        private val useMapViewerInput: UseMapViewerInput,
        private val store: Store
) {

    init {
        rotationInteraction()
        scaleInteraction()
    }

    private fun rotationInteraction() {
        useMapViewerInput.getOnRotateSignal()
                .doOnNext { store.addRotateAngle(it) }
                .subscribe()

    }

    private fun scaleInteraction() {
        useMapViewerInput.getOnScaleSignal()
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .doOnNext { store.productScaleFactor(it) }
                .subscribe()
    }

}