package com.example.hmiyado.sampo.presenter

import com.example.hmiyado.sampo.view.map.custom.CompassView

/**
 * Created by hmiyado on 2016/12/21.
 *
 * @see CompassView の ViewPresenter
 */
class CompassViewPresenter(
        compassView: CompassView
) : ViewPresenter<CompassView>(compassView) {

    fun getOnDrawSignal() = view.getOnDrawSignal()
}