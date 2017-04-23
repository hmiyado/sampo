package com.hmiyado.sampo.controller.map

import android.graphics.*
import com.hmiyado.sampo.R
import com.hmiyado.sampo.controller.ViewController
import com.hmiyado.sampo.domain.math.Measurement
import com.hmiyado.sampo.domain.math.Vector2
import com.hmiyado.sampo.domain.model.Area
import com.hmiyado.sampo.domain.model.DrawableMap
import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.domain.model.Territory
import com.hmiyado.sampo.usecase.map.UseMapView.Sink
import com.hmiyado.sampo.usecase.map.UseMapView.Sink.DrawableTerritories
import com.hmiyado.sampo.view.map.custom.MapView
import org.jetbrains.anko.dip
import org.threeten.bp.Instant

/**
 * Created by hmiyado on 2016/12/10.
 * @link MapView に対応するController
 */
class MapViewController(view: MapView) : ViewController<MapView>(view), Sink {
    var drawableTerritories: DrawableTerritories = DrawableTerritories()
    val drawableMap
        get() = drawableTerritories.drawableMap

    private val measurement: Measurement
        get() = drawableMap.measurement

    private fun createPaint(colorInt: Int, strokeWidth: Float): Paint {
        val paintMapPoint = Paint()
        paintMapPoint.color = colorInt
        paintMapPoint.isAntiAlias = true
        paintMapPoint.strokeWidth = strokeWidth
        return paintMapPoint
    }

    private fun centeringCanvas(canvas: Canvas) {
        canvas.translate((view.width / 2).toFloat(), (view.height / 2).toFloat())
    }

    private fun drawTerritory(canvas: Canvas, territory: Territory, score: Double, drawableMap: DrawableMap) {
        val areaRadius = view.dip(drawableMap.scaleFactor.scale(Area.getRadius(measurement))).toFloat()
        val territoryPaint = createPaint(Color.MAGENTA, view.dip(1).toFloat())
        territory.area.run {
            Pair(Area.findLatitudeById(latitudeId), Area.findLongitudeById(longitudeId))
        }.let { (latitude, longitude) ->
            Pair(latitude + Area.LATITUDE_UNIT / 2, longitude + Area.LONGITUDE_UNIT / 2)
        }.let { (centerLatitude, centerLongitude) ->
            Location(centerLatitude, centerLongitude, Instant.EPOCH)
        }.let {
            drawableMap.determineVectorFromOriginOnCanvas(view, it)
        }.let { (x, y) ->
            canvas.drawCircle(
                    x.toFloat(),
                    y.toFloat(),
                    areaRadius,
                    territoryPaint.apply {
                        val maxAlpha = 256
                        alpha = (maxAlpha * score / drawableTerritories.maxTerritoryScore).toInt()

                    })
        }

        val paint = createPaint(Color.GREEN, view.dip(1).toFloat())
        territory.locations
                .map {
                    drawableMap.determineVectorFromOriginOnCanvas(view, it)
                }
                .forEach { (x, y) ->
                    drawPoint(canvas, x.toFloat(), y.toFloat(), paint)
                }
    }

    private fun drawPoint(canvas: Canvas, x: Float, y: Float, paintFootmark: Paint) {
        canvas.drawCircle(x, y, view.dip(3).toFloat(), paintFootmark)
    }

    private fun drawOriginalLocation(canvas: Canvas) {
        val matrix = Matrix().apply {
            postRotate(drawableMap.rotateAngle.toFloat())
        }

        val originBitmap = BitmapFactory.decodeResource(view.resources, R.drawable.ic_navigation_black_18dp)
        val rotatedBitmap = Bitmap.createBitmap(originBitmap, 0, 0, originBitmap.width, originBitmap.height, matrix, false)
        canvas.drawBitmap(rotatedBitmap, -rotatedBitmap.width.toFloat() / 2, -rotatedBitmap.height.toFloat() / 2, null)
    }

    private fun drawMesh(canvas: Canvas) {
        val paint = createPaint(Color.LTGRAY, view.dip(1).toFloat())
        val drawOneMesh = { start: Vector2, stop: Vector2 ->
            val startRotated = start.rotate(drawableMap.rotateAngle)
            val stopRotated = stop.rotate(drawableMap.rotateAngle)

            canvas.drawLine(
                    startRotated.x.toFloat(),
                    startRotated.y.toFloat(),
                    stopRotated.x.toFloat(),
                    stopRotated.y.toFloat(),
                    paint
            )
        }

        val meshInterval = view.dip(50)
        val maxXRange = view.width - view.width % meshInterval + meshInterval
        val maxYRange = view.height - view.height % meshInterval + meshInterval
        (-maxYRange..maxYRange step meshInterval).forEach {
            val start = Vector2(-maxXRange, it)
            val stop = Vector2(maxXRange, it)
            drawOneMesh(start, stop)
        }
        (-maxXRange..maxXRange step meshInterval).forEach {
            val start = Vector2(it, -maxYRange)
            val stop = Vector2(it, maxYRange)

            drawOneMesh(start, stop)
        }
    }

    override fun draw(drawableTerritories: DrawableTerritories) {
        this.drawableTerritories = drawableTerritories
        invalidate()
    }

    fun draw(canvas: Canvas) {
        centeringCanvas(canvas)

        drawMesh(canvas)
        drawOriginalLocation(canvas)

        drawableTerritories.territories.forEach { (territory, score) ->
            drawTerritory(canvas, territory, score, drawableMap)
        }
    }

    private fun Float.scale(num: Double) = (num / this).toFloat()
}