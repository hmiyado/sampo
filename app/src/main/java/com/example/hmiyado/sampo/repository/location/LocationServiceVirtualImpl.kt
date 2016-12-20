package com.example.hmiyado.sampo.repository.location

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Time.LocalDateTime
import com.example.hmiyado.sampo.domain.model.Time.Second
import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2016/08/06.
 * 仮想の位置情報を取得するサービス
 */
class LocationServiceVirtualImpl : LocationService {
    private val locationSubject: PublishSubject<Location>
    private var nextLocation: Location
    private val delayTimeMs: Long = 5000
    private var nextLocationSubscription: Subscription? = null

    init {
        locationSubject = PublishSubject.create()
        nextLocation = Location(0.0, 0.0, LocalDateTime.Companion.UnixEpoch)
    }


    override fun getLocationObservable(): Observable<Location> {
        return locationSubject.asObservable().share()
    }

    override fun startLocationObserve() {
        nextLocationSubscription = Observable
                .interval(delayTimeMs, TimeUnit.MILLISECONDS)
                .doOnNext { onNextLocation(it) }
                .subscribe()
    }

    override fun stopLocationObserve() {
        nextLocationSubscription?.unsubscribe()
    }

    private fun updateNextLocation(num: Long) {
        nextLocation = Location(
                nextLocation.latitude + 1,
                nextLocation.longitude + 1,
                nextLocation.localDateTime + Second(num.toInt())
        )
    }

    private fun onNextLocation(num: Long) {
        locationSubject.onNext(nextLocation)
        updateNextLocation(num)
    }
}