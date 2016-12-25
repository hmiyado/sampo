package com.example.hmiyado.sampo.usecase.interaction.to_scaleview

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.usecase.scaleview.UseScaleViewOutput

/**
 * Created by hmiyado on 2016/12/26.
 *
 * Store　から ScaleViewOutput への相互作用
 */
class StoreToScaleViewOutputInteraction(
        private val store: Store,
        private val useScaleViewOutput: UseScaleViewOutput
) {
    init {
        mapInteraction()
    }

    private fun mapInteraction() {
        useScaleViewOutput.setMapSignal(store.getMapSignal())
    }
}