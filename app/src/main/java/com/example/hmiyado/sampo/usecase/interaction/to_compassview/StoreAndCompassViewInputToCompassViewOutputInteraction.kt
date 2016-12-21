package com.example.hmiyado.sampo.usecase.interaction.to_compassview

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.usecase.CompassView.UseCompassViewInput
import com.example.hmiyado.sampo.usecase.CompassView.UseCompassViewOutput

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 状態と方位磁針の入力から，方位磁針へ出力する
 */
class StoreAndCompassViewInputToCompassViewOutputInteraction(
        private val store: Store,
        private val useCompassViewInput: UseCompassViewInput,
        private val useCompassViewOutput: UseCompassViewOutput
) {
    init {
        drawInteraction()
    }

    private fun drawInteraction() {
        useCompassViewOutput.setOnDrawSignal(store.getMapSignal().map { it.orientation }, useCompassViewInput.getOnDrawSignal())
    }
}