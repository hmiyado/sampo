package com.example.hmiyado.sampo.usecase.map.store

import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Orientation
import rx.Observable

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


    fun setOriginalLocation(originalLocation: Location)

    fun setOrientation(orientation: Orientation)

    fun setScaleFactor(scaleFactor: Float)

    fun setRotateAngle(rotateAngle: Degree)

    fun getOriginalLocation(): Observable<Location>

    fun getOrientation(): Observable<Orientation>

    fun getScaleFactor(): Observable<Float>

    fun getRotateAngle(): Observable<Degree>

    fun getFootmarks(): Observable<List<Location>>
}