package com.example.hmiyado.sampo.presenter

import android.util.Log
import android.view.View
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.example.hmiyado.sampo.view.MapFragment
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by hmiyado on 2016/07/26.
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment,
        private val useLocation: UseLocation
) : FragmentPresenter {
    private var tempLocation: Location = Location.empty()

    init {
        useLocation
                .getLocationObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { location: Location ->
                    Log.d("subscribe", location.toString())
                    tempLocation = location
                    mapFragment.setText(tempLocation.toString())
                }

    }

    fun createOnGpsStartButtonClickListener(): (View) -> Unit {
        return {v: View ->
            Log.d("StartButton", "onClicked")
            useLocation.startLocationObserve()
        }
    }

    fun createOnGpsStopButtonClickListener(): (View) -> Unit {
        return { v: View ->
            Log.d("StopButton", "onclicked")
            useLocation.stopLocationObserve()
        }
    }
}