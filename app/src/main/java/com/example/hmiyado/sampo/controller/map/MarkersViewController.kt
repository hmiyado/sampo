package com.example.hmiyado.sampo.controller.map

import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.controller.ViewController
import com.example.hmiyado.sampo.domain.model.Marker
import com.example.hmiyado.sampo.usecase.map.UseMarkerView
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageResource

/**
 * Created by hmiyado on 2017/04/07.
 */
class MarkersViewController(view: RelativeLayout) : ViewController<RelativeLayout>(view), UseMarkerView.Sink {
    lateinit var drawableMarkers: UseMarkerView.Sink.DrawableMarkers
    var markerViews: List<Pair<Marker, ImageView>> = emptyList()

    override fun draw(drawableMarkers: UseMarkerView.Sink.DrawableMarkers) {
        this.drawableMarkers = drawableMarkers
        synchronized(drawableMarkers) {
            ImageView(view.context).apply {
                imageResource = R.drawable.ic_my_location_black_24dp
                x = view.dip(100).toFloat()
                y = view.dip(200).toFloat()
            }
        }
    }

}