package com.hmiyado.sampo.controller.result

import android.widget.TextView
import com.hmiyado.sampo.controller.ViewController
import com.hmiyado.sampo.usecase.result.UseTotalDistanceViewer

/**
 * Created by hmiyado on 2017/03/02.
 */
class TotalDistanceViewController(view: TextView) : ViewController<TextView>(view), UseTotalDistanceViewer.Sink {
    override fun show(distance: Double) {
        view.text = distance.toString()
    }

}