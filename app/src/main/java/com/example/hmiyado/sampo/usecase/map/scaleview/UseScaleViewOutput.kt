package com.example.hmiyado.sampo.usecase.map.scaleview

import android.graphics.Canvas
import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Orientation
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/24.
 *
 * 縮尺のビューを描画する人
 */
interface UseScaleViewOutput {
    fun setMapSignal(
            locationSignal: Observable<Location>,
            orientationSignal: Observable<Orientation>,
            rotateAngleSignal: Observable<Degree>,
            scaleSignal: Observable<Float>
    ): Subscription


    fun setOnDrawSignal(scaleSignal: Observable<Float>, drawSignal: Observable<Canvas>): Subscription
}