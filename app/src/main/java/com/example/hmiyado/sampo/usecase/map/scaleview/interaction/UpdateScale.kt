package com.example.hmiyado.sampo.usecase.map.scaleview.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.scaleview.UseScaleViewSink
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/26.
 *
 * MapStore　から ScaleViewOutput への相互作用
 */
class UpdateScale(
        private val store: MapStore,
        private val useScaleViewSink: UseScaleViewSink
) : Interaction() {
    init {
        subscriptions += mapInteraction()
        subscriptions += updateScale()
    }

    private fun mapInteraction(): Subscription =
            Observable.merge(
                    store.getScaleFactor(),
                    store.getRotateAngle(),
                    store.getOriginalLocation(),
                    store.getOrientation()
            ).subscribe { useScaleViewSink.draw() }

    private fun updateScale(): Subscription = store.getScaleFactor().subscribe { useScaleViewSink.setScale(it) }

}