package com.example.hmiyado.sampo.usecase.result.interaction

import com.example.hmiyado.sampo.domain.result.ResultMenuItem
import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.common.UseListView
import com.example.hmiyado.sampo.usecase.result.UseMenuRequester
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2017/02/16.
 */
class SelectResultMenuItem(
        val useListView: UseListView.Source<ResultMenuItem>,
        val useMenuRequester: UseMenuRequester
) : Interaction<ResultMenuItem>() {
    override fun buildProducer(): Observable<ResultMenuItem> {
        return useListView.getSelectedItem()
    }

    override fun buildConsumer(): Observer<ResultMenuItem> {
        return DefaultObserver(useMenuRequester::request)
    }
}