package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.Marker
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.MarkerProducer
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.BiFunction
import timber.log.Timber

/**
 * Created by hmiyado on 2017/04/05.
 */
class AddMarker(
        private val markerProducer: MarkerProducer,
        private val store: MapStore
) : Interaction<List<Marker>>() {
    override fun buildProducer(): Observable<List<Marker>> {
        return markerProducer.product
                .withLatestFrom(store.updatedMarkersSignal, BiFunction<Marker, List<Marker>, List<Marker>> { marker, list ->
                    list.plus(marker)
                })

    }

    override fun buildConsumer(): Observer<List<Marker>> {
        return DefaultObserver({
            Timber.d(it.toString())
            store.setMarkers(it)
        })
    }
}