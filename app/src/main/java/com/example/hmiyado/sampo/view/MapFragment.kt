package com.example.hmiyado.sampo.view



import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.bindView
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.example.hmiyado.sampo.presenter.FragmentPresenter
import com.example.hmiyado.sampo.presenter.MapFragmentPresenter
import com.example.hmiyado.sampo.repository.LocationService
import com.example.hmiyado.sampo.repository.LocationServiceImpl
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
 */

class MapFragment: Fragment(),FragmentWithPresenter,AnkoLogger,KodeinInjected {
    override val injector = KodeinInjector()

    companion object{
        fun getInstance(): MapFragment{
            return MapFragment()
        }
    }

    private var presenter: MapFragmentPresenter? = null
    private val textView: TextView by bindView(MapFragmentUi.textViewID)
    var text: CharSequence
        get() { return textView.text }
        set(value: CharSequence) { textView.text = value}

    override fun getPresenter(): FragmentPresenter {
        return presenter!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appKodein())
        presenter = MapFragmentPresenter(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        debug { "on create view" }
        return MapFragmentUi(presenter!!).createView(AnkoContext.Companion.create(ctx, this))
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }
}
