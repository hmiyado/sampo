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
        // canvasの中心を画面の中心に移動する
        canvas.rotate(orientation.azimuth.toFloat())
        canvas.drawBitmap(bitmap, 0f, 0f, null)
    }

    fun <T> bindToCompassView(observable: Observable<T>) = observable.bindToLifecycle(compassView)
}