package com.example.hmiyado.sampo.controller

import android.view.View

/**
 * Created by hmiyado on 2016/12/24.
 *
 * View の Controller
 */
abstract class ViewController<out T : View>(protected val view: T)