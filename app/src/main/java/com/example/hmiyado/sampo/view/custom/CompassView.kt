package com.example.hmiyado.sampo.view.custom

import android.content.Context
import android.graphics.Canvas
import android.view.View
import com.example.hmiyado.sampo.controller.CompassViewController
import com.example.hmiyado.sampo.presenter.CompassViewPresenter
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import rx.Observable
import rx.lang.kotlin.PublishSubject

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 方位磁針のView
 */
class CompassView(context: Context) : View(context), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()
    val presenter by lazy {
        CompassViewPresenter(this)
    }
    val controller by lazy {
        CompassViewController(this)
    }
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