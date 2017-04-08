package com.example.hmiyado.sampo.controller.map

import android.graphics.Color
import android.view.View
import com.example.hmiyado.sampo.controller.ViewController
import com.example.hmiyado.sampo.usecase.map.UseMarkerAdder
import org.jetbrains.anko.backgroundColor

/**
 * Created by hmiyado on 2017/04/08.
 */
class AddMarkerButtonController(view: View) : ViewController<View>(view), UseMarkerAdder.Sink {
    override fun setEnabled(enabled: Boolean) {
        view.isEnabled = enabled
        if (view.isEnabled) {
            view.alpha = 1.0f
        } else {
            view.backgroundColor = Color.GRAY
        }
    }
}