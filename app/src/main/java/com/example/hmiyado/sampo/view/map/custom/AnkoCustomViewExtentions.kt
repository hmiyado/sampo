package com.example.hmiyado.sampo.view.map.custom

import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

/**
 * Created by hmiyado on 2016/12/21.
 * Anko で必要な拡張関数をまとめたもの．
 */

//-- settings for anko custom view
// map view
inline fun ViewManager.mapView(theme: Int = 0, init: MapView.() -> Unit) = ankoView(::MapView, theme, init)

// compass view
inline fun ViewManager.compassView(theme: Int = 0, init: CompassView.() -> Unit) = ankoView(::CompassView, theme, init)

// scale view
inline fun ViewManager.scaleView(theme: Int = 0, init: ScaleView.() -> Unit) = ankoView(::ScaleView, theme, init)
