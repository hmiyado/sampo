package com.example.hmiyado.sampo.domain.usecase.map

import com.example.hmiyado.sampo.controller.MapViewController
import com.example.hmiyado.sampo.presenter.MapViewPresenter

/**
 * Created by hmiyado on 2016/12/10.
 *
 * 地図の入力と出力をつなぐ．
 */
class UseMapViewerInteraction(
        private val useMapViewerInput: UseMapViewerInput,
        private val useMapViewerOutput: UseMapViewerOutput
) {

    companion object {
        fun create(mapViewPresenter: MapViewPresenter, mapViewController: MapViewController): UseMapViewerInteraction {
            return UseMapViewerInteraction(
                    UseMapViewerInput(mapViewPresenter),
                    UseMapViewerOutput(mapViewController)
            )
        }
    }

    init {
        rotationInteraction()
        scaleInteraction()
    }

    private fun rotationInteraction() {
        useMapViewerInput.getOnRotateSignal()
                .doOnNext {
                    useMapViewerOutput.rotateAngleDegree = it
                }
                .subscribe()

    }

    private fun scaleInteraction() {
        useMapViewerInput.getOnScaleSignal()
                .doOnNext { useMapViewerOutput.scaleFactor = it }
                .subscribe()
    }
}