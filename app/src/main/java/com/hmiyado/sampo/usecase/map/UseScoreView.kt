package com.hmiyado.sampo.usecase.map

/**
 * Created by hmiyado on 2017/04/08.
 */
interface UseScoreView {
    interface Sink {
        fun setScore(score: Double)
    }
}