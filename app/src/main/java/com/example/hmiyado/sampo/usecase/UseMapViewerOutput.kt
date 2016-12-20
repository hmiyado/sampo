package com.example.hmiyado.sampo.usecase

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.hmiyado.sampo.controller.MapViewController
import com.example.hmiyado.sampo.domain.model.Map
import rx.Observable
import rx.Subscription
import timber.log.Timber

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
class UseMapViewerOutput(
        private val mapViewController: MapViewController
) {
    // TODO 「どんなふうに描画するか？」はmapViewControllerに移譲するようにする
    private val paintMapPoint: Paint = Paint()

    init {
        settingPaintMapPoint()
    }

    private fun settingPaintMapPoint() {
        paintMapPoint.color = Color.BLUE
        paintMapPoint.isAntiAlias = true
        paintMapPoint.strokeWidth = 20f
    }

    fun setOnDrawSignal(onUpdateMapSignal: Observable<Map>, onDrawSignal: Observable<Canvas>) {
        onDrawSignal
                .withLatestFrom(onUpdateMapSignal, { canvas, map -> Pair(canvas, map) })
                .doOnNext { pairOfCanvasMap ->
                    val map = pairOfCanvasMap.second
                    val canvas = pairOfCanvasMap.first

                    Timber.d("on canvas drawing")
                    Timber.d("$map")
                    // デバッグ用位置情報出力
                    canvas.drawText(map.originalLocation.toString(), 0f, canvas.height - 100f, paintMapPoint)
                    // デバッグ用縮尺出力
                    canvas.drawLine(50f, canvas.height - 50f, 150f, canvas.height - 50f, paintMapPoint) // 縮尺定規
                    canvas.drawText("${map.scale} [m]", 250f, canvas.height - 50f, paintMapPoint) // 縮尺倍率

                    // canvasの中心を画面の中心に移動する
                    canvas.translate((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())

                    // canvas を回転する
                    Timber.d("map rotate degree: ${map.rotateAngle}")
                    canvas.rotate(map.rotateAngle)

                    canvas.drawLine(-600f, 0f, 600f, 0f, paintMapPoint)
                    canvas.drawLine(0f, -1000f, 0f, 1000f, paintMapPoint)

                    canvas.drawRect(-100f, 200f, 300f, 400f, paintMapPoint)

                    // 位置情報出力
                    canvas.drawCircle(-500f, 0f, 75f, paintMapPoint)
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

