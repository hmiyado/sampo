package com.example.hmiyado.sampo.view



import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.bindView
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.presenter.FragmentPresenter
import com.example.hmiyado.sampo.presenter.MapFragmentPresenter
import com.example.hmiyado.sampo.repository.LocationService
import com.example.hmiyado.sampo.repository.LocationServiceImpl

/**
 * Created by hmiyado on 2016/07/26.
 */

class MapFragment: Fragment(),FragmentWithPresenter {

    companion object{
        fun getInstance(): MapFragment{
            return MapFragment()
        }
    }

    private var presenter: MapFragmentPresenter? = null
    private val gpsButton: Button by bindView(R.id.gps_button)

    init {
    }

    override fun getPresenter(): FragmentPresenter {
        return presenter!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MapFragmentPresenter(
                this,
                LocationServiceImpl(this.activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        )
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        gpsButton.setOnClickListener ( presenter?.createOnGpsButtonClickListener() )

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }
}
