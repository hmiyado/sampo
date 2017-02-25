package com.example.hmiyado.sampo.controller

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Orientation
import com.example.hmiyado.sampo.domain.store.MapStore
import com.example.hmiyado.sampo.usecase.map.scaleview.UseScaleViewOutput
import com.example.hmiyado.sampo.view.map.custom.ScaleView
import org.jetbrains.anko.dip
import org.jetbrains.anko.px2dip
import org.jetbrains.anko.sp
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/24.
 *
 * @see ScaleView のコントローラー
 */
class ScaleViewController(scaleView: ScaleView) : ViewController<ScaleView>(scaleView), UseScaleViewOutput {
    private fun createPaint(colorInt: Int, strokeWidth: Float): Paint {
        val paintMapPoint = Paint()
        paintMapPoint.color = colorInt
        paintMapPoint.isAntiAlias = true
        paintMapPoint.strokeWidth = strokeWidth
        return paintMapPoint
    }

    fun drawScale(canvas: Canvas, scale: Float) {
        val paint = createPaint(Color.BLUE, 5f)

        canvas.drawLine(view.dip(10).toFloat(), view.height / 2f, view.dip(10 + view.px2dip(MapStore.SCALE_UNIT)).toFloat(), view.height / 2f, paint) // 縮尺定規

        val text = createPaint(Color.BLUE, 20f)
        text.textSize = view.sp(14).toFloat()
        canvas.drawText("$scale [m]", view.dip(10).toFloat(), view.height / 2f + view.dip(15), text) // 縮尺倍率
    }

    override fun setMapSignal(
            locationSignal: Observable<Location>,
            orientationSignal: Observable<Orientation>,
            rotateAngleSignal: Observable<Degree>,
            scaleSignal: Observable<Float>
    ): Subscription =
            Observable
                    .merge(
                            locationSignal,
                            scaleSignal,
                            orientationSignal,
                            rotateAngleSignal
                    )
                    .bindToLifecycle()
                    .subscribe({ invalidate() })


    override fun setOnDrawSignal(scaleSignal: Observable<Float>, drawSignal: Observable<Canvas>): Subscription =
            drawSignal
                    .withLatestFrom(scaleSignal, { canvas, scale -> Pair(canvas, scale) })
                    .doOnNext {
                        val canvas = it.first
                        val scale = it.second
                        //                        Timber.d("$map")
                        drawScale(canvas, scale)
                    }
                    .bindToLifecycle()
                    .subscribe()

    fun <T> rx.Observable<T>.bindToLifecycle(): Observable<T> {
        return bindToViewLifecycle(this)
    }
}