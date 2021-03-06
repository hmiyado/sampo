package com.hmiyado.sampo.controller

import android.view.View
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable

/**
 * Created by hmiyado on 2016/12/24.
 *
 * View の Controller
 */
abstract class ViewController<T : View>(protected var view: T) {
    fun set(view: T) {
        this.view = view
    }

    fun invalidate() = view.postInvalidate()
    fun <T> bindToViewLifecycle(observable: Observable<T>) = observable.bindToLifecycle(view)
}