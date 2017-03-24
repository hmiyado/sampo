package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseScaleView
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2016/12/24.
 *
 * 縮尺のビューへの相互作用．
 */
class DrawScale(
        private val store: MapStore,
        private val scaleViewSink: UseScaleView.Sink
) : Interaction<Float>() {
    override fun buildProducer(): Observable<Float> {
        return store.getScaleFactor()
    }

    override fun buildConsumer(): Observer<Float> {
        return DefaultObserver({
            scaleViewSink.setScale(it)
            scaleViewSink.draw()
        })
    }
}