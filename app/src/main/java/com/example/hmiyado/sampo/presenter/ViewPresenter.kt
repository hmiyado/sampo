package com.example.hmiyado.sampo.presenter

import android.view.View

/**
 * Created by hmiyado on 2016/12/24.
 *
 */
abstract class ViewPresenter<T : View>(protected var view: T) {
    fun set(view: T) {
        this.view = view
    }
}