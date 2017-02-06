package com.example.hmiyado.sampo.view.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hmiyado.sampo.presenter.result.ResultFragmentPresenter
import com.example.hmiyado.sampo.view.result.ui.ResultFragmentUi
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.trello.rxlifecycle.components.RxFragment
import org.jetbrains.anko.AnkoContext

/**
 * Created by hmiyado on 2017/02/05.
 */
class ResultFragment : RxFragment(), KodeinInjected {
    override val injector = KodeinInjector()

    private val presenter: ResultFragmentPresenter = ResultFragmentPresenter(this)

    companion object {
        fun getInstance() = ResultFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appKodein())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        Timber.d("on create view")
        return ResultFragmentUi().createView(AnkoContext.create(activity.baseContext, this))
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }
}