package com.example.hmiyado.sampo.service

import android.content.BroadcastReceiver
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import com.example.hmiyado.sampo.repository.location.LocationServiceState
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.BroadcastReceiverInjector
import com.github.salomonbrys.kodein.instance
import rx.Observable
import rx.lang.kotlin.BehaviorSubject

/**
 * Created by hmiyado on 2017/02/25.
 */
class LocationSettingReceiver : BroadcastReceiver(), BroadcastReceiverInjector {
    override val injector: KodeinInjector = KodeinInjector()

    override var context: Context? = null

    private val contentResolver: ContentResolver by injector.instance<ContentResolver>()
    private val locationManager: LocationManager by injector.instance<LocationManager>()

    private val locationServiceStateSubject = BehaviorSubject<LocationServiceState>()

    override fun onReceive(context1: Context?, intent: Intent?) {
        this.context = context1
        context ?: return
        intent ?: return
        initializeInjector()
        if (!intent.action.matches(Regex(LocationManager.PROVIDERS_CHANGED_ACTION))) {
            return
        }

        locationServiceStateSubject.onNext(getLocationServiceState(contentResolver, locationManager))

        destroyInjector()
    }

    private fun getLocationServiceState(contentResolver: ContentResolver, locationManager: LocationManager): LocationServiceState {
        val mode = Settings.Secure.getInt(contentResolver, Settings.Secure.LOCATION_MODE)
        if (mode == Settings.Secure.LOCATION_MODE_OFF) {
            //            Timber.d("Location mode off")
            return LocationServiceState.OFF
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //            "High accuracy. Uses GPS, Wi-Fi, and mobile networks to determine location";
            return LocationServiceState.HIGH_ACCURACY
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //            "Device only. Uses GPS to determine location";
            return LocationServiceState.DEVICE_ONLY
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //            "Battery saving. Uses Wi-Fi and mobile networks to determine location";
            return LocationServiceState.BATTERY_SAVING
        } else {
            return LocationServiceState.ON
        }
    }

    fun setLocationServiceState(contentResolver: ContentResolver, locationManager: LocationManager) {
        locationServiceStateSubject.onNext(getLocationServiceState(contentResolver, locationManager))
    }

    fun onChangeLocationServiceState(): Observable<LocationServiceState> = locationServiceStateSubject.asObservable().distinctUntilChanged().share()
}