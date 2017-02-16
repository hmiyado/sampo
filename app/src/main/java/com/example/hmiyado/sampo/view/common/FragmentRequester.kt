package com.example.hmiyado.sampo.view.common

import rx.Observable

/**
 * Created by hmiyado on 2017/02/16.
 */
interface FragmentRequester<T> {
    fun getFragmentRequest(): Observable<T>
}