package com.example.hmiyado.sampo.usecase.map.mapview

import android.graphics.Canvas
import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.model.Location
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/10.
 *
 * 地図閲覧への出力を責務とする．
 *
 * 地図を閲覧するのに必要な情報
 * - 地図の見た目
 *  - 縮尺
 *  - 回転角
 * - 位置情報
 *  - 現在地
 *  - その他の地点情報
 */
interface UseMapViewOutput {
    fun setOnDrawSignal(
            originalLocationSignal: Observable<Location>,
            scaleSignal: Observable<Float>,
            rotateAngleSignal: Observable<Degree>,
            footmarksSignal: Observable<List<Location>>,
            onDrawSignal: Observable<Canvas>
    ): Subscription

    fun setOnUpdateMapSignal(
            originalLocationSignal: Observable<Location>,
            scaleSignal: Observable<Float>,
            rotateAngleSignal: Observable<Degree>
    ): Subscription
}

