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
    private val gpsStartButton: Button by bindView(R.id.gps_start_button)
    private val gpsStopButton: Button by bindView(R.id.gps_stop_button)
    private val textView: TextView by bindView(R.id.text_view)

    init {
    }

    override fun getPresenter(): FragmentPresenter {
        return presenter!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MapFragmentPresenter(
                this,
                UseLocation(
                    LocationServiceImpl(this.activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
                )
        )
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        gpsStartButton.setOnClickListener ( presenter?.createOnGpsStartButtonClickListener() )
        gpsStopButton.setOnClickListener( presenter?.createOnGpsStopButtonClickListener())
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

    fun setText(text: String){
        textView.setText(text)
    }
}
