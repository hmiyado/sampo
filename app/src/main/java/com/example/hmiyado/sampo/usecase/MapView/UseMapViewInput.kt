package com.example.hmiyado.sampo.usecase.MapView

import android.graphics.Canvas
import android.graphics.PointF
import android.view.MotionEvent
import com.example.hmiyado.sampo.domain.math.Geometry
import com.example.hmiyado.sampo.domain.math.Radian
import com.example.hmiyado.sampo.presenter.MapViewPresenter
import rx.Observable

/**
 * Created by hmiyado on 2016/12/10.
 * 地図閲覧からの入力を責務とする．
 *
 * 地図閲覧からの入力
 * - 回転角の変化
 * - 縮尺の変化
 */
class UseMapViewInput(
        private val mapViewPresenter: MapViewPresenter
) {
    /**
     * @return 地図をどれだけ回転させたか(radian)のシグナル
     */
    fun getOnRotateSignal(): Observable<Radian> {
        val getPointPairByEvent = { event: MotionEvent ->
            Pair(
                    PointF(event.getX(0), event.getY(0)),
                    PointF(event.getX(1), event.getY(1))
            )
        }

        var isRotating = false
        var previousPoints = Pair(PointF(0f, 0f), PointF(0f, 0f))

        return mapViewPresenter.getOnTouchEventSignal()
                // ２点タップしていなければ，回転をとることはない
                .filter {
                    if (it.pointerCount == 2) {
                        if (!isRotating) {
                            previousPoints = getPointPairByEvent(it)
                            isRotating = true
                        }
                        true
                    } else {
                        isRotating = false
                        false
                    }
                }
                .map {
                    val nextPoints = getPointPairByEvent(it)

                    val angle = Geometry.determineAngle(
                            previousPoints.first,
                            previousPoints.second,
                            nextPoints.first,
                            nextPoints.second)

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