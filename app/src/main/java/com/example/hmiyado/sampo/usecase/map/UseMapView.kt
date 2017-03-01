package com.example.hmiyado.sampo.usecase.map

import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.math.Measurement
import com.example.hmiyado.sampo.domain.math.Radian
import com.example.hmiyado.sampo.domain.model.Location
import rx.Observable

/**
 * Created by hmiyado on 2017/02/28.
 */
interface UseMapView {
    interface Source {
        /**
         * @return 地図をどれだけ回転させたか(radian)のシグナル
         */
        fun getOnRotateSignal(): Observable<Radian>

        /**
         * @return 拡大率のシグナル
         */
        fun getOnScaleSignal(): Observable<Float>
    }

    interface Sink {
        data class DrawableMap(
                val originalLocation: Location,
                val scaleFactor: Float,
                val rotateAngle: Degree,
                val footmarks: List<Location>
        )

        fun draw(drawableMap: DrawableMap)

        fun setMeasurement(measurement: Measurement): Unit
    }
}