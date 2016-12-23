package com.example.hmiyado.sampo.usecase.CompassView

import android.graphics.Canvas
import com.example.hmiyado.sampo.controller.CompassViewController
import com.example.hmiyado.sampo.domain.model.Orientation
import rx.Observable
import timber.log.Timber

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 方位磁針への出力
 */
class UseCompassViewOutput(
        private val compassViewController: CompassViewController
) {
    fun setOnOrientationSignal(orientationSignal: Observable<Orientation>) {
        orientationSignal
                .doOnNext { compassViewController.invalidate() }
                .bindToCompassView()
                .subscribe()
    }

    fun setOnDrawSignal(orientationSignal: Observable<Orientation>, onDrawSignal: Observable<Canvas>) {
        onDrawSignal
                .withLatestFrom(orientationSignal, { canvas, orientation -> Pair(canvas, orientation) })
                .doOnNext {
                    val canvas = it.first
                    val orientation = it.second

                    compassViewController.drawCompass(canvas, orientation)

                    Timber.d(it.second.toString())
                }
                .bindToCompassView()
                .subscribe()
    }

    private fun <T> rx.Observable<T>.bindToCompassView() = compassViewController.bindToCompassView(this)
}