package com.hmiyado.sampo.usecase.map

import com.hmiyado.sampo.domain.math.Radian
import com.hmiyado.sampo.domain.model.DrawableMap
import com.hmiyado.sampo.domain.model.Territory
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
        data class DrawableTerritories(
                val drawableMap: DrawableMap = DrawableMap(),
                /**
                 * territory と score のペア
                 */
                val territories: List<Pair<Territory, Double>> = emptyList(),
                val maxTerritoryScore: Double = 0.0
        )

        fun draw(drawableTerritories: DrawableTerritories)
    }
}