package com.example.hmiyado.sampo.view.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.presenter.common.ListViewPresenter
import com.example.hmiyado.sampo.presenter.result.ResultRealmFragmentPresenter
import com.example.hmiyado.sampo.view.result.ui.ResultFragmentUi
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.trello.rxlifecycle.components.RxFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import timber.log.Timber

/**
 * Created by hmiyado on 2017/02/16.
 */
class ResultRealmFragment : RxFragment(), KodeinInjected {
    companion object {
        fun newInstance(): ResultRealmFragment {
            return ResultRealmFragment()
        }
    }

    override val injector: KodeinInjector = KodeinInjector()

    val presenter: ResultRealmFragmentPresenter by lazy { ResultRealmFragmentPresenter(this) }

    val listViewPresenter: ListViewPresenter<Location> by lazy { ListViewPresenter<Location>(find<ListView>(ResultFragmentUi.listViewId)) }
    val listViewAdapter: ResultRealmItemListAdapter by lazy { ResultRealmItemListAdapter(activity.baseContext) }

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
        return ResultFragmentUi().createView(AnkoContext.create(activity.baseContext, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        find<ListView>(ResultFragmentUi.listViewId).let {
            it.adapter = listViewAdapter
            listViewPresenter.set(it)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}