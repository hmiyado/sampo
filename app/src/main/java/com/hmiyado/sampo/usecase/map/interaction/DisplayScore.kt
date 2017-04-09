package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.SampoScorer
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.UseScoreView
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Function3

/**
 * Created by hmiyado on 2017/04/08.
 */
class DisplayScore(
        private val store: MapStore,
        private val scorer: SampoScorer,
        private val useScoreViewSink: UseScoreView.Sink
) : Interaction<Double>() {
    override fun buildProducer(): Observable<Double> {
        return Observable.combineLatest(
                store.getTerritories(),
                store.updatedMarkersSignal,
                store.getValidityPeriod(),
                Function3 { territories, markers, validityPeriod ->
                    scorer.calcScore(territories, markers, validityPeriod)
                }
        )
    }

    override fun buildConsumer(): Observer<Double> {
        return DefaultObserver({
            useScoreViewSink.setScore(it)
        })
    }
}