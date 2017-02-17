package com.example.hmiyado.sampo.controller

import android.view.View
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable

/**
 * Created by hmiyado on 2016/12/24.
 *
 * View の Controller
 */
abstract class ViewController<T : View>(var view: T) {
    fun invalidate() = view.postInvalidate()
    fun <T> bindToViewLifecycle(observable: Observable<T>) = observable.bindToLifecycle(view)
}