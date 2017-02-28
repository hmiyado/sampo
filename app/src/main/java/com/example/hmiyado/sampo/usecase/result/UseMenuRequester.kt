package com.example.hmiyado.sampo.usecase.result

import com.example.hmiyado.sampo.domain.result.ResultMenuItem

/**
 * Created by hmiyado on 2017/02/28.
 */
interface UseMenuRequester {
    fun request(resultMenuItem: ResultMenuItem): Unit
}