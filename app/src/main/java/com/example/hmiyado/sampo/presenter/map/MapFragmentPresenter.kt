package com.example.hmiyado.sampo.presenter.map

import com.example.hmiyado.sampo.domain.math.Measurement
import com.example.hmiyado.sampo.repository.compass.CompassSensor
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.interaction.*
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import com.example.hmiyado.sampo.view.map.MapFragment
import com.github.salomonbrys.kodein.instance
import com.trello.rxlifecycle.android.FragmentEvent
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
    private val measurement by mapFragment.injector.instance<Measurement>()
    private val compassService by mapFragment.injector.instance<CompassSensor>()

    fun onStart() {
        val builder = Interaction.Builder(mapFragment, FragmentEvent.STOP)

        listOf(
                DrawMap(store, measurement, useMapViewSink),
                UpdateRotateAngle(useMapViewSource, store),
                UpdateScale(useMapViewSource, store),
                UpdateOrientation(compassService, store),
                DrawCompass(store, useCompassViewSink),
                DrawScale(store, useScaleViewSink),
                ControlLocationSensor(useLocationSetting, useLocationSensor)
        ).forEach {
            builder.build(it)
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