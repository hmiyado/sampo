package com.example.hmiyado.sampo.presenter

import android.graphics.Canvas
import com.example.hmiyado.sampo.view.custom.ScaleView
import rx.Observable

/**
 * Created by hmiyado on 2016/12/24.
 */
class ScaleViewPresenter(scaleView: ScaleView) : ViewPresenter<ScaleView>(scaleView) {
    fun getOnDrawObservable(): Observable<Canvas> = view.getOnDrawSignal()
}