package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseMarkerAdder
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.BiFunction

/**
 * Created by hmiyado on 2017/04/08.
 */
class SetMarkerAdderAvailability(
        private val store: MapStore,
        private val useMarkerAdder: UseMarkerAdder.Sink
) : Interaction<Boolean>() {
    override fun buildProducer(): Observable<Boolean> {
        return store.updatedMarkersSignal
                .withLatestFrom(store.updatedMarkerLimitSignal, BiFunction { markers, limit ->
                    markers.size < limit
                })
    }

    override fun buildConsumer(): Observer<Boolean> {
        return DefaultObserver({
            useMarkerAdder.setEnabled(it)
        })
    }
}