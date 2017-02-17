package com.example.hmiyado.sampo.view.result

import com.trello.rxlifecycle.components.RxFragment
import timber.log.Timber

/**
 * Created by hmiyado on 2017/02/16.
 */
class ResultRealmFragment : RxFragment() {
    companion object {
        fun newInstance(): ResultRealmFragment {
            return ResultRealmFragment()
        }
    }

    override fun onStart() {
        Timber.d("onStart")
        super.onStart()
    }
}