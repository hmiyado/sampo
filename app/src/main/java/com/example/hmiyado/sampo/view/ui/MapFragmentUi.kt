package com.example.hmiyado.sampo.view.ui

import android.view.View
import com.example.hmiyado.sampo.view.MapFragment
import com.example.hmiyado.sampo.view.custom.compassView
import com.example.hmiyado.sampo.view.custom.mapView
import org.jetbrains.anko.*

/**
 * Created by hmiyado on 2016/08/08.
 * 地図がみえるFragment
 */
class MapFragmentUi() : AnkoComponent<MapFragment> {
    companion object {
        val mapViewId = View.generateViewId()
        val compassViewId = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<MapFragment>) = with(ui) {
        verticalLayout {
            compassView {
                id = compassViewId
            }.lparams(width = matchParent, height = dip(100))
            mapView {
                id = mapViewId
            }.lparams(width = matchParent, height = wrapContent)
        }
    }
}