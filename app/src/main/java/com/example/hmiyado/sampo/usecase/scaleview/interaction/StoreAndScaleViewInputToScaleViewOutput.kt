package com.example.hmiyado.sampo.usecase.scaleview.interaction

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.usecase.scaleview.UseScaleViewInput
import com.example.hmiyado.sampo.usecase.scaleview.UseScaleViewOutput

/**
 * Created by hmiyado on 2016/12/24.
 */
class StoreAndScaleViewInputToScaleViewOutput(
        private val store: Store,
        private val scaleViewInput: UseScaleViewInput,
        private val scaleViewOutput: UseScaleViewOutput
) {
    init {
        drawInteraction()
    }

    private fun drawInteraction() {
        scaleViewOutput.setOnDrawSignal(
                store.getMapSignal(),
                scaleViewInput.getOnDrawCanvasSignal()
        )
    }
}