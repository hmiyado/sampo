package com.example.hmiyado.sampo.presenter

import android.util.Log
import android.view.View
import com.example.hmiyado.sampo.SampoModule
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.kotlin.Time.Second
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.example.hmiyado.sampo.view.MapFragment
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by hmiyado on 2016/07/26.
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment
) : FragmentPresenter {
    private val tempLocationList: MutableList<Location> = mutableListOf()
    private val LOCATION_INTERVAL = Second(10)

    init {
        SampoModule.UseLocation
                .getLocationObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    Timber.i("filter:observed item ${it.localDateTime.toString()}")
                    Timber.i("tempLocationList size ${tempLocationList.size}")
                    if (tempLocationList.isNotEmpty() ){
                        Timber.i("filter:temp.last " + tempLocationList.last().toString())
                    }
                    tempLocationList.isEmpty() || (it.localDateTime - tempLocationList.last().localDateTime).toSecond() >= LOCATION_INTERVAL
                }
                .subscribe { location: Location ->
                    Timber.d("subscribe", location.toString())
                    if ( tempLocationList.isNotEmpty()) {
                        Timber.d("tempLocationList " + tempLocationList.joinToString { it.toString() })
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

    fun createOnGpsStartButtonClickListener(): (View) -> Unit {
        return {v: View ->
            Timber.d("StartButton", "onClicked")
            SampoModule.UseLocation.startLocationObserve()
        }
    }

    fun createOnGpsStopButtonClickListener(): (View) -> Unit {
        return { v: View ->
            Timber.d("StopButton", "onclicked")
            SampoModule.UseLocation.stopLocationObserve()
        }
    }
}