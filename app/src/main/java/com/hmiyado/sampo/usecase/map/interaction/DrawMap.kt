package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.DrawableMap
import com.hmiyado.sampo.domain.model.SampoScorer
import com.hmiyado.sampo.domain.model.Territory
import com.hmiyado.sampo.domain.model.ValidityPeriod
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.UseMapView
import com.hmiyado.sampo.usecase.map.UseMapView.Sink.DrawableTerritories
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Function3

/**
 * Created by hmiyado on 2016/12/15.
 * ストアと地図ビューの入力から，地図ビューの出力する
 */
class DrawMap(
        private val store: MapStore,
        val scorer: SampoScorer,
        private val useMapViewSink: UseMapView.Sink
) : Interaction<DrawableTerritories>() {
    override fun buildProducer(): Observable<DrawableTerritories> {
        return Observable
                .combineLatest(
                        store.drawableMapSignal,
                        store.getTerritories(),
                        store.getValidityPeriod(),
                        // http://stackoverflow.com/questions/42725749/observable-combinelatest-type-inference-in-kotlin
                        Function3(this::toDrawableTarget))
    }

    private fun toDrawableTarget(drawableMap: DrawableMap, territories: List<Territory>, validityPeriod: ValidityPeriod): DrawableTerritories {
        val scores = scorer.run {
            territories.map {
                it.calcScore(validityPeriod)
            }
        }

        return DrawableTerritories(drawableMap, territories.zip(scores), scorer.maxTerritoryScore)
    }

    override fun buildConsumer(): Observer<DrawableTerritories> {
        return DefaultObserver(useMapViewSink::draw)
    }
}