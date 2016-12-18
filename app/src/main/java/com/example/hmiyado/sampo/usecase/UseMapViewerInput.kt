package com.example.hmiyado.sampo.usecase

import android.graphics.Canvas
import android.graphics.PointF
import com.example.hmiyado.sampo.domain.math.Geometry
import com.example.hmiyado.sampo.domain.math.toDegree
import com.example.hmiyado.sampo.presenter.MapViewPresenter
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable
import rx.lang.kotlin.PublishSubject

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
        val previousPointsSubject = PublishSubject<Pair<PointF, PointF>>()

        previousPointsSubject.subscribe()

        mapViewPresenter.getOnTouchEventSignal()
                .zipWith(mapViewPresenter.getOnScaleBeginSignal(), { event, scaleGestureDetector ->
                    event
                })
                .doOnNext {
                    val point1 = PointF(it.getX(0), it.getY(0))
                    val point2 = PointF(it.getX(0), it.getY(0))
                    previousPointsSubject.onNext(Pair(point1, point2))
                }
                .bindToLifecycle(mapViewPresenter.mapView)
                // ScaleGestureがBeginするたびにpointを初期化し直す
                .subscribe {
                }

        return mapViewPresenter.getOnTouchEventSignal()
                // ２点タップしていなければ，回転をとることはない
                .filter { it.pointerCount == 2 }
                .withLatestFrom(previousPointsSubject, { event, previousPoints ->
                    val nextPoint1 = PointF(event.getX(0), event.getY(0))
                    val nextPoint2 = PointF(event.getX(1), event.getY(1))
                    Pair(previousPoints, Pair(nextPoint1, nextPoint2))
                })
                .map {
                    val previousPoints = it.first
                    val nextPoints = it.second

                    previousPointsSubject.onNext(nextPoints)

                    Geometry.determineAngle(
                            previousPoints.first,
                            previousPoints.second,
                            nextPoints.first,
                            nextPoints.second).toDegree()
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