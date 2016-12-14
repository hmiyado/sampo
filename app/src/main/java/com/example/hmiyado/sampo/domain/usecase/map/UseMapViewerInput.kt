package com.example.hmiyado.sampo.domain.usecase.map

import android.graphics.Canvas
import android.graphics.PointF
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
     * @return 地図が北から何度傾いているかのシグナル
     */
    fun getOnRotateSignal(): Observable<Float> {
        var rotateAngleDegree: Float = 0f
        var point1: PointF = PointF(0f, 0f)
        var point2: PointF = PointF(0f, 0f)

        mapViewPresenter.getOnTouchEventSignal()
                .zipWith(mapViewPresenter.getOnScaleBeginSignal(), { event, scaleGestureDetector ->
                    event
                })
                .bindToLifecycle(mapViewPresenter.mapView)
                // ScaleGestureがBeginするたびにpointを初期化し直す
                .subscribe {
                    point1 = PointF(it.getX(0), it.getY(0))
                    point2 = PointF(it.getX(0), it.getY(0))
                }

        return mapViewPresenter.getOnTouchEventSignal()
                // ２点タップしていなければ，回転をとることはない
                .filter { it.pointerCount == 2 }
                .map {
                    val nextPoint1 = PointF(it.getX(0), it.getY(0))
                    val nextPoint2 = PointF(it.getX(1), it.getY(1))

                    rotateAngleDegree += Geometry.determineAngle(point1, point2, nextPoint1, nextPoint2).toDegree()
                    Timber.d("rotate angle degree = $rotateAngleDegree")

                    point1 = nextPoint1
                    point2 = nextPoint2

                    rotateAngleDegree
                }
    }

    /**
     * @return 地図の初期状態に対する拡大率のシグナル
     */
    fun getOnScaleSignal(): Observable<Float> {
        var scaleFactor: Float = 1.0f

        return mapViewPresenter.getOnScaleSignal()
                .map {
                    scaleFactor *= it.scaleFactor
                    scaleFactor
                }
    }

    fun getOnDrawSignal(): Observable<Canvas> {
        return mapViewPresenter.getOnDrawSignal()
    }
}