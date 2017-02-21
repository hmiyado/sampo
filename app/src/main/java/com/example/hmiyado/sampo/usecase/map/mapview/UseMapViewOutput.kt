package com.example.hmiyado.sampo.usecase.map.mapview

import android.graphics.Canvas
import com.example.hmiyado.sampo.controller.MapViewController
import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.math.SphericalTrigonometry
import com.example.hmiyado.sampo.domain.math.cos
import com.example.hmiyado.sampo.domain.math.sin
import com.example.hmiyado.sampo.domain.model.Location
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/10.
 *
 * 地図閲覧への出力を責務とする．
 *
 * 地図を閲覧するのに必要な情報
 * - 地図の見た目
 *  - 縮尺
 *  - 回転角
 * - 位置情報
 *  - 現在地
 *  - その他の地点情報
 */
class UseMapViewOutput(
        private val mapViewController: MapViewController
) {
    private data class Map(
            val canvas: Canvas,
            val originalLocation: Location,
            val scale: Float,
            val rotateAngle: Degree,
            val footmarks: List<Location>
    )

    fun setOnDrawSignal(
            originalLocationSignal: Observable<Location>,
            scaleSignal: Observable<Float>,
            rotateAngleSignal: Observable<Degree>,
            footmarksSignal: Observable<List<Location>>,
            onDrawSignal: Observable<Canvas>
    ): Subscription {
        return onDrawSignal
                .withLatestFrom(
                        originalLocationSignal,
                        scaleSignal,
                        rotateAngleSignal,
                        footmarksSignal,
                        ::Map
                )
                .bindMapView()
                .subscribe({ map ->
                    mapViewController.centeringCanvas(map.canvas)
                    mapViewController.rotateCanvas(map.canvas, map.rotateAngle)

                    mapViewController.drawMesh(map.canvas)
                    mapViewController.drawOriginalLocation(map.canvas)

                    val measurement = SphericalTrigonometry

                    map.footmarks.forEach {
                        val distance = measurement.determinePathwayDistance(map.originalLocation, it)
                        val azimuth = measurement.determineAzimuth(map.originalLocation, it)
                        val x = distance * cos(azimuth)
                        val y = distance * sin(azimuth)
                        mapViewController.drawFootmark(map.canvas, (x / map.scale).toFloat(), (y / map.scale).toFloat())
                    }

                })
    }

    fun setOnUpdateMapSignal(
            originalLocationSignal: Observable<Location>,
            scaleSignal: Observable<Float>,
            rotateAngleSignal: Observable<Degree>
    ): Subscription {
        return Observable.merge(
                originalLocationSignal,
                scaleSignal,
                rotateAngleSignal
        )
                .bindMapView()
                .subscribe({ mapViewController.invalidate() })
    }

    private fun <T> Observable<T>.bindMapView(): Observable<T> {
        return mapViewController.bindToViewLifecycle(this)
    }
}

