package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.domain.math.Measurement
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseMapView
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/15.
 * ストアと地図ビューの入力から，地図ビューの出力する
 */
class DrawMap(
        private val store: MapStore,
        measurement: Measurement,
        private val useMapViewSink: UseMapView.Sink
) : Interaction() {

    init {
        useMapViewSink.setMeasurement(measurement)
        subscriptions += drawInteraction()
    }

    private fun drawInteraction(): Subscription = Observable
            .combineLatest(
                    store.getOriginalLocation(),
                    store.getScaleFactor(),
                    store.getRotateAngle(),
                    store.getFootmarks(), { originalLocation, scaleFactor, rotateAngle, footmarks ->
                UseMapView.Sink.DrawableMap(originalLocation, scaleFactor, rotateAngle, footmarks)
            })
            .subscribe { useMapViewSink.draw(it) }
}