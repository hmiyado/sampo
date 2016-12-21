package com.example.hmiyado.sampo.controller

import com.example.hmiyado.sampo.view.custom.CompassView
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable

/**
 * Created by hmiyado on 2016/12/21.
 *
 *  CompassView のController
 */
class CompassViewController(
        private val compassView: CompassView
) {

    fun invalidate() = compassView.postInvalidate()

    fun <T> bindToCompassView(observable: Observable<T>) = observable.bindToLifecycle(compassView)
}