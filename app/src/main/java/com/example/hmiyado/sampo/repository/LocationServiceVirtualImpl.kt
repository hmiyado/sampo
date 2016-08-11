package com.example.hmiyado.sampo.repository

import android.location.LocationListener
import android.os.Handler
import android.util.Log
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Time.LocalDateTime
import com.example.hmiyado.sampo.domain.model.Time.Second
import org.threeten.bp.LocalDate
import rx.Observable
import rx.subjects.PublishSubject
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.schedule

/**
 * Created by hmiyado on 2016/08/06.
 */
class LocationServiceVirtualImpl : LocationService{
    private val locationSubject: PublishSubject<Location>
    private var handler: Handler
    private var nextLocation: Location
    private val delayTimeMs:Long = 1000

    init {
        locationSubject = PublishSubject.create()
        nextLocation = Location(0.0, 0.0, LocalDateTime.UnixEpoch)
        handler = Handler()
    }


    override fun getLocationObservable(): Observable<Location> {
        return locationSubject.asObservable().share()
    }

    override fun startLocationObserve() {
        handler.postDelayed(object: Runnable{
            override fun run() {
                onNextLocation()
                handler.postDelayed(this, delayTimeMs)
            }
        },delayTimeMs)
    }

    override fun stopLocationObserve() {
        handler.removeCallbacksAndMessages(null)
    }

    private fun updateNextLocation() {
        nextLocation = Location(
                nextLocation.latitude + 1,
                nextLocation.longitude +1,
                nextLocation.localDateTime + Second((delayTimeMs/1000).toInt())
        )
    }

    private fun onNextLocation() {
        locationSubject.onNext(nextLocation)
        updateNextLocation()
    }
}