package com.example.hmiyado.sampo.controller

import android.graphics.Canvas
import com.example.hmiyado.sampo.view.custom.MapView
import rx.Observable

/**
 * Created by hmiyado on 2016/12/10.
 * @link MapView に対応するController
 */
class MapViewController(private val mapView: MapView) {

    fun getOnDrawObservable(): Observable<Canvas> {
        return mapView.getOnDrawSignal()
    }
}