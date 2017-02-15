package com.example.hmiyado.sampo.usecase.map.scaleview

import android.graphics.Canvas
import com.example.hmiyado.sampo.presenter.map.ScaleViewPresenter
import rx.Observable

/**
 * Created by hmiyado on 2016/12/24.
 */
class UseScaleViewInput(private val scaleViewPresenter: ScaleViewPresenter) {

    fun getOnDrawCanvasSignal(): Observable<Canvas> = scaleViewPresenter.getOnDrawObservable()
}