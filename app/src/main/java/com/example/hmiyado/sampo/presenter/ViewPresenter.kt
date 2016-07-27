package com.example.hmiyado.sampo.presenter

import android.view.View

/**
 * Created by hmiyado on 2016/07/27.
 */
interface ViewPresenter{
    fun attachView(v: View)
    fun detachView()
}