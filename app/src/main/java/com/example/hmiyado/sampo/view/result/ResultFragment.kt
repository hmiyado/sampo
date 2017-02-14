package com.example.hmiyado.sampo.view.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.hmiyado.sampo.controller.common.ListViewController
import com.example.hmiyado.sampo.presenter.common.ListViewPresenter
import com.example.hmiyado.sampo.presenter.result.ResultFragmentPresenter
import com.example.hmiyado.sampo.view.result.ui.ResultFragmentUi
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.trello.rxlifecycle.components.RxFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Created by hmiyado on 2017/02/05.
 */
class ResultFragment : RxFragment(), KodeinInjected {
    companion object {
        fun getInstance() = ResultFragment()
    }

    override val injector = KodeinInjector()

    val presenter: ResultFragmentPresenter = ResultFragmentPresenter(this)

    val listViewPresenter: ListViewPresenter by lazy { ListViewPresenter(find<ListView>(ResultFragmentUi.listViewId)) }
    val listViewController: ListViewController by lazy { ListViewController(find<ListView>(ResultFragmentUi.listViewId)) }
    val resultOptionItemListAdapter: ResultOptionItemListAdapter by lazy { ResultOptionItemListAdapter(activity.baseContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appKodein())

        resultOptionItemListAdapter.addAll(listOf(
                "Realm",
                "Share"
        ))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        Timber.d("on create view")
        return ResultFragmentUi().createView(AnkoContext.create(activity.baseContext, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view ?: return
        find<ListView>(ResultFragmentUi.listViewId).adapter = resultOptionItemListAdapter

    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }
}