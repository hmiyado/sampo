package com.example.hmiyado.sampo.usecase.mapview

import android.graphics.Canvas
import com.example.hmiyado.sampo.controller.MapViewController
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
    fun setOnDrawSignal(onUpdateMapSignal: Observable<Map>, onDrawSignal: Observable<Canvas>) {
        onDrawSignal
                .withLatestFrom(onUpdateMapSignal, { canvas, map -> Pair(canvas, map) })
                .doOnNext { pairOfCanvasMap ->
                    val map = pairOfCanvasMap.second
                    val canvas = pairOfCanvasMap.first

                    // canvasの中心を画面の中心に移動する
                    canvas.translate((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())

                    // canvas を回転する
                    canvas.rotate(map.rotateAngle.toFloat())

                    mapViewController.drawMesh(canvas)
                    mapViewController.drawOriginalLocation(canvas)

//                    map.footmarks.forEach {
//                        val distance = HubenyFormula.determinePathwayDistance(it, map.originalLocation)
//                        val
//                    }
                }
                .bindMapViewAndSubscribe()
    }

    fun setOnUpdateMapSignal(onUpdateMapSignal: Observable<Map>) {
        onUpdateMapSignal
                .doOnNext { mapViewController.invalidate() }
                .bindMapViewAndSubscribe()
    }

    private fun <T> Observable<T>.bindMapViewAndSubscribe(): Subscription {
        return mapViewController.bindMapViewAndSubscribe(this)
    }
}

