package com.example.hmiyado.sampo.repository.location

import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Bundle
import org.threeten.bp.Instant
import rx.Observable
import rx.subjects.PublishSubject
import timber.log.Timber
import android.location.Location as AndroidLocation
import com.example.hmiyado.sampo.domain.model.Location as SampoLocation

/**
 * Created by hmiyado on 2016/07/27.
 * 位置情報サービスの実装．
 * AndroidのLocationManagerから位置情報を取得して流す．
 */

// TODO object にする
class LocationSensorImpl(private val locationManager: LocationManager) : LocationSensor {
    companion object {
        private fun convertFromAndroidLocationToSampoLocation(androidLocation: android.location.Location): com.example.hmiyado.sampo.domain.model.Location {
            return com.example.hmiyado.sampo.domain.model.Location(
                    androidLocation.latitude,
                    androidLocation.longitude,
                    Instant.ofEpochMilli(androidLocation.time)
            )
        }
    }

    private val locationSubject: PublishSubject<com.example.hmiyado.sampo.domain.model.Location> = PublishSubject.create()
    private val locationListener: LocationListener

    init {
        locationListener = createLocationListener()
    }

    override fun getLocationObservable(): Observable<com.example.hmiyado.sampo.domain.model.Location> {
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
                    LocationProvider.AVAILABLE               -> Timber.v("Status AVAILABLE")
                    LocationProvider.OUT_OF_SERVICE          -> Timber.v("Status OUT_OF_SERVICE")
                    LocationProvider.TEMPORARILY_UNAVAILABLE -> Timber.v("Status TEMPORARILY_UNAVAILABLE")
                }
                Timber.d("onStatusChanged")
            }

            override fun onProviderEnabled(p0: String?) {
                Timber.d("onProviderEnabled")
            }

            override fun onProviderDisabled(p0: String?) {
                Timber.d("onProviderDisabled")
            }

            override fun onLocationChanged(location: android.location.Location) {
                val sampoLocation = convertFromAndroidLocationToSampoLocation(location)
                locationSubject.onNext(sampoLocation)
            }
        }
    }
}