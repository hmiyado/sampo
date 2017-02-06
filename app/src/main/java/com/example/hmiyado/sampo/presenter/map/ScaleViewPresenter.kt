package com.example.hmiyado.sampo.presenter.map

import android.graphics.Canvas
import com.example.hmiyado.sampo.presenter.ViewPresenter
import com.example.hmiyado.sampo.view.map.custom.ScaleView
import rx.Observable

/**
 * Created by hmiyado on 2016/12/24.
 */
class ScaleViewPresenter(scaleView: ScaleView) : ViewPresenter<ScaleView>(scaleView) {
    fun getOnDrawObservable(): Observable<Canvas> = view.getOnDrawSignal()
}