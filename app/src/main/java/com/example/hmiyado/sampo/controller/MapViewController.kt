package com.example.hmiyado.sampo.controller

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.view.map.custom.MapView
import org.jetbrains.anko.dip

/**
 * Created by hmiyado on 2016/12/10.
 * @link MapView に対応するController
 */
class MapViewController(view: MapView) : ViewController<MapView>(view) {
    val viewWidth = view.width
    val viewHeight = view.height

    private fun createPaint(colorInt: Int, strokeWidth: Float): Paint {
        val paintMapPoint = Paint()
        paintMapPoint.color = colorInt
        paintMapPoint.isAntiAlias = true
        paintMapPoint.strokeWidth = strokeWidth
        return paintMapPoint
    }

    fun centeringCanvas(canvas: Canvas) {
        canvas.translate((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())
    }

    fun rotateCanvas(canvas: Canvas, rotateAngle: Degree) {
        // canvas を回転する
        canvas.rotate(rotateAngle.toFloat())
    }

    fun drawFootmark(canvas: Canvas, x: Float, y: Float) {
        val paintFootmark = createPaint(Color.GREEN, view.dip(1).toFloat())
        canvas.drawCircle(view.dip(x).toFloat(), view.dip(y).toFloat(), view.dip(3).toFloat(), paintFootmark)
    }

    fun drawOriginalLocation(canvas: Canvas) {
        val paintOriginalLocation: Paint = createPaint(Color.BLUE, view.dip(1).toFloat())
        val length = view.dip(25).toFloat()
        canvas.drawLine(0f, 0f, 0f, length, paintOriginalLocation)
        canvas.drawCircle(0f, length, length / 2, paintOriginalLocation)
    }

    fun drawMesh(canvas: Canvas) {
        val paint = createPaint(Color.LTGRAY, view.dip(1).toFloat())
        val meshInterval = view.dip(50)
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