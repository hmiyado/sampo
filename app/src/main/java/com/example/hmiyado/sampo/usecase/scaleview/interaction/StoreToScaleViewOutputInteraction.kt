package com.example.hmiyado.sampo.usecase.scaleview.interaction

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.scaleview.UseScaleViewOutput
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/26.
 *
 * Store　から ScaleViewOutput への相互作用
 */
class StoreToScaleViewOutputInteraction(
        private val store: Store,
        private val useScaleViewOutput: UseScaleViewOutput
) : Interaction() {
    init {
        subscriptions += mapInteraction()
    }

    private fun mapInteraction(): Subscription =
            useScaleViewOutput.setMapSignal(store.getMapSignal())

}