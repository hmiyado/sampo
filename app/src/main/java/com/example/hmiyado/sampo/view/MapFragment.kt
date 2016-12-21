package com.example.hmiyado.sampo.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hmiyado.sampo.controller.CompassViewController
import com.example.hmiyado.sampo.controller.MapViewController
import com.example.hmiyado.sampo.presenter.CompassViewPresenter
import com.example.hmiyado.sampo.presenter.MapFragmentPresenter
import com.example.hmiyado.sampo.presenter.MapViewPresenter
import com.example.hmiyado.sampo.view.custom.CompassView
import com.example.hmiyado.sampo.view.custom.MapView
import com.example.hmiyado.sampo.view.ui.MapFragmentUi
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.trello.rxlifecycle.components.RxFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import timber.log.Timber

/**
 * Created by hmiyado on 2016/07/26.
 * 地図情報をだすフラグメント
 */

class MapFragment : RxFragment(), AnkoLogger, KodeinInjected {
    override val injector = KodeinInjector()

    companion object {
        fun getInstance(): MapFragment {
            return MapFragment()
        }
    }

    val presenter: MapFragmentPresenter by lazy { MapFragmentPresenter(this) }

    val mapViewPresenter: MapViewPresenter by lazy { find<MapView>(MapFragmentUi.mapViewId).presenter }
    val mapViewController: MapViewController by lazy { find<MapView>(MapFragmentUi.mapViewId).controller }

    val compassViewPresenter: CompassViewPresenter by lazy { find<CompassView>(MapFragmentUi.compassViewId).presenter }
    val compassViewController: CompassViewController by lazy { find<CompassView>(MapFragmentUi.compassViewId).controller }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appKodein())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.e("on create view")
        return MapFragmentUi().createView(AnkoContext.Companion.create(activity.baseContext, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }
}
