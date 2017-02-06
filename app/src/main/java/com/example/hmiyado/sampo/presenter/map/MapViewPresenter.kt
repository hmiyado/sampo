package com.example.hmiyado.sampo.presenter.map

import android.graphics.Canvas
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.example.hmiyado.sampo.presenter.ViewPresenter
import com.example.hmiyado.sampo.view.map.custom.MapView
import rx.Observable
import rx.lang.kotlin.PublishSubject
import timber.log.Timber

/**
 * Created by hmiyado on 2016/12/04.
 * {@link MapView }に対応するPresenter
 */
class MapViewPresenter(
        mapView: MapView
) : ViewPresenter<MapView>(mapView) {
    private val scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(mapView.context, createGestureDetector())
    private val onScaleBeginSignalSubject = PublishSubject<ScaleGestureDetector>()
    private val onScaleSignalSubject = PublishSubject<ScaleGestureDetector>()

    private fun createGestureDetector(): ScaleGestureDetector.OnScaleGestureListener {
        return object : ScaleGestureDetector.OnScaleGestureListener {
            override fun onScaleBegin(p0: ScaleGestureDetector?): Boolean {
                Timber.d("onScaleStart")
                p0 ?: return true

                onScaleBeginSignalSubject.onNext(p0)
                return true
            }

            override fun onScaleEnd(p0: ScaleGestureDetector?) {
                Timber.d("onScaleEnd")
                return
            }

            override fun onScale(p0: ScaleGestureDetector?): Boolean {
                p0 ?: return true

                onScaleSignalSubject.onNext(p0)

                return true
            }
        }
    }

    fun getOnTouchEventSignal(): Observable<MotionEvent> {
        return view.getOnTouchSignal()
                .doOnNext { scaleGestureDetector.onTouchEvent(it) }
                .share()
    }

    fun getOnScaleSignal(): Observable<ScaleGestureDetector> {
        return onScaleSignalSubject.share()
    }

    fun getOnScaleBeginSignal(): Observable<ScaleGestureDetector> {
        return onScaleBeginSignalSubject.share()
    }

    fun getOnDrawSignal(): Observable<Canvas> {
        return view.getOnDrawSignal()
    }
}