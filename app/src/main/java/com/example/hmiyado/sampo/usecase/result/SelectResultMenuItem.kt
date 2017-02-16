package com.example.hmiyado.sampo.usecase.result

import com.example.hmiyado.sampo.domain.result.ResultMenuItem
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.presenter.common.ListViewPresenter
import com.example.hmiyado.sampo.presenter.result.ResultMenuFragmentPresenter
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.view.result.ResultFragmentType
import timber.log.Timber

/**
 * Created by hmiyado on 2017/02/16.
 */
class SelectResultMenuItem(
        listViewPresenter: ListViewPresenter,
        resultMenuFragmentPresenter: ResultMenuFragmentPresenter
) : Interaction() {
    init {
        subscriptions += listViewPresenter.getItemSelectedObservable()
                .subscribe {
                    Timber.d(it.toString())
                    when (it) {
                        ResultMenuItem.Realm -> {
                            resultMenuFragmentPresenter.request(ResultFragmentType.Realm)
                        }
                        ResultMenuItem.Share -> {
                        }
                        else                 -> {
                        }
                    }
                }
    }
}