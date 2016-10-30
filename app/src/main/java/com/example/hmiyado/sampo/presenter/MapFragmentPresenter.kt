package com.example.hmiyado.sampo.presenter

import android.util.Log
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Time.Second
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.example.hmiyado.sampo.view.MapFragment
import com.github.salomonbrys.kodein.instance
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
    private val UseLocation: UseLocation by mapFragment.injector.instance<UseLocation>()

    init {
        loadLocationList = UseLocation.loadLocationList()

        UseLocation
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
        UseLocation.startLocationObserve()
    }

    fun stopLocationLogging() {
        Timber.d("StopButton onclicked")
        UseLocation.stopLocationObserve()
    }

    fun saveLocationLog() {
        Timber.d("Save Location ${tempLocationToString()}")
        UseLocation.saveLocationList(tempLocationList.asReversed())
    }

    fun loadLocationLog() {
        Timber.d("Load Location")
        tempLocationList.removeAll { true }
        Timber.d("before load Location=${tempLocationToString()}")
        tempLocationList.addAll(
                UseLocation.loadLocationList()
        )
        Timber.d("after load Location=${tempLocationToString()}")
        mapFragment.text = tempLocationToString()
    }
}