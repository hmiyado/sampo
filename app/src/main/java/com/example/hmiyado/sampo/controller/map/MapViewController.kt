package com.example.hmiyado.sampo.controller.map

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.hmiyado.sampo.controller.ViewController
import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.math.Measurement
import com.example.hmiyado.sampo.domain.math.cos
import com.example.hmiyado.sampo.domain.math.sin
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.usecase.map.UseMapView.Sink
import com.example.hmiyado.sampo.view.map.custom.MapView
import org.jetbrains.anko.dip

/**
 * Created by hmiyado on 2016/12/10.
 * @link MapView に対応するController
 */
class MapViewController(view: MapView) : ViewController<MapView>(view), Sink {
    private var drawableMap: Sink.DrawableMap = Sink.DrawableMap(
            originalLocation = Location.empty(),
            scaleFactor = 1.0f,
            rotateAngle = Degree(0),
            footmarks = emptyList()
    )

    private lateinit var measurement: Measurement

    private fun createPaint(colorInt: Int, strokeWidth: Float): Paint {
        val paintMapPoint = Paint()
        paintMapPoint.color = colorInt
        paintMapPoint.isAntiAlias = true
        paintMapPoint.strokeWidth = strokeWidth
        return paintMapPoint
    }

    private fun centeringCanvas(canvas: Canvas) {
        canvas.translate((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())
    }

    private fun rotateCanvas(canvas: Canvas, rotateAngle: Degree) {
        // canvas を回転する
        canvas.rotate(rotateAngle.toFloat())
    }

    private fun drawFootmark(canvas: Canvas, x: Float, y: Float) {
        val paintFootmark = createPaint(Color.GREEN, view.dip(1).toFloat())
        canvas.drawCircle(view.dip(x).toFloat(), view.dip(y).toFloat(), view.dip(3).toFloat(), paintFootmark)
    }

    private fun drawOriginalLocation(canvas: Canvas) {
        val paintOriginalLocation: Paint = createPaint(Color.BLUE, view.dip(1).toFloat())
        val length = view.dip(25).toFloat()
        canvas.drawLine(0f, 0f, 0f, length, paintOriginalLocation)
        canvas.drawCircle(0f, length, length / 2, paintOriginalLocation)
    }

    private fun drawMesh(canvas: Canvas) {
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

    override fun draw(drawableMap: Sink.DrawableMap) {
        this.drawableMap = drawableMap
        invalidate()
    }

    fun draw(canvas: Canvas) {
        centeringCanvas(canvas)
        rotateCanvas(canvas, drawableMap.rotateAngle)

        drawMesh(canvas)
        drawOriginalLocation(canvas)

        drawableMap.footmarks.forEach {
            val distance = measurement.determinePathwayDistance(drawableMap.originalLocation, it)
            val azimuth = measurement.determineAzimuth(drawableMap.originalLocation, it)
            val x = distance * cos(azimuth)
            val y = distance * sin(azimuth)
            drawFootmark(canvas, (x / drawableMap.scaleFactor).toFloat(), (y / drawableMap.scaleFactor).toFloat())
        }
    }

    override fun setMeasurement(measurement: Measurement) {
        this.measurement = measurement
    }
}