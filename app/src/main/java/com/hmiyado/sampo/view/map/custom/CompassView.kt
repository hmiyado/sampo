package com.hmiyado.sampo.view.map.custom

import android.content.Context
import com.hmiyado.sampo.controller.map.CompassViewController
import com.hmiyado.sampo.presenter.map.CompassViewPresenter

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 方位磁針のView
 */
class CompassView(context: Context) : CanvasView(context) {
    val presenter by lazy {
        CompassViewPresenter(this)
    }
    val controller by lazy {
        CompassViewController(this)
    }
}