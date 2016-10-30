package com.example.hmiyado.sampo.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

/**
 * Created by hmiyado on 2016/09/23.
 */

// settings for anko custom view
public inline fun ViewManager.mapView(theme: Int = 0) = mapView(theme) {}
public inline fun ViewManager.mapView(theme: Int = 0, init: MapView.() -> Unit) = ankoView({ MapView(it) }, theme, init)

class MapView(context: Context): View(context) {
    val paint: Paint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return

        canvas.drawText("Hello Text", 50f, 50f, paint)
    }
}