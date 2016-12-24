package com.example.hmiyado.sampo.presenter

import android.view.View

/**
 * Created by hmiyado on 2016/12/24.
 *
 */
abstract class ViewPresenter<out T : View>(protected val view: T)