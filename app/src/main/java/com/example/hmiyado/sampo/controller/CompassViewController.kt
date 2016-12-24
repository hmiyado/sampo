package com.example.hmiyado.sampo.controller

import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.hmiyado.sampo.domain.model.Orientation
import com.example.hmiyado.sampo.view.custom.CompassView
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable

/**
 * Created by hmiyado on 2016/12/21.
 *
 * CompassView のController
 */
class CompassViewController(
        private val compassView: CompassView
) {

    fun invalidate() = compassView.postInvalidate()

    fun drawCompass(canvas: Canvas, orientation: Orientation) {
        val bitmap = BitmapFactory.decodeResource(compassView.resources, android.R.drawable.arrow_up_float)
        val centerX = canvas.width / 2f
        val centerY = canvas.height / 2f
        canvas.rotate(orientation.azimuth.toFloat(), centerX, centerY)
        canvas.drawBitmap(bitmap, centerX, centerY, null)
    }

    fun <T> bindToCompassView(observable: Observable<T>) = observable.bindToLifecycle(compassView)
}