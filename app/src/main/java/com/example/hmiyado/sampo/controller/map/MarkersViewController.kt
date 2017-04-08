package com.example.hmiyado.sampo.controller.map

import android.view.View
import android.widget.RelativeLayout
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.controller.ViewController
import com.example.hmiyado.sampo.domain.math.Vector2
import com.example.hmiyado.sampo.usecase.map.UseMarkerView
import com.example.hmiyado.sampo.view.map.custom.MarkerView
import org.jetbrains.anko.childrenSequence
import org.jetbrains.anko.imageResource
import timber.log.Timber

/**
 * Created by hmiyado on 2017/04/07.
 */
class MarkersViewController(view: RelativeLayout) : ViewController<RelativeLayout>(view), UseMarkerView.Sink {
    val canvasCenter
        get() = Vector2(view.width / 2.0, view.height / 2.0)

    override fun draw(drawableMarkers: UseMarkerView.Sink.DrawableMarkers) {
        Timber.d("markers size = ${drawableMarkers.markers.size}")
        synchronized(view) {
            drawableMarkers.markers.minus(view.childrenMarkerViews().map { it.marker }).forEach {
                view.addView(
                        MarkerView(view.context, it).apply {
                            imageResource = R.drawable.ic_place_black_36dp
                        }
                )
            }

            view.childrenMarkerViews().forEach {
                if (drawableMarkers.markers.contains(it.marker)) {
                    // 現在の地図の原点からマーカー位置へのベクトルを計算する
                    val vector = drawableMarkers.drawableMap.determineVectorFromOriginOnCanvas(view, it.marker.location)
                    Timber.d(it.marker.toString())
                    Timber.d(vector.toString())
                    // キャンバスの中心座標（原点）からのベクトルを計算する
                    (vector + canvasCenter).apply {
                        Timber.d(this.toString())
                        it.x = x.toFloat()
                        it.y = y.toFloat()
                        Timber.d("(${it.x}, ${it.y})")
                    }
                } else {
                    view.removeView(it)
                }
            }
        }
    }

    private fun View.childrenMarkerViews(): Sequence<MarkerView> = view.childrenSequence().filter { it is MarkerView }.map { it as MarkerView }
}