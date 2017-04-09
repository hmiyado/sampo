package com.hmiyado.sampo.presenter.map

import com.hmiyado.sampo.presenter.ViewPresenter
import com.hmiyado.sampo.usecase.map.UseCompassView
import com.hmiyado.sampo.view.map.custom.CompassView

/**
 * Created by hmiyado on 2016/12/21.
 *
 * @see CompassView の ViewPresenter
 */
class CompassViewPresenter(
        compassView: CompassView
) : ViewPresenter<CompassView>(compassView), UseCompassView.Source