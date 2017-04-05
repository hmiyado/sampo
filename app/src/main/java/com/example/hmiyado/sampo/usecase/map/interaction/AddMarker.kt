package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Marker
import com.example.hmiyado.sampo.domain.model.ValidityPeriod
import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseMarkerAdder
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import timber.log.Timber

/**
 * Created by hmiyado on 2017/04/05.
 */
class AddMarker(
        private val useMarkerAdder: UseMarkerAdder.Source,
        private val store: MapStore
) : Interaction<List<Marker>>() {
    override fun buildProducer(): Observable<List<Marker>> {
        return useMarkerAdder.onClickedSignal
                .withLatestFrom(store.getOriginalLocation(), store.getValidityPeriod(), Function3<Unit, Location, ValidityPeriod, Marker> { _, location, validityPeriod ->
                    Marker(location, ValidityPeriod.create(start = location.timeStamp, period = validityPeriod.period))
                })
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