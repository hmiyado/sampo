package com.example.hmiyado.sampo.view.custom

import android.content.Context
import com.example.hmiyado.sampo.controller.ScaleViewController
import com.example.hmiyado.sampo.presenter.ScaleViewPresenter

/**
 * Created by hmiyado on 2016/12/24.
 *
 * 縮尺を表す View
 */
class ScaleView(context: Context) : CanvasView(context) {
    val presenter by lazy { ScaleViewPresenter(this) }
    val controller by lazy { ScaleViewController(this) }
}