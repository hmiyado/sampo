package com.hmiyado.sampo.usecase.result.interaction

import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.common.UseListView
import com.hmiyado.sampo.usecase.result.UseMenuRequester
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2017/02/16.
 */
class SelectResultMenuItem<T>(
        val useListView: UseListView.Source<T>,
        val useMenuRequester: UseMenuRequester<T>
) : Interaction<T>() {
    override fun buildProducer(): Observable<T> {
        return useListView.getSelectedItem()
    }

    override fun buildConsumer(): Observer<T> {
        return DefaultObserver(useMenuRequester::request)
    }
}