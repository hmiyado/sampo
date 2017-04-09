package com.hmiyado.sampo.view.map.ui

import android.graphics.Color
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import com.hmiyado.sampo.BuildConfig
import com.hmiyado.sampo.R
import com.hmiyado.sampo.view.common.sampoToolbar
import com.hmiyado.sampo.view.map.MapFragment
import com.hmiyado.sampo.view.map.custom.compassView
import com.hmiyado.sampo.view.map.custom.mapView
import com.hmiyado.sampo.view.map.custom.scaleView
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
    val scoreLayoutId = View.generateViewId()
    val scoreTextViewId = View.generateViewId()

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
                }.lparams(width = matchParent, height = matchParent)

                relativeLayout {
                    compassView {
                        id = compassViewId
                    }.lparams(width = dip(100), height = dip(100)) {
                        alignParentTop()
                        alignParentLeft()
                    }
                    linearLayout {
                        id = scoreLayoutId
                        orientation = LinearLayout.VERTICAL
                        textView {
                            text = "なわばり"
                            textSize = 17f
                            textColor = Color.BLACK
                        }
                        textView {
                            id = scoreTextViewId
                            text = "123456789"
                            textColor = Color.BLACK
                            textSize = 27f
                        }
                    }.lparams(width = matchParent, height = wrapContent) {
                        alignParentBottom()
                        alignParentLeft()
                    }
                    scaleView {
                        id = scaleViewId
                        visibility = if (BuildConfig.DEBUG) {
                            VISIBLE
                        } else {
                            GONE
                        }
                    }.lparams(width = dip(200), height = dip(50)) {
                        topOf(scoreLayoutId)
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