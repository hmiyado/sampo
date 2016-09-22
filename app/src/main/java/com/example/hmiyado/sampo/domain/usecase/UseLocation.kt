package com.example.hmiyado.sampo.domain.usecase

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.repository.LocationRepository
import com.example.hmiyado.sampo.repository.LocationService
import rx.Observable


/**
 * Created by hmiyado on 2016/07/28.
 * アプリケーションがロケーションを使うためのもろもろ
 */
class UseLocation (
        private val locationService: LocationService,
        private val locationRepository: LocationRepository
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

    fun saveLocation(location: Location) {
        locationRepository.saveLocation(location)
    }

    fun saveLocationList(locationList: List<Location>) {
        locationRepository.saveLocationList(locationList)
    }

    fun loadLocationList(): List<Location> {
        return locationRepository.loadLocationList()
    }

    fun determineDistance(location1: Location, location2: Location): Double {
        return locationService.determineDistance(location1, location2)
    }
}