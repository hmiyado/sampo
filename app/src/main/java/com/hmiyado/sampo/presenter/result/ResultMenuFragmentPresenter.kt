package com.hmiyado.sampo.presenter.result

import com.hmiyado.sampo.domain.result.ResultMenuItem
import com.hmiyado.sampo.usecase.result.UseMenuRequester
import com.hmiyado.sampo.view.result.ResultFragmentType
import com.hmiyado.sampo.view.result.ResultMenuFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by hmiyado on 2017/02/06.
 */
class ResultMenuFragmentPresenter(
        private val resultMenuFragment: ResultMenuFragment
) : UseMenuRequester<ResultMenuItem> {
    private val fragmentRequester = PublishSubject.create<ResultFragmentType>()

    override fun request(menuItem: ResultMenuItem) {
        when (menuItem) {
            ResultMenuItem.LOCATION_REPOSITORY -> {
                request(ResultFragmentType.LOCATION_REPOSITORY)
            }
            ResultMenuItem.MARKER_REPOSITORY   -> {
                request(ResultFragmentType.MARKER_REPOSITORY)
            }
            ResultMenuItem.Summary             -> {
                request(ResultFragmentType.Summary)
            }
            ResultMenuItem.Share               -> {
            }
            else                               -> {
            }
        }
    }

    fun request(resultFragmentType: ResultFragmentType) {
        fragmentRequester.onNext(resultFragmentType)
    }

    fun getFragmentRequest(): Observable<ResultFragmentType> = fragmentRequester.hide().share()
}