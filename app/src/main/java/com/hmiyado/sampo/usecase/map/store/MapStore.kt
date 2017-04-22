package com.hmiyado.sampo.usecase.map.store

import com.hmiyado.sampo.domain.math.Degree
import com.hmiyado.sampo.domain.math.Measurement
import com.hmiyado.sampo.domain.model.*
import io.reactivex.Observable
import io.reactivex.functions.Function3

/**
 * Created by hmiyado on 2017/02/26.
 */
interface MapStore {
    companion object {
        /**
         * 倍率１のときの，1 dip あたりの地図上の距離（メートル）
         */
        val SCALE_UNIT = 1
        /**
         * 初期のマーカーの個数の上限
         */
        val INITIAL_MARKER_LIMIT = 3
    }

    val drawableMapSignal: Observable<DrawableMap>
        get() = Observable.combineLatest(
                getOriginalLocation(),
                getScaleFactor(),
                getRotateAngle(),
                Function3 { location, scaleFactor, rotateAngle ->
                    DrawableMap(location, scaleFactor, rotateAngle, measurement)
                })

    val updatedMarkersSignal: Observable<List<Marker>>

    val updatedMarkerLimitSignal: Observable<Int>

    val measurement: Measurement

    fun setOriginalLocation(originalLocation: Location)

    fun setOrientation(orientation: Orientation)

    fun setScaleFactor(scaleFactor: Float)

    fun setRotateAngle(rotateAngle: Degree)

    fun setTerritories(territories: List<Territory>)

    fun setValidityPeriod(validityPeriod: ValidityPeriod)

    fun setMarkers(markers: List<Marker>)

    fun getOriginalLocation(): Observable<Location>

    fun getOrientation(): Observable<Orientation>

    fun getScaleFactor(): Observable<Float>

    fun getRotateAngle(): Observable<Degree>

    fun getTerritories(): Observable<List<Territory>>

    fun getValidityPeriod(): Observable<ValidityPeriod>
}