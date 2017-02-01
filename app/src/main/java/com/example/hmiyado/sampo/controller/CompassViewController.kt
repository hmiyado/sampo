package com.example.hmiyado.sampo.controller

import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.hmiyado.sampo.domain.model.Orientation
import com.example.hmiyado.sampo.view.map.custom.CompassView

/**
 * Created by hmiyado on 2016/12/21.
 *
 * CompassView のController
 */
class CompassViewController(
        compassView: CompassView
) : ViewController<CompassView>(compassView) {

    fun drawCompass(canvas: Canvas, orientation: Orientation) {
        val bitmap = BitmapFactory.decodeResource(view.resources, android.R.drawable.arrow_up_float)
        val centerX = view.width / 2f
        val centerY = view.height / 2f
        canvas.rotate(orientation.azimuth.toFloat(), centerX, centerY)
        canvas.drawBitmap(bitmap, centerX, centerY, null)
    }
}