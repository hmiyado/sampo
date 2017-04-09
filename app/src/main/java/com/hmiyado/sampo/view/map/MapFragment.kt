package com.hmiyado.sampo.view.map


import android.content.ContentResolver
import android.content.IntentFilter
import android.content.res.Configuration
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.appKodein
import com.hmiyado.sampo.controller.common.IntentDispatcher
import com.hmiyado.sampo.controller.map.*
import com.hmiyado.sampo.presenter.map.AddMarkerButtonPresenter
import com.hmiyado.sampo.presenter.map.MapFragmentPresenter
import com.hmiyado.sampo.presenter.map.MapViewPresenter
import com.hmiyado.sampo.repository.compass.CompassSensor
import com.hmiyado.sampo.service.LocationSettingReceiver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.*
import com.hmiyado.sampo.usecase.map.interaction.mapUseCaseModule
import com.hmiyado.sampo.view.map.custom.CompassView
import com.hmiyado.sampo.view.map.custom.MapView
import com.hmiyado.sampo.view.map.custom.ScaleView
import com.hmiyado.sampo.view.map.ui.MapFragmentUi
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import timber.log.Timber

/**
 * Created by hmiyado on 2016/07/26.
 * 地図情報をだすフラグメント
 */

class MapFragment : RxFragment(), LazyKodeinAware {
    override val kodein: LazyKodein = LazyKodein {
        Kodein {
            extend(appKodein())
            import(mapUseCaseModule)

            bind<UseMapView.Sink>() with provider { instance<MapViewController>() }
            bind<UseMapView.Source>() with provider { instance<MapViewPresenter>() }
            bind<UseCompassView.Sink>() with provider { instance<CompassViewController>() }
            bind<UseScaleView.Sink>() with provider { instance<ScaleViewController>() }
            bind<UseScoreView.Sink>() with provider { instance<ScoreViewController>() }
            bind<UseLocationSetting.Source>() with provider { instance<LocationSettingReceiver>() }
            bind<UseLocationSensor.Sink>() with provider { instance<IntentDispatcher>() }
            bind<UseMarkerAdder.Source>() with provider { instance<AddMarkerButtonPresenter>() }
            bind<UseMarkerAdder.Sink>() with provider { instance<AddMarkerButtonController>() }
            bind<UseMarkerView.Sink>() with provider { instance<MarkersViewController>() }

            bind<MapFragmentPresenter>() with singleton { MapFragmentPresenter(this@MapFragment) }
            bind<MapViewPresenter>() with singleton { find<MapView>(ui.mapViewId).presenter }
            bind<MapViewController>() with singleton { find<MapView>(ui.mapViewId).controller }
            bind<MarkersViewController>() with singleton { MarkersViewController(find<RelativeLayout>(ui.markerCanvasId)) }
            bind<CompassViewController>() with singleton { find<CompassView>(ui.compassViewId).controller }
            bind<ScaleViewController>() with singleton { find<ScaleView>(ui.scaleViewId).controller }
            bind<ScoreViewController>() with singleton { ScoreViewController(find<TextView>(ui.scoreTextViewId)) }
            bind<AddMarkerButtonPresenter>() with singleton { AddMarkerButtonPresenter(find<FloatingActionButton>(ui.addMarkerButtonId)) }
            bind<AddMarkerButtonController>() with singleton { AddMarkerButtonController(find<FloatingActionButton>(ui.addMarkerButtonId)) }
        }
    }

    companion object {
        fun getInstance(): MapFragment {
            return MapFragment()
        }
    }

    val presenter: MapFragmentPresenter by instance()
    val interactions: List<Interaction<*>> by instance()

    val locationSettingReceiver: LocationSettingReceiver by instance()
    val compassSensor: CompassSensor by instance()
    private val locationManager: LocationManager by instance()
    private val contentResolver: ContentResolver by instance()
    val ui: MapFragmentUi = MapFragmentUi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationSettingReceiver.setLocationServiceState(contentResolver, locationManager)
        activity.baseContext.registerReceiver(locationSettingReceiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))
        Timber.d("onCreate: $locationSettingReceiver")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //        Timber.d("on create view")
        return ui.createView(AnkoContext.create(activity.baseContext, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        Timber.d("on configuration changed")
        super.onConfigurationChanged(newConfig)
    }

    override fun onStart() {
        super.onStart()
        Timber.d("on start")
        Interaction.Builder(this, FragmentEvent.STOP).buildAll(interactions)
    }

    override fun onResume() {
        super.onResume()
        Timber.d("on resume")
        compassSensor.startCompassService()
    }

    override fun onPause() {
        super.onPause()
        Timber.d("on pause")
        compassSensor.stopCompassService()
    }

    override fun onStop() {
        super.onStop()
        Timber.d("on stop")
    }

    override fun onDestroy() {
        Timber.d("on destroy")
        super.onDestroy()
        activity.baseContext.unregisterReceiver(locationSettingReceiver)
    }
}
