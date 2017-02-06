@file:Suppress("NOTHING_TO_INLINE")

package com.example.hmiyado.sampo.view.map.custom

import android.content.Context
import android.view.MotionEvent
import com.example.hmiyado.sampo.controller.MapViewController
import com.example.hmiyado.sampo.presenter.map.MapViewPresenter
import com.jakewharton.rxbinding.view.RxView
import rx.Observable


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

    fun getOnTouchSignal(): Observable<MotionEvent> {
        return RxView.touches(this)
    }
}