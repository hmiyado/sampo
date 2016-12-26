package com.example.hmiyado.sampo.usecase.compassview.interaction

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.usecase.compassview.UseCompassViewOutput

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 状態の変化に応じて方位磁針に出力する
 */
class StoreToCompassViewOutputInteraction(
        private val store: Store,
        private val useCompassViewOutput: UseCompassViewOutput
) {
    init {
        orientationInteraction()
    }

    private fun orientationInteraction() {
        useCompassViewOutput.setOnOrientationSignal(store.getMapSignal().map { it.orientation })
    }
}