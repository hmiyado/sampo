package com.hmiyado.sampo.repository.location

import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.threeten.bp.Instant
import timber.log.Timber
import android.location.Location as AndroidLocation
import com.hmiyado.sampo.domain.model.Location as SampoLocation

/**
 * Created by hmiyado on 2016/07/27.
 * 位置情報サービスの実装．
 * AndroidのLocationManagerから位置情報を取得して流す．
 */

// TODO object にする
class LocationSensorImpl(private val locationManager: LocationManager) : LocationSensor {
    companion object {
        private fun convertFromAndroidLocationToSampoLocation(androidLocation: android.location.Location): com.hmiyado.sampo.domain.model.Location {
            return com.hmiyado.sampo.domain.model.Location(
                    androidLocation.latitude,
                    androidLocation.longitude,
                    Instant.ofEpochMilli(androidLocation.time)
            )
        }
    }

    private val locationSubject: PublishSubject<com.hmiyado.sampo.domain.model.Location> = PublishSubject.create()
    private val locationListener: LocationListener

    init {
        locationListener = createLocationListener()
    }

    override fun getLocationObservable(): Observable<com.hmiyado.sampo.domain.model.Location> {
        return locationSubject.hide().share()
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
            val twoMinutes = 2 * 60 * 1000
            var currentLocation: AndroidLocation? = null

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
                if (currentLocation == null) {
                    currentLocation = location
                    return
                }
                if (isBetterLocation(location, currentLocation)) {
                    currentLocation = location
                    val sampoLocation = convertFromAndroidLocationToSampoLocation(location)
                    locationSubject.onNext(sampoLocation)
                }
            }

            // https://developer.android.com/guide/topics/location/strategies.html
            private fun isBetterLocation(androidLocation: AndroidLocation, currentAndroidLocation: AndroidLocation?): Boolean {
                currentAndroidLocation ?: return true

                val timeDelta = androidLocation.time - currentAndroidLocation.time
                val isSignificantOlder = timeDelta < twoMinutes
                val isNewer = timeDelta > 0

                if (isSignificantOlder) {
                    return false
                }

                val accuracyDelta = androidLocation.accuracy - currentAndroidLocation.accuracy
                val isLessAccurate = accuracyDelta > 0
                val isMoreAccurate = accuracyDelta < 0
                val isSignificantlyLessAccurate = accuracyDelta > 200

                val isFromSameProvider = androidLocation.provider == currentAndroidLocation.provider

                if (isMoreAccurate) {
                    return true
                } else if (isNewer && !isLessAccurate) {
                    return true
                } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
                    return true
                }
                return false
            }
        }
    }
}