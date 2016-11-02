package com.example.hmiyado.sampo.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View
import android.view.ViewManager
import com.example.hmiyado.sampo.domain.math.Geometry
import com.example.hmiyado.sampo.domain.math.toDegree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.android.withContext
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import org.jetbrains.anko.custom.ankoView
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.*


// settings for anko custom view
inline fun ViewManager.mapView(theme: Int = 0) = mapView(theme) {}
inline fun ViewManager.mapView(theme: Int = 0, init: MapView.() -> Unit) = ankoView({ MapView(it) }, theme, init)

/**
 * Created by hmiyado on 2016/09/23.
 * 位置情報を描画する
 * ピンチによる拡大縮小・回転　に対応する
 */
class MapView(context: Context): View(context), LazyKodeinAware {
    override val kodein = LazyKodein(appKodein)
    private val paintMapPoint: Paint = getPaintMapPoint()
    private val UseLocation: UseLocation by kodein.instance<UseLocation>()
    private var location: Location? = null
    private var scaleFactor: Double = 1.0
    private var rotateAngleDgree: Double = 0.0

    private val scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(context, createGestureDetector())

    init {
        createLocationObservable().subscribe()
//        UseLocation.startLocationObserve()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return
        location ?: return

        // デバッグ用位置情報テキスト出力
        canvas.drawText(location.toString(), 0f, 0f, paintMapPoint)

        // 位置情報出力
        canvas.drawCircle(100f, 100f, 50f, paintMapPoint)
    }

    private fun createLocationObservable(): Observable<Location> {
        return UseLocation
                .getLocationObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    location = it
                    Timber.d("subscribing: ${location}")
                    invalidate()
                }
                .onErrorResumeNext {
                    Timber.e(it, "error on get location")
                    createLocationObservable()
                }
    }

    private fun getPaintMapPoint(): Paint {
        val paint = Paint()
        paint.color = Color.BLUE
        paint.isAntiAlias = true
        paint.strokeWidth = 20f
        return paint
    }

    private fun createGestureDetector(): OnScaleGestureListener {
        return object : OnScaleGestureListener {
            var focusX: Double = 0.0
            var focusY: Double = 0.0


            override fun onScaleBegin(p0: ScaleGestureDetector?): Boolean {
                p0 ?: return true

                focusX = p0.focusX.toDouble()
                focusY = p0.focusY.toDouble()

                Timber.d("onScaleBegin: ($focusX, $focusY)")
                return true
            }

            override fun onScaleEnd(p0: ScaleGestureDetector?) {
                Timber.d("onScaleEnd")
                return
            }

            override fun onScale(p0: ScaleGestureDetector?): Boolean {
                p0 ?: return true

                // 倍率
                scaleFactor *= p0.scaleFactor.toDouble()

                // 回転角度
                rotateAngleDgree += Geometry.determineAngle(focusX, focusY, p0.focusX.toDouble(), p0.focusY.toDouble()).toDegree()

                Timber.d("start: ($focusX, $focusY)")
                Timber.d("end  : (${p0.focusX}, ${p0.focusY})")
                Timber.d("scale: $scaleFactor")
                Timber.d("rotateAngleDgree: ${rotateAngleDgree}")

                return true
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return scaleGestureDetector.onTouchEvent(event)
    }
}