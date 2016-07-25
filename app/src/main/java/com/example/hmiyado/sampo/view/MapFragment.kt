package com.example.hmiyado.sampo.view


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.presenter.MapFragmentPresenter

/**
 * Created by hmiyado on 2016/07/26.
 */

class MapFragment: Fragment() {
    companion object{
        fun getInstance(): MapFragment{
            return MapFragment()
        }
    }

    private val presenter: MapFragmentPresenter

    init {
        presenter = MapFragmentPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onPause() {
        super.onPause()
    }
}
