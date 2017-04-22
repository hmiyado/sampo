package com.hmiyado.sampo.usecase.map

import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.domain.model.Marker
import com.hmiyado.sampo.domain.model.ValidityPeriod
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.functions.Function3
import org.threeten.bp.Instant
import org.threeten.bp.Period

/**
 * Created by hmiyado on 2017/04/08.
 */
class MarkerProducer(
        useMarkerAdder: UseMarkerAdder.Source,
        store: MapStore
) {
    val product: Observable<Marker> = useMarkerAdder.onClickedSignal
            .withLatestFrom(store.getOriginalLocation(),
                    store.updatedValidityPeriodEndSignal,
                    Function3<Unit, Location, Instant, Marker> { _, location, validityPeriodEnd ->
                        Marker(location, ValidityPeriod.create(Period.ofDays(1), end = validityPeriodEnd))
                    })
            .share()
}