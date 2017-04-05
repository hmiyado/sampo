package com.example.hmiyado.sampo.view.map.ui

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.view.common.sampoToolbar
import com.example.hmiyado.sampo.view.map.MapFragment
import com.example.hmiyado.sampo.view.map.custom.compassView
import com.example.hmiyado.sampo.view.map.custom.mapView
import com.example.hmiyado.sampo.view.map.custom.scaleView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton

/**
 * Created by hmiyado on 2016/08/08.
 * 地図がみえるFragment
 */
class MapFragmentUi() : AnkoComponent<MapFragment> {
    val toolbarId = View.generateViewId()
    val mapViewId = View.generateViewId()
    val compassViewId = View.generateViewId()
    val scaleViewId = View.generateViewId()
    val markerCanvasId = View.generateViewId()
    val addMarkerButtonId = View.generateViewId()

    override fun createView(ui: AnkoContext<MapFragment>) = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            sampoToolbar {
                id = toolbarId
            }
            relativeLayout {
                mapView {
                    id = mapViewId
                }.lparams(width = matchParent, height = matchParent)
                relativeLayout {
                    id = markerCanvasId
                    // TODO 取り除く
                    // marker をちゃんと配置できることがわかるようになるまでの簡易的な措置
                    backgroundColor = Color.BLUE
                    alpha = 0.1f
                }.lparams(width = matchParent, height = matchParent)

                relativeLayout {
                    compassView {
                        id = compassViewId
                    }.lparams(width = dip(100), height = dip(100)) {
                        alignParentTop()
                        alignParentLeft()
                    }
                    scaleView {
                        id = scaleViewId
                    }.lparams(width = dip(200), height = dip(50)) {
                        alignParentBottom()
                        alignParentLeft()
                    }
                    floatingActionButton(theme = R.style.AppTheme) {
                        id = addMarkerButtonId
                        imageResource = R.drawable.ic_add_location_white_24dp
                    }.lparams {
                        alignParentBottom()
                        alignParentEnd()
                    }

                    lparams {
                        padding = dip(16)
                    }
                }
            }
        }
    }
}