package com.example.hmiyado.sampo.presenter

import android.util.Log
import com.example.hmiyado.sampo.SampoModule
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.kotlin.Time.Second
import com.example.hmiyado.sampo.view.MapFragment
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

import org.jetbrains.anko.*

/**
 * Created by hmiyado on 2016/07/26.
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment
) : FragmentPresenter {
    private val tempLocationList: MutableList<Location> = mutableListOf()
    private val loadLocationList: List<Location>
    private val LOCATION_INTERVAL = Second(10)

    init {
        loadLocationList = SampoModule.UseLocation.loadLocationList()

        SampoModule.UseLocation
                .getLocationObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    tempLocationList.isEmpty() || (it.localDateTime - tempLocationList.last().localDateTime).toSecond() >= LOCATION_INTERVAL
                }
                .subscribe { location: Location ->
                    Timber.d("subscribe " + location.toString())
                    tempLocationList.add(location)
                    mapFragment.text = tempLocationToString()
                    if (tempLocationList.isNotEmpty()) {
                        Timber.d("tempLocationListSize=${tempLocationList.size}")
                    }
                }
    }

    private fun tempLocationToString(): String {
        return tempLocationList.fold("") { text: String, location: Location ->
            """
            $text
            ${location.toString()}
            """
        }
    }

    fun startLocationLogging() {
        Timber.d("StartButton onClicked")
        SampoModule.UseLocation.startLocationObserve()
    }

    fun stopLocationLogging() {
        Timber.d("StopButton onclicked")
        SampoModule.UseLocation.stopLocationObserve()
    }

    fun saveLocationLog() {
        Timber.d("Save Location ${tempLocationToString()}")
        SampoModule.UseLocation.saveLocationList(tempLocationList.asReversed())
    }

    fun loadLocationLog() {
        Timber.d("Load Location")
        tempLocationList.removeAll { true }
        Timber.d("before load Location=${tempLocationToString()}")
        tempLocationList.addAll(
                SampoModule.UseLocation.loadLocationList()
        )
        Timber.d("after load Location=${tempLocationToString()}")
        mapFragment.text = tempLocationToString()
    }
}