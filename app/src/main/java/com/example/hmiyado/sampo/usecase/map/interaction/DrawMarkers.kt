package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseMarkerView
import com.example.hmiyado.sampo.usecase.map.UseMarkerView.Sink.DrawableMarkers
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.BiFunction

/**
 * Created by hmiyado on 2017/04/05.
 */
class DrawMarkers(
        private val store: MapStore,
        private val useMarkerSink: UseMarkerView.Sink
) : Interaction<DrawableMarkers>() {
    override fun buildProducer(): Observable<DrawableMarkers> {
        return Observable.combineLatest(
                store.drawableMapSignal,
                store.updatedMarkersSignal,
                BiFunction(::DrawableMarkers)
        )
    }

    override fun buildConsumer(): Observer<DrawableMarkers> {
        return DefaultObserver({
            useMarkerSink.draw(it)
        })
    }
}