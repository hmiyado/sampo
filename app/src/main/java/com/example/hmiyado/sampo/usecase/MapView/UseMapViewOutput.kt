package com.example.hmiyado.sampo.usecase.mapview

import android.graphics.Canvas
import com.example.hmiyado.sampo.controller.MapViewController
import com.example.hmiyado.sampo.domain.math.SphericalTrigonometry
import com.example.hmiyado.sampo.domain.math.cos
import com.example.hmiyado.sampo.domain.math.sin
import com.example.hmiyado.sampo.domain.model.Map
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
    fun setOnDrawSignal(onUpdateMapSignal: Observable<Map>, onDrawSignal: Observable<Canvas>): Subscription {
        return onDrawSignal
                .withLatestFrom(onUpdateMapSignal, { canvas, map -> Pair(canvas, map) })
                .doOnNext { pairOfCanvasMap ->
                    val map = pairOfCanvasMap.second
                    val canvas = pairOfCanvasMap.first

                    mapViewController.centeringCanvas(canvas)
                    mapViewController.rotateCanvas(canvas, map.rotateAngle)

                    mapViewController.drawMesh(canvas)
                    mapViewController.drawOriginalLocation(canvas)

                    val measurement = SphericalTrigonometry

                    map.footmarks.forEach {
                        val distance = measurement.determinePathwayDistance(map.originalLocation, it)
                        val azimuth = measurement.determineAzimuth(map.originalLocation, it)
                        val x = distance * cos(azimuth)
                        val y = distance * sin(azimuth)
                        mapViewController.drawFootmark(canvas, (x / map.scale).toFloat(), (y / map.scale).toFloat())
                    }
                }
                .bindMapViewAndSubscribe()
    }

    fun setOnUpdateMapSignal(onUpdateMapSignal: Observable<Map>): Subscription {
        return onUpdateMapSignal
                .doOnNext { mapViewController.invalidate() }
                .bindMapViewAndSubscribe()
    }

    private fun <T> Observable<T>.bindMapViewAndSubscribe(): Subscription {
        return mapViewController.bindToViewLifecycle(this).subscribe()
    }
}

