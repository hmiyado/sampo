package com.example.hmiyado.sampo.usecase.map.scaleview

import android.graphics.Canvas
import rx.Observable

/**
 * Created by hmiyado on 2016/12/24.
 */
interface UseScaleViewInput {

    fun getOnDrawCanvasSignal(): Observable<Canvas>
}