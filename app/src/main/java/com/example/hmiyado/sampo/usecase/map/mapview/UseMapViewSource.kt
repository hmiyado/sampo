package com.example.hmiyado.sampo.usecase.map.mapview

import android.graphics.Canvas
import com.example.hmiyado.sampo.domain.math.Radian
import rx.Observable

/**
 * Created by hmiyado on 2016/12/10.
 * 地図閲覧からの入力を責務とする．
 *
 * 地図閲覧からの入力
 * - 回転角の変化
 * - 縮尺の変化
 */
interface UseMapViewSource {
    /**
     * @return 地図をどれだけ回転させたか(radian)のシグナル
     */
    fun getOnRotateSignal(): Observable<Radian>

    /**
     * @return 拡大率のシグナル
     */
    fun getOnScaleSignal(): Observable<Float>

    fun getOnDrawSignal(): Observable<Canvas>
}