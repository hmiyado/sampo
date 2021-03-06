package com.hmiyado.sampo.view.map.custom

import android.content.Context
import android.graphics.Canvas
import com.hmiyado.sampo.controller.map.ScaleViewController
import com.hmiyado.sampo.presenter.map.ScaleViewPresenter

/**
 * Created by hmiyado on 2016/12/24.
 *
 * 縮尺を表す View
 */
class ScaleView(context: Context) : CanvasView(context) {
    val presenter by lazy { ScaleViewPresenter(this) }
    val controller by lazy { ScaleViewController(this) }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        controller.drawScale(canvas)
    }
}