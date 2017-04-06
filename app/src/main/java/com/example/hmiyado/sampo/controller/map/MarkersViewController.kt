package com.example.hmiyado.sampo.controller.map

import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.hmiyado.sampo.controller.ViewController
import com.example.hmiyado.sampo.usecase.map.UseMarkerView
import org.jetbrains.anko.dip

/**
 * Created by hmiyado on 2017/04/07.
 */
class MarkersViewController(view: RelativeLayout) : ViewController<RelativeLayout>(view), UseMarkerView.Sink {
    lateinit var drawableMarkers: UseMarkerView.Sink.DrawableMarkers

    override fun draw(drawableMarkers: UseMarkerView.Sink.DrawableMarkers) {
        this.drawableMarkers = drawableMarkers
        synchronized(drawableMarkers) {
            ImageView(view.context).apply {
                x = view.dip(100).toFloat()
                y = view.dip(200).toFloat()
            }
        }
    }

}