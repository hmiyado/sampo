package com.example.hmiyado.sampo.presenter.map

import android.content.Intent
import com.example.hmiyado.sampo.domain.store.MapStore
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.compass.CompassService
import com.example.hmiyado.sampo.repository.location.LocationServiceState
import com.example.hmiyado.sampo.service.LocationAndroidService
import com.example.hmiyado.sampo.usecase.map.compassview.interaction.StoreAndCompassViewInputToCompassViewOutputInteraction
import com.example.hmiyado.sampo.usecase.map.compassview.interaction.StoreToCompassViewOutputInteraction
import com.example.hmiyado.sampo.usecase.map.interaction.store.CompassServiceToStoreInteraction
import com.example.hmiyado.sampo.usecase.map.interaction.store.MapViewerInputToStoreInteraction
import com.example.hmiyado.sampo.usecase.map.mapview.interaction.StoreAndMapViewInputToMapViewOutputInteraction
import com.example.hmiyado.sampo.usecase.map.mapview.interaction.StoreToMapViewOutputInteraction
import com.example.hmiyado.sampo.usecase.map.scaleview.interaction.StoreAndScaleViewInputToScaleViewOutputInteraction
import com.example.hmiyado.sampo.usecase.map.scaleview.interaction.StoreToScaleViewOutputInteraction
import com.example.hmiyado.sampo.view.map.MapFragment
import com.github.salomonbrys.kodein.instance
import rx.Observable
import rx.subscriptions.CompositeSubscription
import timber.log.Timber

/**
 * Created by hmiyado on 2016/07/26.
 * 地図フラグメントのプレゼンター
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment
) {
    private val subscriptions = CompositeSubscription()
    private val store = MapStore
    private val useMapViewInput by lazy { mapFragment.mapViewPresenter }
    private val useMapViewOutput by lazy { mapFragment.mapViewController }
    private val useCompassViewInput by lazy { mapFragment.compassViewPresenter }
    private val useCompassViewOutput by lazy { mapFragment.compassViewController }
    private val useScaleViewInput by lazy { mapFragment.scaleViewPresenter }
    private val useScaleViewOutput by lazy { mapFragment.scaleViewController }
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

        subscriptions += mapFragment.locationSettingReceiver.onChangeLocationServiceState()
                .subscribe {
                    Timber.d(it.toString())
                    when (it) {
                        LocationServiceState.OFF -> {
                            mapFragment.activity.startService(Intent(mapFragment.activity.baseContext, LocationAndroidService::class.java)
                                    .putExtra(
                                            LocationAndroidService.IntentType::class.simpleName,
                                            LocationAndroidService.IntentType.STOP
                                    )
                            )
                        }
                        else                     -> {
                            mapFragment.activity.startService(Intent(mapFragment.activity.baseContext, LocationAndroidService::class.java)
                                    .putExtra(
                                            LocationAndroidService.IntentType::class.simpleName,
                                            LocationAndroidService.IntentType.START
                                    )
                            )
                        }
                    }
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