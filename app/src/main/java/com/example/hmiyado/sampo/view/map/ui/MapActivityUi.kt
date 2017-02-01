package com.example.hmiyado.sampo.view.map.ui

import android.view.View
import com.example.hmiyado.sampo.view.map.MapActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout

/**
 * Created by hmiyado on 2016/08/08.
 */
class MapActivityUi : AnkoComponent<MapActivity> {
    companion object {
        val ROOT_VIEW_ID = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<MapActivity>) = with(ui) {
        relativeLayout {
            id = ROOT_VIEW_ID
        }
    }
}