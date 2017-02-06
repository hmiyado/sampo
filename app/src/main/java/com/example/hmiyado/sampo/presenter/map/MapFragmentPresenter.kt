package com.example.hmiyado.sampo.presenter.map

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.compass.CompassService
import com.example.hmiyado.sampo.usecase.compassview.UseCompassViewInput
import com.example.hmiyado.sampo.usecase.compassview.UseCompassViewOutput
import com.example.hmiyado.sampo.usecase.compassview.interaction.StoreAndCompassViewInputToCompassViewOutputInteraction
import com.example.hmiyado.sampo.usecase.compassview.interaction.StoreToCompassViewOutputInteraction
import com.example.hmiyado.sampo.usecase.interaction.store.CompassServiceToStoreInteraction
import com.example.hmiyado.sampo.usecase.interaction.store.MapViewerInputToStoreInteraction
import com.example.hmiyado.sampo.usecase.mapview.UseMapViewInput
import com.example.hmiyado.sampo.usecase.mapview.UseMapViewOutput
import com.example.hmiyado.sampo.usecase.mapview.interaction.StoreAndMapViewInputToMapViewOutputInteraction
import com.example.hmiyado.sampo.usecase.mapview.interaction.StoreToMapViewOutputInteraction
import com.example.hmiyado.sampo.usecase.scaleview.UseScaleViewInput
import com.example.hmiyado.sampo.usecase.scaleview.UseScaleViewOutput
import com.example.hmiyado.sampo.usecase.scaleview.interaction.StoreAndScaleViewInputToScaleViewOutputInteraction
import com.example.hmiyado.sampo.usecase.scaleview.interaction.StoreToScaleViewOutputInteraction
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
    private val store = Store
    private val useMapViewInput by lazy { UseMapViewInput(mapFragment.mapViewPresenter) }
    private val useMapViewOutput by lazy { UseMapViewOutput(mapFragment.mapViewController) }
    private val useCompassViewInput by lazy { UseCompassViewInput(mapFragment.compassViewPresenter) }
    private val useCompassViewOutput by lazy { UseCompassViewOutput(mapFragment.compassViewController) }
    private val useScaleViewInput by lazy { UseScaleViewInput(mapFragment.scaleViewPresenter) }
    private val useScaleViewOutput by lazy { UseScaleViewOutput(mapFragment.scaleViewController) }
    private val compassService by mapFragment.injector.instance<CompassService>()

    fun onStart() {
        Observable.from(
                listOf(
                        StoreAndMapViewInputToMapViewOutputInteraction(store, useMapViewInput, useMapViewOutput),
                        MapViewerInputToStoreInteraction(useMapViewInput, store),
                        StoreToMapViewOutputInteraction(store, useMapViewOutput),
                        CompassServiceToStoreInteraction(compassService, store),
                        StoreAndCompassViewInputToCompassViewOutputInteraction(store, useCompassViewInput, useCompassViewOutput),
                        StoreToCompassViewOutputInteraction(store, useCompassViewOutput),
                        StoreAndScaleViewInputToScaleViewOutputInteraction(store, useScaleViewInput, useScaleViewOutput),
                        StoreToScaleViewOutputInteraction(store, useScaleViewOutput)
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