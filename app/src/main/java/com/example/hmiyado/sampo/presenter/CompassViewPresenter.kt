package com.example.hmiyado.sampo.presenter

import com.example.hmiyado.sampo.view.custom.CompassView

/**
 * Created by hmiyado on 2016/12/21.
 *
 * @see CompassView の Presenter
 */
class CompassViewPresenter(
        private val compassView: CompassView
) {

    fun getOnDrawSignal() = compassView.getOnDrawSignal()

}