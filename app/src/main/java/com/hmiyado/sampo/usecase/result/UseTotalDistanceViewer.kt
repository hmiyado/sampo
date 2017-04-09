package com.hmiyado.sampo.usecase.result

/**
 * Created by hmiyado on 2017/03/02.
 */
interface UseTotalDistanceViewer {
    interface Sink {
        fun show(distance: Double): Unit
    }
}