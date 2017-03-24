package com.example.hmiyado.sampo.presenter.map

import android.graphics.PointF
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.example.hmiyado.sampo.domain.math.Geometry
import com.example.hmiyado.sampo.domain.math.Radian
import com.example.hmiyado.sampo.presenter.ViewPresenter
import com.example.hmiyado.sampo.usecase.map.UseMapView
import com.example.hmiyado.sampo.view.map.custom.MapView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * Created by hmiyado on 2016/12/04.
 * {@link MapView }に対応するPresenter
 */
class MapViewPresenter(
        mapView: MapView
) : ViewPresenter<MapView>(mapView), UseMapView.Source {
    private val scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(mapView.context, createGestureDetector())
    private val onScaleBeginSignalSubject = PublishSubject.create<ScaleGestureDetector>()
    private val onScaleSignalSubject = PublishSubject.create<ScaleGestureDetector>()

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

    override fun getOnScaleSignal(): Observable<Float> {
        return onScaleSignalSubject.share().map { 1 / it.scaleFactor }

    }

    fun getOnScaleBeginSignal(): Observable<ScaleGestureDetector> {
        return onScaleBeginSignalSubject.share()
    }

    /**
     * @return 地図をどれだけ回転させたか(radian)のシグナル
     */
    override fun getOnRotateSignal(): Observable<Radian> {
        val getPointPairByEvent = { event: MotionEvent ->
            Pair(
                    PointF(event.getX(0), event.getY(0)),
                    PointF(event.getX(1), event.getY(1))
            )
        }

        var isRotating = false
        var previousPoints = Pair(PointF(0f, 0f), PointF(0f, 0f))

        return getOnTouchEventSignal()
                .subscribeOn(AndroidSchedulers.mainThread())
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
                .share()
    }
}