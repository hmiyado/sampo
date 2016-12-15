package com.example.hmiyado.sampo.domain.usecase.map

import com.example.hmiyado.sampo.domain.store.Store

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
                .doOnNext { store.productScaleFactor(it) }
                .subscribe()
    }

}