package com.example.hmiyado.sampo.view.result.repository.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.hmiyado.sampo.presenter.result.ResultRepositoryFragmentPresenter
import com.example.hmiyado.sampo.view.result.ui.ListFragmentUi
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import timber.log.Timber

/**
 * Created by hmiyado on 2017/02/16.
 */
class ResultLocationRepositoryFragment : RxFragment(), KodeinInjected {
    companion object {
        fun newInstance(): ResultLocationRepositoryFragment {
            return ResultLocationRepositoryFragment()
        }
    }

    override val injector: KodeinInjector = KodeinInjector()

    val presenter: ResultRepositoryFragmentPresenter by lazy { ResultRepositoryFragmentPresenter(this) }

    val ui = ListFragmentUi()
    val listViewAdapter: ResultRepositoryItemListAdapter by lazy { ResultRepositoryItemListAdapter(activity.baseContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appKodein())
    }

    override fun onStart() {
        Timber.d("onStart")
        super.onStart()
        presenter.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ui.createView(AnkoContext.create(activity.baseContext, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        find<ListView>(ui.listViewId).adapter = listViewAdapter
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}