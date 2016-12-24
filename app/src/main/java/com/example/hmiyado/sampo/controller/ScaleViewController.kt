package com.example.hmiyado.sampo.controller

import com.example.hmiyado.sampo.view.custom.ScaleView
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable

/**
 * Created by hmiyado on 2016/12/24.
 */
class ScaleViewController(scaleView: ScaleView) : ViewController<ScaleView>(scaleView) {

    fun <T> bindToViewLifecycle(observable: Observable<T>): Observable<T> {
        return observable.bindToLifecycle(view)
    }
}