package com.example.hmiyado.sampo.presenter.map

import android.content.Intent
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.compass.CompassService
import com.example.hmiyado.sampo.repository.location.LocationServiceState
import com.example.hmiyado.sampo.service.LocationAndroidService
import com.example.hmiyado.sampo.usecase.map.interaction.*
import com.example.hmiyado.sampo.usecase.map.store.MapStore
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
    private val store: MapStore by mapFragment.injector.instance()
    private val useMapViewSource by lazy { mapFragment.mapViewPresenter }
    private val useMapViewSink by lazy { mapFragment.mapViewController }
    private val useCompassViewSink by lazy { mapFragment.compassViewController }
    private val useScaleViewSink by lazy { mapFragment.scaleViewController }
    private val compassService by mapFragment.injector.instance<CompassService>()

    fun onStart() {
        Observable.from(
                listOf(
                        DrawMap(store, useMapViewSink),
                        UpdateMapState(useMapViewSource, store),
                        UpdateOrientation(compassService, store),
                        DrawCompass(store, useCompassViewSink),
                        DrawScale(store, useScaleViewSink)
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