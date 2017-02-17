package com.example.hmiyado.sampo.usecase.map.compassview

import android.graphics.Canvas
import com.example.hmiyado.sampo.controller.CompassViewController
import com.example.hmiyado.sampo.domain.model.Orientation
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 方位磁針への出力
 */
class UseCompassViewOutput(
        private val compassViewController: CompassViewController
) {
    fun setOnOrientationSignal(orientationSignal: Observable<Orientation>): Subscription =
            orientationSignal
                    .doOnNext { compassViewController.invalidate() }
                    .bindToCompassView()
                    .subscribe()

    fun setOnDrawSignal(orientationSignal: Observable<Orientation>, onDrawSignal: Observable<Canvas>): Subscription =
            onDrawSignal
                    .withLatestFrom(orientationSignal, { canvas, orientation -> Pair(canvas, orientation) })
                    .doOnNext {
                        val canvas = it.first
                        val orientation = it.second

                        compassViewController.drawCompass(canvas, orientation)

//                        Timber.d(it.second.toString())
                    }
                    .bindToCompassView()
                    .subscribe()


    private fun <T> rx.Observable<T>.bindToCompassView() = compassViewController.bindToViewLifecycle(this)
}