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
import timber.log.Timber

/**
 * Created by hmiyado on 2017/02/25.
 */
class GpsLocationReceiver : BroadcastReceiver(), BroadcastReceiverInjector {
    override val injector: KodeinInjector = KodeinInjector()

    override var context: Context? = null

    private val contentResolver: ContentResolver by injector.instance<ContentResolver>()
    private val locationManager: LocationManager by injector.instance<LocationManager>()

    private val locationServiceStateSubject = BehaviorSubject(LocationServiceState.OFF)

    override fun onReceive(context1: Context?, intent: Intent?) {
        this.context = context1
        context ?: return
        intent ?: return
        initializeInjector()
        if (!intent.action.matches(Regex(LocationManager.PROVIDERS_CHANGED_ACTION))) {
            return
        }

        val mode = Settings.Secure.getInt(contentResolver, Settings.Secure.LOCATION_MODE)

        if (mode == Settings.Secure.LOCATION_MODE_OFF) {
            Timber.d("Location mode off")
            locationServiceStateSubject.onNext(LocationServiceState.OFF)
            return
        }

        val locationMode = if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationServiceStateSubject.onNext(LocationServiceState.HIGH_ACCURACY)
            "High accuracy. Uses GPS, Wi-Fi, and mobile networks to determine location";
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationServiceStateSubject.onNext(LocationServiceState.DEVICE_ONLY)
            "Device only. Uses GPS to determine location";
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationServiceStateSubject.onNext(LocationServiceState.BATTERY_SAVING)
            "Battery saving. Uses Wi-Fi and mobile networks to determine location";
        } else {
            "unknown"
        }

        Timber.d(locationMode)

        destroyInjector()
    }

    fun onChangeLocationServiceState(): Observable<LocationServiceState> = locationServiceStateSubject.asObservable().distinctUntilChanged().share()
}