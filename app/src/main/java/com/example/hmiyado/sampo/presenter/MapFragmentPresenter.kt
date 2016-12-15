package com.example.hmiyado.sampo.presenter

import com.example.hmiyado.sampo.controller.MapViewController
import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.domain.usecase.map.MapViewerInputToMapViewerOutputInteraction
import com.example.hmiyado.sampo.domain.usecase.map.MapViewerInputToStoreInteraction
import com.example.hmiyado.sampo.domain.usecase.map.StoreToMapViewerOutputInteraction
import com.example.hmiyado.sampo.view.MapFragment
import com.github.salomonbrys.kodein.factory

/**
 * Created by hmiyado on 2016/07/26.
 * 地図フラグメントのプレゼンター
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment
) {
    private val store = Store()
    private val mapViewerInputToStoreInteractionFactory: (Pair<MapViewPresenter, Store>) -> MapViewerInputToStoreInteraction
            by mapFragment.injector.factory()
    private val storeToMapViewerOutputInteractionFactory: (Pair<Store, MapViewController>) -> StoreToMapViewerOutputInteraction
            by mapFragment.injector.factory()
    private val mapViewerInputToMapViewerOutputInteractionFactory: (Pair<MapViewPresenter, MapViewController>) -> MapViewerInputToMapViewerOutputInteraction
            by mapFragment.injector.factory()

    fun onStart() {
        mapViewerInputToStoreInteractionFactory(Pair(mapFragment.mapViewPresenter, store))
        mapViewerInputToMapViewerOutputInteractionFactory(Pair(mapFragment.mapViewPresenter, mapFragment.mapViewController))
        storeToMapViewerOutputInteractionFactory(Pair(store, mapFragment.mapViewController))
    }

}