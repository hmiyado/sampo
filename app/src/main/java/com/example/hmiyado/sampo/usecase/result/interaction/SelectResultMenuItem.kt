package com.example.hmiyado.sampo.usecase.result.interaction

import com.example.hmiyado.sampo.domain.result.ResultMenuItem
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.common.UseListView
import com.example.hmiyado.sampo.usecase.result.UseMenuRequester
import timber.log.Timber

/**
 * Created by hmiyado on 2017/02/16.
 */
class SelectResultMenuItem(
        useListView: UseListView.Source<ResultMenuItem>,
        useMenuRequester: UseMenuRequester
) : Interaction() {
    init {
        subscriptions += useListView.getSelectedItem()
                .subscribe {
                    Timber.d(it.toString())
                    useMenuRequester.request(it)
                }
    }
}