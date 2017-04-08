package com.example.hmiyado.sampo.usecase.map

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Marker
import com.example.hmiyado.sampo.domain.model.ValidityPeriod
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.functions.Function3

/**
 * Created by hmiyado on 2017/04/08.
 */
class MarkerProducer(
        useMarkerAdder: UseMarkerAdder.Source,
        store: MapStore
) {
    val product: Observable<Marker> = useMarkerAdder.onClickedSignal
            .withLatestFrom(store.getOriginalLocation(),
                    store.getValidityPeriod(),
                    Function3<Unit, Location, ValidityPeriod, Marker> { _, location, validityPeriod ->
                        Marker(location, validityPeriod)
                    })
            .share()
}