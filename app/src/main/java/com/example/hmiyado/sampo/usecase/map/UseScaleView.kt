package com.example.hmiyado.sampo.usecase.map

/**
 * Created by hmiyado on 2017/02/28.
 */
interface UseScaleView {
    interface Source

    interface Sink {
        fun draw(): Unit

        fun setScale(scale: Float): Unit
    }
}