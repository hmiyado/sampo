package com.example.hmiyado.sampo.repository

import com.example.hmiyado.sampo.domain.model.Location
import rx.Observable

/**
 * Created by hmiyado on 2016/07/27.
 * 状態をもたずに位置情報を扱う方法を提供する
 */
interface LocationService : LocationDistanceInterface {
    /**
     * 位置情報が流れてくるObservable
     */
    fun getLocationObservable(): Observable<Location>

    /**
     * Observe開始する
     */
    fun startLocationObserve(): Unit

    /**
     * Observeを中断する
     */
    fun stopLocationObserve(): Unit
}