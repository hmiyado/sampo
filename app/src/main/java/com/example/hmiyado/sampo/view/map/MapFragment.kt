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
import com.example.hmiyado.sampo.service.LocationSettingReceiver
import com.example.hmiyado.sampo.view.map.custom.CompassView
import com.example.hmiyado.sampo.view.map.custom.MapView
import com.example.hmiyado.sampo.view.map.custom.ScaleView
import com.example.hmiyado.sampo.view.map.ui.MapFragmentUi
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.trello.rxlifecycle.components.RxFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import timber.log.Timber

/**
 * Created by hmiyado on 2016/07/26.
 * 地図情報をだすフラグメント
 */

class MapFragment : RxFragment(), KodeinInjected {
    override val injector = KodeinInjector()

    companion object {
        fun getInstance(): MapFragment {
            return MapFragment()
        }
    }

    val presenter: MapFragmentPresenter by lazy { MapFragmentPresenter(this) }

    val mapViewPresenter: MapViewPresenter by lazy { find<MapView>(MapFragmentUi.mapViewId).presenter }
    val mapViewController: MapViewController by lazy { find<MapView>(MapFragmentUi.mapViewId).controller }

    val compassViewController: CompassViewController by lazy { find<CompassView>(MapFragmentUi.compassViewId).controller }

    val scaleViewController: ScaleViewController by lazy { find<ScaleView>(MapFragmentUi.scaleViewId).controller }

    val locationSettingReceiver: LocationSettingReceiver by instance()
    val intentDispatcher: IntentDispatcher by instance()
    private val locationManager: LocationManager by instance()
    private val contentResolver: ContentResolver by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appKodein())

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
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("on resume")
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        Timber.d("on pause")
        presenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        Timber.d("on stop")
        presenter.onStop()
    }

    override fun onDestroy() {
        Timber.d("on destroy")
        super.onDestroy()
        activity.baseContext.unregisterReceiver(locationSettingReceiver)
    }
}
