package com.example.hmiyado.sampo.domain.usecase

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.repository.LocationService
import rx.Observable


/**
 * Created by hmiyado on 2016/07/28.
 */
class UseLocation (
        private val locationService: LocationService
){
    init {
    }

    fun getLocationObservable(): Observable<Location> {
        return locationService.getLocationObservable()
    }

    fun startLocationObserve() {
        locationService.startLocationObserve()
    }
    fun stopLocationObserve() {
        locationService.stopLocationObserve()
    }
}