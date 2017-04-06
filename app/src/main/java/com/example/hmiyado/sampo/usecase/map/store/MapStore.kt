package com.example.hmiyado.sampo.usecase.map.store

import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.model.*
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
    }

    val drawableMapSignal: Observable<DrawableMap>
        get() = Observable.combineLatest(
                getOriginalLocation(),
                getScaleFactor(),
                getRotateAngle(), Function3(::DrawableMap))

    val updatedMarkersSignal: Observable<List<Marker>>

    fun setOriginalLocation(originalLocation: Location)

    fun setOrientation(orientation: Orientation)

    fun setScaleFactor(scaleFactor: Float)

    fun setRotateAngle(rotateAngle: Degree)

    fun setFootmarks(footmarks: List<Location>)

    fun setTerritories(territories: List<Territory>)

    fun setValidityPeriod(validityPeriod: ValidityPeriod)

    fun setMarkers(markers: List<Marker>)

    fun getOriginalLocation(): Observable<Location>

    fun getOrientation(): Observable<Orientation>

    fun getScaleFactor(): Observable<Float>

    fun getRotateAngle(): Observable<Degree>

    fun getFootmarks(): Observable<List<Location>>

    fun getTerritories(): Observable<List<Territory>>

    fun getValidityPeriod(): Observable<ValidityPeriod>
}