package com.example.hmiyado.sampo.usecase.map

import com.example.hmiyado.sampo.domain.math.Radian
import com.example.hmiyado.sampo.domain.model.*
import io.reactivex.Observable

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
        data class DrawableFootmarks(
                val drawableMap: DrawableMap,
                val footmarks: List<Location>,
                val territories: List<Territory>,
                val validityPeriod: ValidityPeriod,
                val scorer: SampoScorer
        )

        fun draw(drawableFootmarks: DrawableFootmarks)
    }
}