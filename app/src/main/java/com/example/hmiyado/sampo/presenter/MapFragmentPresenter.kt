package com.example.hmiyado.sampo.presenter

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.example.hmiyado.sampo.repository.compass.CompassService
import com.example.hmiyado.sampo.view.MapFragment
import com.github.salomonbrys.kodein.instance
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2016/07/26.
 * 地図フラグメントのプレゼンター
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment
) {
    private val tempLocationList: MutableList<Location> = mutableListOf()
    private val loadLocationList: List<Location>
    val UseLocation: UseLocation by mapFragment.injector.instance()
    val CompassService: CompassService by mapFragment.injector.instance()

    init {
        loadLocationList = UseLocation.loadLocationList()

        UseLocation
                .getLocationObservable()
                .subscribeOn(AndroidSchedulers.mainThread())
                .throttleLast(10, TimeUnit.SECONDS)
                .filter { tempLocationList.isEmpty() }
                .subscribe { location: Location ->
                    Timber.d("subscribe " + location.toString())
                    //tempLocationList.add(location)
                    //mapFragment.text = tempLocationToString()
                    if (tempLocationList.isNotEmpty()) {
                        Timber.d("tempLocationListSize=${tempLocationList.size}")
                    }
                }

        CompassService
                .getCompassService()
                .throttleLast(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { orientation ->
                    Timber.e("$orientation")
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