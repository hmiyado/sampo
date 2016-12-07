package com.example.hmiyado.sampo.presenter

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.example.hmiyado.sampo.view.MapFragment
import com.github.salomonbrys.kodein.instance
import timber.log.Timber

/**
 * Created by hmiyado on 2016/07/26.
 * 地図フラグメントのプレゼンター
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment
) {
    private val tempLocationList: MutableList<Location> = mutableListOf()
    val UseLocation: UseLocation by mapFragment.injector.instance()
    val mapViewPresenter: MapViewPresenter by lazy {
        mapFragment.mapViewPresenter
    }

    private fun tempLocationToString(): String {
        return tempLocationList.fold("") { text: String, location: Location ->
            """
            $text
            ${location.toString()}
            """
        }
    }

    fun onResume() {
        mapViewPresenter.startGeometricalLogging()
    }

    fun onPause() {
        mapViewPresenter.stopGeometricalLogging()
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