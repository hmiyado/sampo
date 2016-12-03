package com.example.hmiyado.sampo.view



import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.example.hmiyado.sampo.presenter.FragmentPresenter
import com.example.hmiyado.sampo.presenter.MapFragmentPresenter
import com.example.hmiyado.sampo.view.ui.MapFragmentUi
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.support.v4.ctx

/**
 * Created by hmiyado on 2016/07/26.
 * 地図情報をだすフラグメント
 */

class MapFragment: Fragment(),FragmentWithPresenter,AnkoLogger,KodeinInjected {
    override val injector = KodeinInjector()

    companion object{
        fun getInstance(): MapFragment{
            return MapFragment()
        }
    }

    private val presenter: MapFragmentPresenter by lazy {
        MapFragmentPresenter(this)
    }
    private val textView: TextView by bindView(MapFragmentUi.textViewID)
    var text: CharSequence
        get() { return textView.text }
        set(value) {
            textView.text = value
        }

    override fun getPresenter(): FragmentPresenter {
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appKodein())
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        debug { "on create view" }
        return MapFragmentUi(presenter).createView(AnkoContext.Companion.create(ctx, this))
    }

    override fun onResume() {
        super.onResume()
        presenter.CompassService.startCompassService()
    }

    override fun onPause() {
        super.onPause()
        presenter.CompassService.stopCompassService()
    }
}
