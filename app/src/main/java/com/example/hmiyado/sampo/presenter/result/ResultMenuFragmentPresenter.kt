package com.example.hmiyado.sampo.presenter.result

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.result.SelectResultMenuItem
import com.example.hmiyado.sampo.view.result.ResultFragmentType
import com.example.hmiyado.sampo.view.result.ResultMenuFragment
import rx.Observable
import rx.lang.kotlin.PublishSubject
import rx.subscriptions.CompositeSubscription

/**
 * Created by hmiyado on 2017/02/06.
 */
class ResultMenuFragmentPresenter(
        private val resultMenuFragment: ResultMenuFragment
) {
    private val subscriptions = CompositeSubscription()
    private val fragmentRequester = PublishSubject<ResultFragmentType>()

    fun onStart() {
        Observable.from(
                listOf(
                        SelectResultMenuItem(resultMenuFragment.listViewPresenter, this).subscriptions
                )
        ).forEach {
            subscriptions += it
        }
    }

    fun onStop() {
        subscriptions.unsubscribe()
    }

    fun request(resultFragmentType: ResultFragmentType) {
        fragmentRequester.onNext(resultFragmentType)
    }

    fun getFragmentRequest(): Observable<ResultFragmentType> = fragmentRequester.asObservable().share()
}