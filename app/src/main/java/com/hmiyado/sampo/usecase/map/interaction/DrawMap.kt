package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.*
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.UseMapView
import com.hmiyado.sampo.usecase.map.UseMapView.Sink.DrawableFootmarks
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Function4

/**
 * Created by hmiyado on 2016/12/15.
 * ストアと地図ビューの入力から，地図ビューの出力する
 */
class DrawMap(
        private val store: MapStore,
        val scorer: SampoScorer,
        private val useMapViewSink: UseMapView.Sink
) : Interaction<DrawableFootmarks>() {
    override fun buildProducer(): Observable<DrawableFootmarks> {
        return Observable
                .combineLatest(
                        store.drawableMapSignal,
                        store.getFootmarks(),
                        store.getTerritories(),
                        store.getValidityPeriod(),
                        // http://stackoverflow.com/questions/42725749/observable-combinelatest-type-inference-in-kotlin
                        Function4(this::toDrawableTarget))
    }

    private fun toDrawableTarget(drawableMap: DrawableMap, footmarks: List<Location>, territories: List<Territory>, validityPeriod: ValidityPeriod): DrawableFootmarks {
        return DrawableFootmarks(drawableMap, footmarks, territories, validityPeriod, scorer)
    }

    override fun buildConsumer(): Observer<DrawableFootmarks> {
        return DefaultObserver(useMapViewSink::draw)
    }
}