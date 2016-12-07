package com.example.hmiyado.sampo.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.example.hmiyado.sampo.presenter.MapFragmentPresenter
import com.example.hmiyado.sampo.presenter.MapViewPresenter
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

    val presenter: MapFragmentPresenter by lazy {
        MapFragmentPresenter(this)
    }
    private val textView: TextView by bindView(MapFragmentUi.textViewID)
    var text: CharSequence
        get() {
            return textView.text
        }
        set(value) {
            textView.text = value
        }

    val mapViewPresenter: MapViewPresenter by lazy {
        val mapView: MapView = find(MapFragmentUi.mapViewId)
        mapView.presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appKodein())
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.e("on create view")
        return MapFragmentUi(presenter).createView(AnkoContext.Companion.create(activity.baseContext, this))
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
