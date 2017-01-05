package com.example.hmiyado.sampo.usecase.scaleview.interaction

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.scaleview.UseScaleViewInput
import com.example.hmiyado.sampo.usecase.scaleview.UseScaleViewOutput
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/24.
 *
 * 縮尺のビューへの相互作用．
 */
class StoreAndScaleViewInputToScaleViewOutputInteraction(
        private val store: Store,
        private val scaleViewInput: UseScaleViewInput,
        private val scaleViewOutput: UseScaleViewOutput
) : Interaction() {
    init {
        subscriptions += drawInteraction()
    }

    private fun drawInteraction(): Subscription =
            scaleViewOutput.setOnDrawSignal(
                    store.getMapSignal(),
                    scaleViewInput.getOnDrawCanvasSignal()
            )

}