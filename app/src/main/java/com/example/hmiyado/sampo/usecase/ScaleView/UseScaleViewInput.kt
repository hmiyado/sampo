package com.example.hmiyado.sampo.usecase.scaleview

import android.graphics.Canvas
import com.example.hmiyado.sampo.presenter.ScaleViewPresenter
import rx.Observable

/**
 * Created by hmiyado on 2016/12/24.
 */
class UseScaleViewInput(private val scaleViewPresenter: ScaleViewPresenter) {

    fun getOnDrawCanvasSignal(): Observable<Canvas> = scaleViewPresenter.getOnDrawObservable()
}