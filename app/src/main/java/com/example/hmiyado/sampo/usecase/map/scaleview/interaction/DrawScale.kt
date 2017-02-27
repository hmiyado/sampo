package com.example.hmiyado.sampo.usecase.map.scaleview.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.scaleview.UseScaleViewSink
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/24.
 *
 * 縮尺のビューへの相互作用．
 */
class DrawScale(
        private val store: MapStore,
        private val scaleViewSink: UseScaleViewSink
) : Interaction() {
    init {
        subscriptions += drawInteraction()
    }

    private fun drawInteraction(): Subscription = store.getScaleFactor()
            .subscribe {
                scaleViewSink.setScale(it)
                scaleViewSink.draw()
            }
}