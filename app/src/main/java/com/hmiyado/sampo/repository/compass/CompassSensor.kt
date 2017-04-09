package com.hmiyado.sampo.repository.compass

import com.hmiyado.sampo.domain.model.Orientation
import io.reactivex.Observable

/**
 * Created by hmiyado on 2016/11/30.
 * 方位を取得するサービス
 */
interface CompassSensor {

    /**
     * @return 方位が変更されるたびに，この端末の向きを取得する
     */
    fun getCompassService(): Observable<Orientation>

    /**
     * 方位の取得を開始する
     */
    fun startCompassService(): Unit

    /**
     * 方位の取得を停止する
     */
    fun stopCompassService(): Unit
}