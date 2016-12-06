package com.example.hmiyado.sampo.view.ui

import android.view.View
import com.example.hmiyado.sampo.presenter.MapFragmentPresenter
import com.example.hmiyado.sampo.view.MapFragment
import com.example.hmiyado.sampo.view.custom.mapView
import org.jetbrains.anko.*

/**
 * Created by hmiyado on 2016/08/08.
 * 地図がみえるFragment
 */
class MapFragmentUi(
        val mapFragmentPresenter: MapFragmentPresenter
): AnkoComponent<MapFragment> {
    companion object {
        val textViewID = View.generateViewId()
        val mapViewId = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<MapFragment>) = with(ui) {
        verticalLayout {
            mapView().lparams(width = matchParent, height = wrapContent) {
                id = mapViewId
            }
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
            button("Save") {
                onClick {
                    mapFragmentPresenter.saveLocationLog()
                }
            }
            button("Load All") {
                onClick {
                    mapFragmentPresenter.loadLocationLog()
                }
            }
            textView("please start") {
                id = textViewID
            }
        }
    }
}