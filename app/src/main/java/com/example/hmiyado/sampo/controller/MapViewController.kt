package com.example.hmiyado.sampo.controller

import com.example.hmiyado.sampo.view.custom.MapView
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/10.
 * @link MapView に対応するController
 */
class MapViewController(private val mapView: MapView) {

    fun bindMapViewAndSubscribe(observable: Observable<*>): Subscription {
        return observable.bindToLifecycle(mapView).subscribe()
    }
}