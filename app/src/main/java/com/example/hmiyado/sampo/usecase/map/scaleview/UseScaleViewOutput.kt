package com.example.hmiyado.sampo.usecase.map.scaleview

import android.graphics.Canvas
import com.example.hmiyado.sampo.controller.ScaleViewController
import com.example.hmiyado.sampo.domain.model.Map
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/24.
 *
 * 縮尺のビューを描画する人
 */
class UseScaleViewOutput(private val scaleViewController: ScaleViewController) {
    fun setMapSignal(mapSignal: Observable<Map>): Subscription =
            mapSignal
                    .doOnNext { scaleViewController.invalidate() }
                    .bindToViewLifecycle()
                    .subscribe()


    fun setOnDrawSignal(mapSignal: Observable<Map>, drawSignal: Observable<Canvas>): Subscription =
            drawSignal
                    .withLatestFrom(mapSignal, { canvas, map -> Pair(canvas, map) })
                    .doOnNext {
                        val canvas = it.first
                        val map = it.second
//                        Timber.d("$map")
                        scaleViewController.drawScale(canvas, map.scale)
                    }
                    .bindToViewLifecycle()
                    .subscribe()

    fun <T> rx.Observable<T>.bindToViewLifecycle(): Observable<T> {
        return scaleViewController.bindToViewLifecycle(this)
    }
}