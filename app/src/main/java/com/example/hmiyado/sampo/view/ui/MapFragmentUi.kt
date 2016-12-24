package com.example.hmiyado.sampo.view.ui

import android.view.View
import com.example.hmiyado.sampo.view.MapFragment
import com.example.hmiyado.sampo.view.custom.compassView
import com.example.hmiyado.sampo.view.custom.mapView
import com.example.hmiyado.sampo.view.custom.scaleView
import org.jetbrains.anko.*

/**
 * Created by hmiyado on 2016/08/08.
 * 地図がみえるFragment
 */
class MapFragmentUi() : AnkoComponent<MapFragment> {
    companion object {
        val mapViewId = View.generateViewId()
        val compassViewId = View.generateViewId()
        val scaleViewId = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<MapFragment>) = with(ui) {
        relativeLayout {
            mapView {
                id = mapViewId
            }.lparams(width = matchParent, height = matchParent)
            compassView {
                id = compassViewId
            }.lparams(width = dip(100), height = dip(100)) {
                alignParentTop()
                alignParentLeft()
            }
            scaleView {
                id = scaleViewId
            }.lparams(width = dip(500), height = dip(100)) {
                alignParentBottom()
                alignParentLeft()
            }
        }
    }
}