package com.example.hmiyado.sampo.presenter

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.repository.compass.CompassService
import com.example.hmiyado.sampo.repository.location.LocationService
import com.example.hmiyado.sampo.usecase.CompassView.UseCompassViewInput
import com.example.hmiyado.sampo.usecase.CompassView.UseCompassViewOutput
import com.example.hmiyado.sampo.usecase.MapView.UseMapViewInput
import com.example.hmiyado.sampo.usecase.MapView.UseMapViewOutput
import com.example.hmiyado.sampo.usecase.interaction.to_compassview.StoreAndCompassViewInputToCompassViewOutputInteraction
import com.example.hmiyado.sampo.usecase.interaction.to_compassview.StoreToCompassViewOutputInteraction
import com.example.hmiyado.sampo.usecase.interaction.to_mapview.StoreAndMapViewInputToMapViewOutputInteraction
import com.example.hmiyado.sampo.usecase.interaction.to_mapview.StoreToMapViewOutputInteraction
import com.example.hmiyado.sampo.usecase.interaction.to_store.CompassServiceToStoreInteraction
import com.example.hmiyado.sampo.usecase.interaction.to_store.LocationServiceToStoreInteraction
import com.example.hmiyado.sampo.usecase.interaction.to_store.MapViewerInputToStoreInteraction
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
    private val useMapViewInput by lazy { UseMapViewInput(mapFragment.mapViewPresenter) }
    private val useMapViewOutput by lazy { UseMapViewOutput(mapFragment.mapViewController) }
    private val useCompassViewInput by lazy { UseCompassViewInput(mapFragment.compassViewPresenter) }
    private val useCompassViewOutput by lazy { UseCompassViewOutput(mapFragment.compassViewController) }
    private val locationService: LocationService by mapFragment.injector.instance<LocationService>()
    private val compassService by mapFragment.injector.instance<CompassService>()

    fun onStart() {
        StoreAndMapViewInputToMapViewOutputInteraction(store, useMapViewInput, useMapViewOutput)
        MapViewerInputToStoreInteraction(useMapViewInput, store)
        StoreToMapViewOutputInteraction(store, useMapViewOutput)
        LocationServiceToStoreInteraction(locationService, store)
        CompassServiceToStoreInteraction(compassService, store)
        StoreAndCompassViewInputToCompassViewOutputInteraction(store, useCompassViewInput, useCompassViewOutput)
        StoreToCompassViewOutputInteraction(store, useCompassViewOutput)
    }

    fun onResume() {
        locationService.startLocationObserve()
        compassService.startCompassService()
    }

    fun onPause() {
        locationService.stopLocationObserve()
        compassService.stopCompassService()
    }

}