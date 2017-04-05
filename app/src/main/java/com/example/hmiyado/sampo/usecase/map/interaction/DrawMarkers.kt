package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.domain.model.Marker
import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseMarkerView
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2017/04/05.
 */
class DrawMarkers(
        private val store: MapStore,
        private val useMarkerSink: UseMarkerView.Sink
) : Interaction<List<Marker>>() {
    override fun buildProducer(): Observable<List<Marker>> {
        return store.updatedMarkersSignal
    }

    override fun buildConsumer(): Observer<List<Marker>> {
        return DefaultObserver({
            useMarkerSink.draw(it)
        })
    }
}