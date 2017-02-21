package com.example.hmiyado.sampo.usecase.map.scaleview

import android.graphics.Canvas
import com.example.hmiyado.sampo.controller.ScaleViewController
import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Orientation
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/24.
 *
 * 縮尺のビューを描画する人
 */
class UseScaleViewOutput(private val scaleViewController: ScaleViewController) {
    fun setMapSignal(
            locationSignal: Observable<Location>,
            orientationSignal: Observable<Orientation>,
            rotateAngleSignal: Observable<Degree>,
            scaleSignal: Observable<Float>
    ): Subscription =
            Observable
                    .merge(
                            locationSignal,
                            scaleSignal,
                            orientationSignal,
                            rotateAngleSignal
                    )
                    .bindToViewLifecycle()
                    .subscribe({ scaleViewController.invalidate() })


    fun setOnDrawSignal(scaleSignal: Observable<Float>, drawSignal: Observable<Canvas>): Subscription =
            drawSignal
                    .withLatestFrom(scaleSignal, { canvas, scale -> Pair(canvas, scale) })
                    .doOnNext {
                        val canvas = it.first
                        val scale = it.second
                        //                        Timber.d("$map")
                        scaleViewController.drawScale(canvas, scale)
                    }
                    .bindToViewLifecycle()
                    .subscribe()

    fun <T> rx.Observable<T>.bindToViewLifecycle(): Observable<T> {
        return scaleViewController.bindToViewLifecycle(this)
    }
}