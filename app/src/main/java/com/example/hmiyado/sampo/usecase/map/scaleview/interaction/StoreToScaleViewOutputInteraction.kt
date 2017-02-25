package com.example.hmiyado.sampo.usecase.map.scaleview.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.scaleview.UseScaleViewOutput
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/26.
 *
 * MapStoreImpl　から ScaleViewOutput への相互作用
 */
class StoreToScaleViewOutputInteraction(
        private val store: MapStore,
        private val useScaleViewOutput: UseScaleViewOutput
) : Interaction() {
    init {
        subscriptions += mapInteraction()
    }

    private fun mapInteraction(): Subscription =
            useScaleViewOutput.setMapSignal(store.getOriginalLocation(), store.getOrientation(), store.getRotateAngle(), store.getScaleFactor())

}