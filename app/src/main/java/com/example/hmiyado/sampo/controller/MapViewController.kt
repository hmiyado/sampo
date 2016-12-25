package com.example.hmiyado.sampo.controller

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.hmiyado.sampo.view.custom.MapView
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/10.
 * @link MapView に対応するController
 */
class MapViewController(private val mapView: MapView) {


    fun bindMapViewAndSubscribe(observable: Observable<*>): Subscription {
        return observable.bindToLifecycle(mapView).subscribe()
    }

    private fun createPaint(colorInt: Int, strokeWidth: Float): Paint {
        val paintMapPoint = Paint()
        paintMapPoint.color = colorInt
        paintMapPoint.isAntiAlias = true
        paintMapPoint.strokeWidth = strokeWidth
        return paintMapPoint
    }

    fun invalidate() {
        mapView.postInvalidate()
    }

    fun drawOriginalLocation(canvas: Canvas) {
        val paintOriginalLocation: Paint = createPaint(Color.BLUE, 10f)
        val length = 50f
        canvas.drawLine(0f, 0f, 0f, length, paintOriginalLocation)
        canvas.drawCircle(0f, length, length / 2, paintOriginalLocation)
    }

    fun drawMesh(canvas: Canvas) {
        val paint = createPaint(Color.LTGRAY, 5f)
        val meshInterval = 100
        (0..canvas.height step meshInterval).forEach {
            val left = canvas.width.unaryMinus().toFloat()
            val right = canvas.width.toFloat()
            canvas.drawLine(left, it.toFloat(), right, it.toFloat(), paint)
            canvas.drawLine(left, it.unaryMinus().toFloat(), right, it.unaryMinus().toFloat(), paint)
        }
        (0..canvas.width step meshInterval).forEach {
            val top = canvas.height.toFloat()
            val bottom = canvas.height.unaryMinus().toFloat()
            canvas.drawLine(it.toFloat(), bottom, it.toFloat(), top, paint)
            canvas.drawLine(it.unaryMinus().toFloat(), bottom, it.unaryMinus().toFloat(), top, paint)
        }
    }
}