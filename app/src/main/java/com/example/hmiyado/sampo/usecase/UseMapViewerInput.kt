package com.example.hmiyado.sampo.usecase

import android.graphics.Canvas
import android.graphics.PointF
import android.view.MotionEvent
import com.example.hmiyado.sampo.domain.math.Geometry
import com.example.hmiyado.sampo.domain.math.toDegree
import com.example.hmiyado.sampo.presenter.MapViewPresenter
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable
import timber.log.Timber

/**
 * Created by hmiyado on 2016/12/10.
 * 地図閲覧からの入力を責務とする．
 *
 * 地図閲覧からの入力
 * - 回転角の変化
 * - 縮尺の変化
 */
class UseMapViewerInput(
        private val mapViewPresenter: MapViewPresenter
) {
    /**
     * @return 地図をどれだけ回転させたかのシグナル
     */
    fun getOnRotateSignal(): Observable<Float> {
        val getPointPairByEvent = { event: MotionEvent ->
            Pair(
                    PointF(event.getX(0), event.getY(0)),
                    PointF(event.getX(1), event.getY(1))
            )
        }

        var isScaleBegin = true
        var previousPoints = Pair(PointF(0f, 0f), PointF(0f, 0f))

        mapViewPresenter.getOnScaleBeginSignal()
                .doOnNext { Timber.d("on scale begin") }
                .doOnNext { Timber.d("with latest from: $it") }
                .doOnNext {
                    isScaleBegin = true
                }
                .bindToLifecycle(mapViewPresenter.mapView)
                // ScaleGestureがBeginするたびにpointを初期化し直す
                .subscribe {
                }

        return mapViewPresenter.getOnTouchEventSignal()
                .doOnNext { Timber.d("onTouchSignal") }
                // ２点タップしていなければ，回転をとることはない
                .filter { it.pointerCount == 2 }
                .doOnNext { Timber.d("filtered") }
                .doOnNext { Timber.d("latest from $it") }
                .filter {
                    if (isScaleBegin) {
                        previousPoints = getPointPairByEvent(it)
                        isScaleBegin = false
                        false
                    } else true
                }
                .map {
                    val nextPoints = getPointPairByEvent(it)

                    val angle = Geometry.determineAngle(
                            previousPoints.first,
                            previousPoints.second,
                            nextPoints.first,
                            nextPoints.second).toDegree()

                    previousPoints = nextPoints

                    angle
                }
    }

    /**
     * @return 拡大率のシグナル
     */
    fun getOnScaleSignal(): Observable<Float> {
        return mapViewPresenter.getOnScaleSignal()
                .map { it.scaleFactor }
    }

    fun getOnDrawSignal(): Observable<Canvas> {
        return mapViewPresenter.getOnDrawSignal()
    }
}