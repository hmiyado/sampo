package com.example.hmiyado.sampo.usecase.map.compassview

import android.graphics.Canvas
import com.example.hmiyado.sampo.domain.model.Orientation
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 方位磁針への出力
 */
interface UseCompassViewOutput {
    fun setOnOrientationSignal(orientationSignal: Observable<Orientation>): Subscription

    fun setOnDrawSignal(orientationSignal: Observable<Orientation>, onDrawSignal: Observable<Canvas>): Subscription
}