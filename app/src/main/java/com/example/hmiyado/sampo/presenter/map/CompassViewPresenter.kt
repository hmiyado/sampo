package com.example.hmiyado.sampo.presenter.map

import com.example.hmiyado.sampo.presenter.ViewPresenter
import com.example.hmiyado.sampo.usecase.map.compassview.UseCompassViewInput
import com.example.hmiyado.sampo.view.map.custom.CompassView

/**
 * Created by hmiyado on 2016/12/21.
 *
 * @see CompassView の ViewPresenter
 */
class CompassViewPresenter(
        compassView: CompassView
) : ViewPresenter<CompassView>(compassView), UseCompassViewInput {

    override fun getOnDrawSignal() = view.getOnDrawSignal()
}