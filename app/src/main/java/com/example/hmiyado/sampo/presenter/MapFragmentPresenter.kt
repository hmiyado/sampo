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

/**
 * Created by hmiyado on 2016/07/26.
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment
) : FragmentPresenter {
    private var tempLocation: Location = Location.empty()

    init {
        SampoModule.UseLocation
                .getLocationObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { tempLocation.isEmpty() || (it.localDateTime - tempLocation.localDateTime).toSecond() > Second(60) }
                .subscribe { location: Location ->
                    Log.d("subscribe", location.toString())
                    tempLocation = location
                    mapFragment.setText(tempLocation.toString())
                }
    }

    fun createOnGpsStartButtonClickListener(): (View) -> Unit {
        return {v: View ->
            Log.d("StartButton", "onClicked")
            SampoModule.UseLocation.startLocationObserve()
        }
    }

    fun createOnGpsStopButtonClickListener(): (View) -> Unit {
        return { v: View ->
            Log.d("StopButton", "onclicked")
            SampoModule.UseLocation.stopLocationObserve()
        }
    }
}