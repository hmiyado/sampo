package com.example.hmiyado.sampo.usecase.common

import io.reactivex.Observable

/**
 * Created by hmiyado on 2017/02/28.
 */
interface UseListView {
    interface Source<T> {
        fun getSelectedItem(): Observable<T>
    }
}