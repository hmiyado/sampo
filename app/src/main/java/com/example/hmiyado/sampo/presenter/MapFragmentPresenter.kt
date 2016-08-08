package com.example.hmiyado.sampo.presenter

import android.util.Log
import com.example.hmiyado.sampo.SampoModule
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.kotlin.Time.Second
import com.example.hmiyado.sampo.view.MapFragment
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import org.jetbrains.anko.*

/**
 * Created by hmiyado on 2016/07/26.
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment
) : FragmentPresenter, AnkoLogger {
    private val tempLocationList: MutableList<Location> = mutableListOf()
    private val LOCATION_INTERVAL = Second(10)

    init {
        SampoModule.UseLocation
                .getLocationObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    Log.d("filter:observed item"," ${it.localDateTime.toString()}")
                    debug("filter:observed item ${it.localDateTime.toString()}")
                    if (tempLocationList.isNotEmpty() ){
                        debug("filter:temp.last $tempLocationList.last().toString()")
                    }
                    tempLocationList.isEmpty() || (it.localDateTime - tempLocationList.last().localDateTime).toSecond() >= LOCATION_INTERVAL
                }
                .subscribe { location: Location ->
                    debug("subscribe $location")
                    if ( tempLocationList.isNotEmpty()) {
                        debug("tempLocationList ${tempLocationList.joinToString { it.toString() }}")
                    }
                    tempLocationList.add(location)
                    val text = tempLocationList.fold(""){ text: String, location: Location ->
                        """
                        $text
                        ${location.toString()}
                        """
                    }
                    mapFragment.setText(text)
                }
    }

    fun startLocationLogging() {
        debug("StartButton onClicked")
        SampoModule.UseLocation.startLocationObserve()
    }

    fun stopLocationLogging() {
        debug("StopButton onclicked")
        SampoModule.UseLocation.stopLocationObserve()
    }
}