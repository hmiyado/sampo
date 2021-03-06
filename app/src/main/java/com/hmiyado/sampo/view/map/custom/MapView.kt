@file:Suppress("NOTHING_TO_INLINE")

package com.hmiyado.sampo.view.map.custom

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import com.hmiyado.sampo.controller.map.MapViewController
import com.hmiyado.sampo.presenter.map.MapViewPresenter
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable


/**
 * Created by hmiyado on 2016/09/23.
 * 位置情報を描画する
 * ピンチによる拡大縮小・回転　に対応する
 */
class MapView(context: Context) : CanvasView(context) {
    val presenter: MapViewPresenter by kotlin.lazy {
        MapViewPresenter(this)
    }
    val controller: MapViewController by kotlin.lazy {
        MapViewController(this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        controller.draw(canvas)
    }

    fun getOnTouchSignal(): Observable<MotionEvent> {
        return RxView.touches(this)
    }
}