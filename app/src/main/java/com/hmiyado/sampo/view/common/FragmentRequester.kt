package com.hmiyado.sampo.view.common

import io.reactivex.Observable

/**
 * Created by hmiyado on 2017/02/16.
 */
interface FragmentRequester<T> {
    fun getFragmentRequest(): Observable<T>
}