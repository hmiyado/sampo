package com.example.hmiyado.sampo.usecase.map.scaleview.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.scaleview.UseScaleViewInput
import com.example.hmiyado.sampo.usecase.map.scaleview.UseScaleViewOutput
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/24.
 *
 * 縮尺のビューへの相互作用．
 */
class StoreAndScaleViewInputToScaleViewOutputInteraction(
        private val store: MapStore,
        private val scaleViewInput: UseScaleViewInput,
        private val scaleViewOutput: UseScaleViewOutput
) : Interaction() {
    init {
        subscriptions += drawInteraction()
    }

    private fun drawInteraction(): Subscription =
            scaleViewOutput.setOnDrawSignal(
                    store.getScaleFactor(),
                    scaleViewInput.getOnDrawCanvasSignal()
            )

}