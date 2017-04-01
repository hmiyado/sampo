package com.example.hmiyado.sampo.controller.map

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.controller.ViewController
import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.math.Measurement
import com.example.hmiyado.sampo.domain.math.cos
import com.example.hmiyado.sampo.domain.math.sin
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Territory
import com.example.hmiyado.sampo.domain.model.TerritoryScorerSizeImpl
import com.example.hmiyado.sampo.domain.model.ValidityPeriod
import com.example.hmiyado.sampo.usecase.map.UseMapView.Sink
import com.example.hmiyado.sampo.view.map.custom.MapView
import org.jetbrains.anko.dip
import org.threeten.bp.Instant
import org.threeten.bp.Period

/**
 * Created by hmiyado on 2016/12/10.
 * @link MapView に対応するController
 */
class MapViewController(view: MapView) : ViewController<MapView>(view), Sink {
    private var drawableMap: Sink.DrawableMap = Sink.DrawableMap(
            originalLocation = Location.empty(),
            scaleFactor = 1.0f,
            rotateAngle = Degree(0),
            footmarks = emptyList(),
            territories = emptyList(),
            scorer = TerritoryScorerSizeImpl,
            validityPeriod = ValidityPeriod.create(Instant.EPOCH, Period.ofWeeks(1))
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

    private fun drawTerritory(canvas: Canvas, territory: Territory, drawableMap: Sink.DrawableMap) {
        val latitude = Territory.findLatitudeById(territory.latitudeId)
        val longitude = Territory.findLongitudeById(territory.longitudeId)

        val centerLatitude = latitude + Territory.LATITUDE_UNIT / 2
        val centerLongitude = longitude + Territory.LONGITUDE_UNIT / 2

        val centerLocation = Location(centerLatitude, centerLongitude, Instant.EPOCH)

        val distance = measurement.determinePathwayDistance(drawableMap.originalLocation, centerLocation)
        val azimuth = measurement.determineAzimuth(drawableMap.originalLocation, centerLocation)
        val x = distance * cos(azimuth)
        val y = distance * sin(azimuth)

        canvas.drawCircle(
                view.dip(drawableMap.scaleFactor.scale(x)).toFloat(),
                view.dip(drawableMap.scaleFactor.scale(y)).toFloat(),
                view.dip(drawableMap.scaleFactor.scale(Territory.getRadius(measurement))).toFloat(),
                createPaint(Color.MAGENTA, view.dip(1).toFloat()).apply {
                    alpha = drawableMap.scorer.calcScorePerTerritory(territory, drawableMap.validityPeriod).toInt()
                })
        //        Timber.d("draw: ($x, $y) r = ${Territory.getRadius(measurement)}")
    }

    private fun drawPoint(canvas: Canvas, x: Float, y: Float, paintFootmark: Paint) {
        canvas.drawCircle(view.dip(x).toFloat(), view.dip(y).toFloat(), view.dip(3).toFloat(), paintFootmark)
    }

    private fun drawOriginalLocation(canvas: Canvas) {
        val originBitmap = BitmapFactory.decodeResource(view.resources, R.drawable.ic_navigation_black_18dp)
        canvas.drawBitmap(originBitmap, -originBitmap.width.toFloat() / 2, -originBitmap.height.toFloat() / 2, null)
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
            drawPoint(canvas, drawableMap.scaleFactor.scale(x), drawableMap.scaleFactor.scale(y), createPaint(Color.GREEN, view.dip(1).toFloat()))
        }

        drawableMap.territories.forEach {
            drawTerritory(canvas, it, drawableMap)
        }
    }

    private fun Float.scale(num: Double) = (num / this).toFloat()

    override fun setMeasurement(measurement: Measurement) {
        this.measurement = measurement
    }
}