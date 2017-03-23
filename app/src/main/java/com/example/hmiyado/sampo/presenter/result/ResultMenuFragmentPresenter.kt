package com.example.hmiyado.sampo.presenter.result

import com.example.hmiyado.sampo.domain.result.ResultMenuItem
import com.example.hmiyado.sampo.usecase.result.UseMenuRequester
import com.example.hmiyado.sampo.view.result.ResultFragmentType
import com.example.hmiyado.sampo.view.result.ResultMenuFragment
import rx.Observable
import rx.lang.kotlin.PublishSubject

/**
 * Created by hmiyado on 2017/02/06.
 */
class ResultMenuFragmentPresenter(
        private val resultMenuFragment: ResultMenuFragment
) : UseMenuRequester {
    private val fragmentRequester = PublishSubject<ResultFragmentType>()

    override fun request(resultMenuItem: ResultMenuItem) {
        when (resultMenuItem) {
            ResultMenuItem.Realm   -> {
                request(ResultFragmentType.Realm)
            }
            ResultMenuItem.Summary -> {
                request(ResultFragmentType.Summary)
            }
            ResultMenuItem.Share   -> {
            }
            else                   -> {
            }
        }
    }

    fun request(resultFragmentType: ResultFragmentType) {
        fragmentRequester.onNext(resultFragmentType)
    }

    fun getFragmentRequest(): Observable<ResultFragmentType> = fragmentRequester.asObservable().share()
}