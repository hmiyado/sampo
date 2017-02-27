package com.example.hmiyado.sampo.controller

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.hmiyado.sampo.usecase.map.scaleview.UseScaleViewSink
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import com.example.hmiyado.sampo.view.map.custom.ScaleView
import org.jetbrains.anko.dip
import org.jetbrains.anko.px2dip
import org.jetbrains.anko.sp

/**
 * Created by hmiyado on 2016/12/24.
 *
 * @see ScaleView のコントローラー
 */
class ScaleViewController(scaleView: ScaleView) : ViewController<ScaleView>(scaleView), UseScaleViewSink {
    private var scale = 0.0f

    private fun createPaint(colorInt: Int, strokeWidth: Float): Paint {
        val paintMapPoint = Paint()
        paintMapPoint.color = colorInt
        paintMapPoint.isAntiAlias = true
        paintMapPoint.strokeWidth = strokeWidth
        return paintMapPoint
    }

    fun drawScale(canvas: Canvas) {
        val paint = createPaint(Color.BLUE, 5f)

        canvas.drawLine(view.dip(10).toFloat(), view.height / 2f, view.dip(10 + view.px2dip(MapStore.SCALE_UNIT)).toFloat(), view.height / 2f, paint) // 縮尺定規

        val text = createPaint(Color.BLUE, 20f)
        text.textSize = view.sp(14).toFloat()
        canvas.drawText("$scale [m]", view.dip(10).toFloat(), view.height / 2f + view.dip(15), text) // 縮尺倍率
    }

    override fun setScale(scale: Float) {
        this.scale = scale
    }

    override fun draw() {
        invalidate()
    }
}