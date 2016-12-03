package com.example.hmiyado.sampo.repository.location

import android.os.Handler
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Time.LocalDateTime
import com.example.hmiyado.sampo.domain.model.Time.Second
import rx.Observable
import rx.subjects.PublishSubject

/**
 * Created by hmiyado on 2016/08/06.
 * 仮想の位置情報を取得するサービス
 */
class LocationServiceVirtualImpl : LocationService {
    private val locationSubject: PublishSubject<Location>
    private val handler: Handler
    private var nextLocation: Location
    private val delayTimeMs:Long = 1000

    init {
        locationSubject = PublishSubject.create()
        nextLocation = Location(0.0, 0.0, LocalDateTime.Companion.UnixEpoch)
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