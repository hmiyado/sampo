@file:Suppress("NOTHING_TO_INLINE")

package com.example.hmiyado.sampo.view.custom

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import com.example.hmiyado.sampo.controller.MapViewController
import com.example.hmiyado.sampo.presenter.MapViewPresenter
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding.view.RxView
import rx.Observable
import rx.lang.kotlin.PublishSubject
import timber.log.Timber


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
    val controller: MapViewController by kotlin.lazy {
        MapViewController(this)
    }

    private val onDrawSignalSubject = PublishSubject<Canvas>()

    init {
        injector.inject(appKodein())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        Timber.e("onDraw")
        onDrawSignalSubject.onNext(canvas)
    }

    fun getOnDrawSignal(): Observable<Canvas> {
        return onDrawSignalSubject.share()
    }

    fun getOnTouchSignal(): Observable<MotionEvent> {
        return RxView.touches(this)
    }
}