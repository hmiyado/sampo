package com.example.hmiyado.sampo.view.custom

import android.content.Context
import android.graphics.Canvas
import android.view.View
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import rx.Observable
import rx.lang.kotlin.PublishSubject

/**
 * Created by hmiyado on 2016/12/24.
 *
 * @see Canvas に描画するビュー
 */
open class CanvasView(
        context: Context
) : View(context), KodeinInjected {
    final override val injector: KodeinInjector = KodeinInjector()
    private val onDrawSignalSubject = PublishSubject<Canvas>()

    init {
        injector.inject(appKodein())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        onDrawSignalSubject.onNext(canvas)
    }

    fun getOnDrawSignal(): Observable<Canvas> {
        return onDrawSignalSubject.asObservable().share()
    }
}