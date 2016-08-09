package com.example.hmiyado.sampo.view.ui

import android.text.InputType
import android.view.View
import android.widget.TextView
import com.example.hmiyado.sampo.presenter.MapFragmentPresenter
import com.example.hmiyado.sampo.view.MapFragment
import org.jetbrains.anko.*

/**
 * Created by hmiyado on 2016/08/08.
 */
class MapFragmentUi(
        val mapFragmentPresenter: MapFragmentPresenter
): AnkoComponent<MapFragment> {
    companion object {
        val textViewID = View.generateViewId()
    }
    override fun createView(ui: AnkoContext<MapFragment>) = with(ui) {
        verticalLayout {
            button("Start") {
                onClick {
                    mapFragmentPresenter.startLocationLogging()
                }
            }
            button("Stop") {
                onClick {
                    mapFragmentPresenter.stopLocationLogging()
                }
            }
            textView("please start") {
                id = textViewID
                inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
            }
        }
    }
}