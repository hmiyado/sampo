package com.hmiyado.sampo.view.common

import io.reactivex.Observable

/**
 * Created by hmiyado on 2017/02/16.
 */
interface FragmentRequester<T> {
    val fragmentRequest: Observable<T>
}