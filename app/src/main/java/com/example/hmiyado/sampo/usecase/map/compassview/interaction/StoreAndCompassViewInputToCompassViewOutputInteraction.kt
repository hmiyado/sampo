package com.example.hmiyado.sampo.usecase.map.compassview.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.compassview.UseCompassViewInput
import com.example.hmiyado.sampo.usecase.map.compassview.UseCompassViewOutput
import com.example.hmiyado.sampo.usecase.map.store.MapStore

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 状態と方位磁針の入力から，方位磁針へ出力する
 */
class StoreAndCompassViewInputToCompassViewOutputInteraction(
        private val store: MapStore,
        private val useCompassViewInput: UseCompassViewInput,
        private val useCompassViewOutput: UseCompassViewOutput
) : Interaction() {
    init {
        subscriptions += drawInteraction()
    }

    private fun drawInteraction() =
            useCompassViewOutput.setOnDrawSignal(store.getOrientation(), useCompassViewInput.getOnDrawSignal())

}