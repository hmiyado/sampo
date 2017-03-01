package com.example.hmiyado.sampo.controller

import android.widget.TextView
import com.example.hmiyado.sampo.usecase.result.UseTotalDistanceViewer

/**
 * Created by hmiyado on 2017/03/02.
 */
class TotalDistanceViewController(view: TextView) : ViewController<TextView>(view), UseTotalDistanceViewer.Sink {
    override fun show(distance: Double) {
        view.text = distance.toString()
    }

}