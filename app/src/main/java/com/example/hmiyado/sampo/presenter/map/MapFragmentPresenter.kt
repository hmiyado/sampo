package com.example.hmiyado.sampo.presenter.map

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.compass.CompassSensor
import com.example.hmiyado.sampo.usecase.map.interaction.*
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import com.example.hmiyado.sampo.view.map.MapFragment
import com.github.salomonbrys.kodein.instance
import rx.Observable
import rx.subscriptions.CompositeSubscription

/**
 * Created by hmiyado on 2016/07/26.
 * 地図フラグメントのプレゼンター
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment
) {
    private val subscriptions = CompositeSubscription()
    private val store: MapStore by mapFragment.injector.instance()
    private val useMapViewSource by lazy { mapFragment.mapViewPresenter }
    private val useMapViewSink by lazy { mapFragment.mapViewController }
    private val useCompassViewSink by lazy { mapFragment.compassViewController }
    private val useScaleViewSink by lazy { mapFragment.scaleViewController }
    private val useLocationSetting by lazy { mapFragment.locationSettingReceiver }
    private val useLocationSensor by lazy { mapFragment.intentDispatcher }
    private val compassService by mapFragment.injector.instance<CompassSensor>()

    fun onStart() {
        Observable.from(
                listOf(
                        DrawMap(store, useMapViewSink),
                        UpdateMapState(useMapViewSource, store),
                        UpdateOrientation(compassService, store),
                        DrawCompass(store, useCompassViewSink),
                        DrawScale(store, useScaleViewSink),
                        ControlLocationSensor(useLocationSetting, useLocationSensor)
                )
        ).forEach {
            subscriptions += it.subscriptions
        }
    }

    fun onResume() {
        compassService.startCompassService()
    }

    fun onPause() {
        compassService.stopCompassService()
    }

    fun onStop() {
        subscriptions.clear()
    }

}