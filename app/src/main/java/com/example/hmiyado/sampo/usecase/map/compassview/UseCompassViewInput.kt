package com.example.hmiyado.sampo.usecase.map.compassview

import android.graphics.Canvas
import rx.Observable

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 方位磁針からの入力を扱う
 */
interface UseCompassViewInput {
    fun getOnDrawSignal(): Observable<Canvas>
}