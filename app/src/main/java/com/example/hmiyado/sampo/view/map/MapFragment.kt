package com.example.hmiyado.sampo.view.map


import android.content.ContentResolver
import android.content.IntentFilter
import android.content.res.Configuration
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hmiyado.sampo.controller.common.IntentDispatcher
import com.example.hmiyado.sampo.controller.map.CompassViewController
import com.example.hmiyado.sampo.controller.map.MapViewController
import com.example.hmiyado.sampo.controller.map.ScaleViewController
import com.example.hmiyado.sampo.presenter.map.MapFragmentPresenter
import com.example.hmiyado.sampo.presenter.map.MapViewPresenter
import com.example.hmiyado.sampo.repository.compass.CompassSensor
import com.example.hmiyado.sampo.service.LocationSettingReceiver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.*
import com.example.hmiyado.sampo.usecase.map.interaction.mapUseCaseModule
import com.example.hmiyado.sampo.view.map.custom.CompassView
import com.example.hmiyado.sampo.view.map.custom.MapView
import com.example.hmiyado.sampo.view.map.custom.ScaleView
import com.example.hmiyado.sampo.view.map.ui.MapFragmentUi
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.appKodein
import com.trello.rxlifecycle.android.FragmentEvent
import com.trello.rxlifecycle.components.RxFragment
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
            bind<UseLocationSetting.Source>() with provider { instance<LocationSettingReceiver>() }
            bind<UseLocationSensor.Sink>() with provider { instance<IntentDispatcher>() }

            bind<MapFragmentPresenter>() with singleton { MapFragmentPresenter(this@MapFragment) }
            bind<MapViewPresenter>() with singleton { find<MapView>(MapFragmentUi.mapViewId).presenter }
            bind<MapViewController>() with singleton { find<MapView>(MapFragmentUi.mapViewId).controller }
            bind<CompassViewController>() with singleton { find<CompassView>(MapFragmentUi.compassViewId).controller }
            bind<ScaleViewController>() with singleton { find<ScaleView>(MapFragmentUi.scaleViewId).controller }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationSettingReceiver.setLocationServiceState(contentResolver, locationManager)
        activity.baseContext.registerReceiver(locationSettingReceiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //        Timber.d("on create view")
        return MapFragmentUi().createView(AnkoContext.create(activity.baseContext, this))
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
