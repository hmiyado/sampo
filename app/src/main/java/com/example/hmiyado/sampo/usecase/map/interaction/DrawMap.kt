package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.math.Measurement
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Territory
import com.example.hmiyado.sampo.domain.model.TerritoryScorer
import com.example.hmiyado.sampo.domain.model.TerritoryValidityPeriod
import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseMapView
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Function6

/**
 * Created by hmiyado on 2016/12/15.
 * ストアと地図ビューの入力から，地図ビューの出力する
 */
class DrawMap(
        private val store: MapStore,
        measurement: Measurement,
        val scorer: TerritoryScorer,
        private val useMapViewSink: UseMapView.Sink
) : Interaction<UseMapView.Sink.DrawableMap>() {
    override fun buildProducer(): Observable<UseMapView.Sink.DrawableMap> {
        return Observable
                .combineLatest(
                        store.getOriginalLocation(),
                        store.getScaleFactor(),
                        store.getRotateAngle(),
                        store.getFootmarks(),
                        store.getTerritories(),
                        store.getTerritoryValidityPeriod(),
                        // http://stackoverflow.com/questions/42725749/observable-combinelatest-type-inference-in-kotlin
                        Function6(this::toDrawableMap))
    }

    private fun toDrawableMap(originalLocation: Location, scaleFactor: Float, rotateAngle: Degree, footmarks: List<Location>, territories: List<Territory>, validityPeriod: TerritoryValidityPeriod): UseMapView.Sink.DrawableMap {
        return UseMapView.Sink.DrawableMap(originalLocation, scaleFactor, rotateAngle, footmarks, territories, scorer, validityPeriod)
    }

    override fun buildConsumer(): Observer<UseMapView.Sink.DrawableMap> {
        return DefaultObserver(useMapViewSink::draw)
    }

    init {
        useMapViewSink.setMeasurement(measurement)
    }
}