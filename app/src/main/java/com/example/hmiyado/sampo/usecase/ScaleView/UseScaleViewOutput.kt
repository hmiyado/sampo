package com.example.hmiyado.sampo.usecase.scaleview

import android.graphics.Canvas
import com.example.hmiyado.sampo.controller.ScaleViewController
import com.example.hmiyado.sampo.domain.model.Map
import rx.Observable

/**
 * Created by hmiyado on 2016/12/24.
 *
 * 縮尺のビューを描画する人
 */
class UseScaleViewOutput(private val scaleViewController: ScaleViewController) {
    fun setOnDrawSignal(mapSignal: Observable<Map>, drawSignal: Observable<Canvas>) {
        drawSignal
                .withLatestFrom(mapSignal, { canvas, map -> Pair(canvas, map) })
                .doOnNext {

                }
                .bindToViewLifecycle()
                .subscribe()
    }

    fun <T> rx.Observable<T>.bindToViewLifecycle(): Observable<T> {
        return scaleViewController.bindToViewLifecycle(this)
    }
}