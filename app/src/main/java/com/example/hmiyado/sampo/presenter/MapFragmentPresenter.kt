package com.example.hmiyado.sampo.presenter

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.repository.location.LocationService
import com.example.hmiyado.sampo.usecase.*
import com.example.hmiyado.sampo.view.MapFragment
import com.github.salomonbrys.kodein.instance

/**
 * Created by hmiyado on 2016/07/26.
 * 地図フラグメントのプレゼンター
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment
) {
    private val store = Store()
    private val useMapViewerInput by lazy { UseMapViewerInput(mapFragment.mapViewPresenter) }
    private val useMapViewerOutput by lazy { UseMapViewerOutput(mapFragment.mapViewController) }
    private val locationService: LocationService by mapFragment.injector.instance<LocationService>()

    fun onStart() {
        StoreAndMapViewerInputToMapViewerOutputInteraction(store, useMapViewerInput, useMapViewerOutput)
        MapViewerInputToStoreInteraction(useMapViewerInput, store)
        StoreToMapViewerOutputInteraction(store, useMapViewerOutput)
        LocationServiceToStoreInteraction(locationService, store)
    }

    fun onResume() {
        locationService.startLocationObserve()
    }

    fun onPause() {
        locationService.stopLocationObserve()
    }

}