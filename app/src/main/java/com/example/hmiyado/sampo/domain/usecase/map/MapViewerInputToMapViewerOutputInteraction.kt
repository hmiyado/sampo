package com.example.hmiyado.sampo.domain.usecase.map

/**
 * Created by hmiyado on 2016/12/15.
 */
class MapViewerInputToMapViewerOutputInteraction(
        private val useMapViewerInput: UseMapViewerInput,
        private val useMapViewerOutput: UseMapViewerOutput
) {

    init {
        drawInteraction()
    }

    private fun drawInteraction() {
        useMapViewerOutput.setOnDrawSignal(useMapViewerInput.getOnDrawSignal())
    }

}