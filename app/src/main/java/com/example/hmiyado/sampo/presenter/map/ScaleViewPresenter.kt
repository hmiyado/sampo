package com.example.hmiyado.sampo.presenter.map

import com.example.hmiyado.sampo.presenter.ViewPresenter
import com.example.hmiyado.sampo.usecase.map.scaleview.UseScaleViewSource
import com.example.hmiyado.sampo.view.map.custom.ScaleView

/**
 * Created by hmiyado on 2016/12/24.
 */
class ScaleViewPresenter(scaleView: ScaleView) : ViewPresenter<ScaleView>(scaleView), UseScaleViewSource {
    //    override fun getOnDrawCanvasSignal(): Observable<Canvas> = view.getOnDrawSignal()
}