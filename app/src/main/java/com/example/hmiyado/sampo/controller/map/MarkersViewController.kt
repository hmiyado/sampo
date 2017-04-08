package com.example.hmiyado.sampo.controller.map

import android.view.View
import android.widget.RelativeLayout
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.controller.ViewController
import com.example.hmiyado.sampo.domain.math.Vector2
import com.example.hmiyado.sampo.domain.model.DrawableMap
import com.example.hmiyado.sampo.usecase.map.UseMarkerView
import com.example.hmiyado.sampo.usecase.map.UseMarkerView.Sink.DrawableMarkers
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

    override fun draw(drawableMarkers: DrawableMarkers) {
        Timber.d("markers size = ${drawableMarkers.markers.size}")
        synchronized(view) {
            drawableMarkers.markers.minus(view.childrenMarkerViews().map { it.marker }).forEach {
                MarkerView(view.context, it).apply {
                    imageResource = R.drawable.ic_place_black_36dp
                    setMarkerViewCoordinate(drawableMarkers.drawableMap, this)
                }.apply {
                    view.addView(this)
                }
            }

            view.childrenMarkerViews().forEach {
                if (drawableMarkers.markers.contains(it.marker)) {
                    setMarkerViewCoordinate(drawableMarkers.drawableMap, it)
                } else {
                    view.removeView(it)
                }
            }
        }
    }

    private fun setMarkerViewCoordinate(drawableMap: DrawableMap, markerView: MarkerView) {
        // マーカー画像の下辺の中点が位置に重なるようにするための補正値
        val imageRevisionVector = Vector2(-markerView.width / 2, -markerView.height)
        if (imageRevisionVector == Vector2.ZERO) {
            // まだ view が描画されていないせいで，width, height が 0 であることがある
            markerView.visibility = View.INVISIBLE
            return
        }
        markerView.visibility = View.VISIBLE

        // 現在の地図の原点からマーカー位置へのベクトル
        val vector = drawableMap.determineVectorFromOriginOnCanvas(view, markerView.marker.location)
        // キャンバスの中心座標（原点）からのベクトルを計算する
        val (x, y) = (vector + canvasCenter + imageRevisionVector)
        markerView.x = x.toFloat()
        markerView.y = y.toFloat()
    }

    private fun View.childrenMarkerViews(): Sequence<MarkerView> = view.childrenSequence().filter { it is MarkerView }.map { it as MarkerView }
}