package com.example.hmiyado.sampo.view.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hmiyado.sampo.controller.result.TotalDistanceViewController
import com.example.hmiyado.sampo.presenter.result.ResultSummaryFragmentPresenter
import com.example.hmiyado.sampo.view.result.ui.ResultSummaryFragmentUi
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.trello.rxlifecycle.components.RxFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Created by hmiyado on 2017/03/01.
 */
class ResultSummaryFragment : RxFragment(), KodeinInjected {
    companion object {
        fun getInstance() = ResultSummaryFragment()
    }

    override val injector: KodeinInjector = KodeinInjector()

    private val presenter = ResultSummaryFragmentPresenter(this)

    val totalDistanceViewController by lazy { TotalDistanceViewController(find<TextView>(ResultSummaryFragmentUi.totalDistanceTextViewId)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appKodein())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ResultSummaryFragmentUi().createView(AnkoContext.Companion.create(activity.baseContext, this))
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }
}