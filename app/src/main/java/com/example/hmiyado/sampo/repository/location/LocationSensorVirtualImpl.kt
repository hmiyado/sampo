package com.example.hmiyado.sampo.repository.location

import com.example.hmiyado.sampo.domain.model.Location
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2016/08/06.
 * 仮想の位置情報を取得するサービス
 */
class LocationSensorVirtualImpl : LocationSensor {
    private val locationSubject: PublishSubject<Location>
    private var nextLocation: Location
    private val delayTimeMs: Long = 1000
    private var nextLocationSubscription: Disposable? = null

    init {
        locationSubject = PublishSubject.create()
        nextLocation = Location.empty()
    }


    override fun getLocationObservable(): Observable<Location> {
        return locationSubject.hide().share()
    }

    override fun startLocationObserve() {
        nextLocationSubscription = Observable
                .interval(delayTimeMs, TimeUnit.MILLISECONDS)
                .doOnNext { onNextLocation(it) }
                .subscribe()
    }

    override fun stopLocationObserve() {
        nextLocationSubscription?.dispose()
    }

    private fun updateNextLocation(num: Long) {
        val value = num * Math.PI / 16
        nextLocation = Location(
                0.01 * Math.cos(value),
                0.01 * Math.sin(value),
                nextLocation.timeStamp.plusMillis(delayTimeMs)
        )
    }

    private fun onNextLocation(num: Long) {
        locationSubject.onNext(nextLocation)
        updateNextLocation(num)
    }
}