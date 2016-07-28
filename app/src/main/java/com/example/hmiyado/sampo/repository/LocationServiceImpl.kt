package com.example.hmiyado.sampo.repository

import android.location.Location as AndroidLocation
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import com.example.hmiyado.sampo.domain.model.Location as SampoLocation
import rx.Observable
import rx.subjects.PublishSubject

/**
 * Created by hmiyado on 2016/07/27.
 */

// TODO object にする
class LocationServiceImpl(private val locationManager: LocationManager) : LocationService {
    companion object {
        private fun convertFromAndroidLocationToSampoLocation(androidLocation: AndroidLocation): SampoLocation {
            return SampoLocation(androidLocation.latitude, androidLocation.longitude)
        }
    }

    private val locationSubject: PublishSubject<SampoLocation>
    private val locationListener: LocationListener

    init {
        locationSubject = PublishSubject.create()
        locationListener = createLocationListener()
    }

    override fun getLocationObservable(): Observable<SampoLocation> {
        return locationSubject.asObservable().share()
    }

    override fun startLocationObserve() {
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                /*min time for update*/0,
                /*min distance for update*/0f,
                locationListener
        )
    }

    override fun stopLocationObserve() {
        locationManager.removeUpdates(locationListener)
    }


    private fun createLocationListener(): LocationListener {
        return object : LocationListener {
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                when (status) {
                    LocationProvider.AVAILABLE -> Log.v("Status", "AVAILABLE");
                    LocationProvider.OUT_OF_SERVICE -> Log.v("Status", "OUT_OF_SERVICE");
                    LocationProvider.TEMPORARILY_UNAVAILABLE -> Log.v("Status", "TEMPORARILY_UNAVAILABLE");
                }
                Log.d("locationlistener", "onsStatusChanged")
            }

            override fun onProviderEnabled(p0: String?) {
                Log.d("locationlistener", "onsProviderEnabled")
            }

            override fun onProviderDisabled(p0: String?) {
                Log.d("locationlistener", "onsProviderDisabled")
            }

            override fun onLocationChanged(location: AndroidLocation) {
                val sampoLoation = convertFromAndroidLocationToSampoLocation(location)
                Log.d("GPS", sampoLoation.toString())
                locationSubject.onNext(sampoLoation)
            }
        }
    }
}