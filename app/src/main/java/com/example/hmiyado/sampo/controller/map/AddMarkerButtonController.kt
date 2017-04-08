package com.example.hmiyado.sampo.controller.map

import android.view.View
import com.example.hmiyado.sampo.controller.ViewController
import com.example.hmiyado.sampo.usecase.map.UseMarkerAdder

/**
 * Created by hmiyado on 2017/04/08.
 */
class AddMarkerButtonController(view: View) : ViewController<View>(view), UseMarkerAdder.Sink {
    override fun setEnabled(enabled: Boolean) {
        view.isEnabled = enabled
    }
}