package com.hmiyado.sampo.usecase.result

/**
 * Created by hmiyado on 2017/02/28.
 */
interface UseMenuRequester<T> {
    fun request(menuItem: T): Unit
}