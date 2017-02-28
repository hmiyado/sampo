package com.example.hmiyado.sampo.usecase.map

/**
 * Created by hmiyado on 2017/03/01.
 */
interface UseLocationSensor {
    interface Sink {
        fun start(): Unit
        fun stop(): Unit
    }
}