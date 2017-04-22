package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import org.threeten.bp.Instant
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2017/04/23.
 */
class UpdateValidityPeriodEnd(
        private val mapStore: MapStore
) : Interaction<Instant>() {
    override fun buildProducer(): Observable<Instant> {
        return Observable.interval(1, TimeUnit.SECONDS)
                .map {
                    Instant.now()
                }
    }

    override fun buildConsumer(): Observer<Instant> {
        return DefaultObserver({
            mapStore.setValidityPeriodEnd(it)
        })
    }
}