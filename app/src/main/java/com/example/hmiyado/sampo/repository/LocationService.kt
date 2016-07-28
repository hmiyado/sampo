package com.example.hmiyado.sampo.repository

import com.example.hmiyado.sampo.domain.model.Location
import rx.Observable

/**
 * Created by hmiyado on 2016/07/27.
 */
interface LocationService {
    fun getLocationObservable(): Observable<Location>
    fun startLocationObserve(): Unit
    fun stopLocationObserve(): Unit
}