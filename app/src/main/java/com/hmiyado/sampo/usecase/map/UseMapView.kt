package com.hmiyado.sampo.usecase.map

import com.hmiyado.sampo.domain.math.Radian
import com.hmiyado.sampo.domain.math.Vector2
import com.hmiyado.sampo.domain.model.DrawableMap
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
                 * territory の座標， score， territory に含まれる location の座標 の組
                 */
                val territoriesCoordinates: List<Triple<Vector2, Double, List<Vector2>>> = emptyList(),
                val maxTerritoryScore: Double = 0.0
        )

        fun draw(drawableTerritories: DrawableTerritories)
    }
}