package com.example.hmiyado.sampo.repository.location

import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import com.example.hmiyado.sampo.domain.model.Time.Second
import rx.Observable
import rx.subjects.PublishSubject
import timber.log.Timber
import android.location.Location as AndroidLocation
import com.example.hmiyado.sampo.domain.model.Location as SampoLocation
import com.example.hmiyado.sampo.domain.model.Time.LocalDateTime as SampoLocalDateTime

/**
 * Created by hmiyado on 2016/07/27.
 * 位置情報サービスの実装．
 * AndroidのLocationManagerから位置情報を取得して流す．
 */

// TODO object にする
class LocationServiceImpl(private val locationManager: LocationManager) : LocationService {
    companion object {
        private fun convertFromAndroidLocationToSampoLocation(androidLocation: android.location.Location): com.example.hmiyado.sampo.domain.model.Location {
            return com.example.hmiyado.sampo.domain.model.Location(
                    androidLocation.latitude,
                    androidLocation.longitude,
                    com.example.hmiyado.sampo.domain.model.Time.LocalDateTime.Companion.Factory
                            // Location.getTime() は ms を返すので，1000で割って秒に変換する
                            .initByUnixTime(Second((androidLocation.time / 1000)))
                            .complete()
            )
        }
    }

    private val locationSubject: PublishSubject<com.example.hmiyado.sampo.domain.model.Location>
    private val locationListener: LocationListener

    init {
        locationSubject = PublishSubject.create()
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
                    LocationProvider.AVAILABLE               -> Log.v("Status", "AVAILABLE")
                    LocationProvider.OUT_OF_SERVICE          -> Log.v("Status", "OUT_OF_SERVICE")
                    LocationProvider.TEMPORARILY_UNAVAILABLE -> Log.v("Status", "TEMPORARILY_UNAVAILABLE")
                }
                Log.d("locationlistener", "onsStatusChanged")
            }

            override fun onProviderEnabled(p0: String?) {
                Log.d("locationlistener", "onsProviderEnabled")
            }

            override fun onProviderDisabled(p0: String?) {
                Log.d("locationlistener", "onsProviderDisabled")
            }

            override fun onLocationChanged(location: android.location.Location) {
                val sampoLocation = convertFromAndroidLocationToSampoLocation(location)
                Timber.d(sampoLocation.toString())
                locationSubject.onNext(sampoLocation)
            }
        }
    }
}