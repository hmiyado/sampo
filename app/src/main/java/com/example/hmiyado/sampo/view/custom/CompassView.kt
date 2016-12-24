package com.example.hmiyado.sampo.view.custom

import android.content.Context
import com.example.hmiyado.sampo.controller.CompassViewController
import com.example.hmiyado.sampo.presenter.CompassViewPresenter

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