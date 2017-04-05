package com.example.hmiyado.sampo.usecase.map

import io.reactivex.Observable

/**
 * Created by hmiyado on 2017/04/04.
 */
interface UseMarkerAdder {
    interface Source {
        val onClickedSignal: Observable<Unit>
    }
}