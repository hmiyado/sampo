package com.example.hmiyado.sampo.presenter.result

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.result.SelectResultMenuItem
import com.example.hmiyado.sampo.view.result.ResultFragment
import rx.Observable
import rx.subscriptions.CompositeSubscription

/**
 * Created by hmiyado on 2017/02/06.
 */
class ResultFragmentPresenter(
        private val resultFragment: ResultFragment
) {
    private val subscriptions = CompositeSubscription()

    fun onStart() {
        Observable.from(
                listOf(
                        SelectResultMenuItem(resultFragment.listViewPresenter).subscriptions
                )
        ).forEach {
            subscriptions += it
        }
    }

    fun onStop() {
        subscriptions.unsubscribe()
    }
}