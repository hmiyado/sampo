@file:Suppress("NOTHING_TO_INLINE")

package com.example.hmiyado.sampo.view.custom

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import android.view.ViewManager
import com.example.hmiyado.sampo.presenter.MapViewPresenter
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding.view.RxView
import org.jetbrains.anko.custom.ankoView
import rx.Observable


// settings for anko custom view
inline fun ViewManager.mapView(theme: Int = 0) = mapView(theme) {}

inline fun ViewManager.mapView(theme: Int = 0, init: MapView.() -> Unit) = ankoView(::MapView, theme, init)

/**
 * Created by hmiyado on 2016/09/23.
 * 位置情報を描画する
 * ピンチによる拡大縮小・回転　に対応する
 */
class MapView(context: Context) : View(context), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()
    val presenter: MapViewPresenter by kotlin.lazy {
        MapViewPresenter(this)
    }

    init {
        injector.inject(appKodein())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return

        presenter.onDraw(canvas)
    }

    fun getOnTouchSignal(): Observable<MotionEvent> {
        return RxView.touches(this)
    }
}