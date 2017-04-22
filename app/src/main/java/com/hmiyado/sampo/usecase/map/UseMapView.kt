package com.hmiyado.sampo.usecase.map

import com.hmiyado.sampo.domain.math.Radian
import com.hmiyado.sampo.domain.model.DrawableMap
import com.hmiyado.sampo.domain.model.SampoScorer
import com.hmiyado.sampo.domain.model.Territory
import com.hmiyado.sampo.domain.model.ValidityPeriod
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
                val drawableMap: DrawableMap,
                val territories: List<Territory>,
                val validityPeriod: ValidityPeriod,
                val scorer: SampoScorer
        )

        fun draw(drawableTerritories: DrawableTerritories)
    }
}