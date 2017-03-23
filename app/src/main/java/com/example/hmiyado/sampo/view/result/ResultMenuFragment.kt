package com.example.hmiyado.sampo.view.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.hmiyado.sampo.domain.result.ResultMenuItem
import com.example.hmiyado.sampo.presenter.common.ListViewPresenter
import com.example.hmiyado.sampo.presenter.result.ResultMenuFragmentPresenter
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.common.UseListView
import com.example.hmiyado.sampo.usecase.result.UseMenuRequester
import com.example.hmiyado.sampo.usecase.result.interaction.resultMenuUseCaseModule
import com.example.hmiyado.sampo.view.common.FragmentRequester
import com.example.hmiyado.sampo.view.result.ui.ResultFragmentUi
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.appKodein
import com.trello.rxlifecycle.android.FragmentEvent
import com.trello.rxlifecycle.components.RxFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import rx.Observable
import timber.log.Timber

/**
 * Created by hmiyado on 2017/02/05.
 */
class ResultMenuFragment : RxFragment(), FragmentRequester<ResultFragmentType>, LazyKodeinAware {
    companion object {
        fun getInstance() = ResultMenuFragment()
    }

    override val kodein: LazyKodein = LazyKodein {
        Kodein {
            extend(appKodein())
            import(resultMenuUseCaseModule)
            bind<UseMenuRequester>() with provider { instance<ResultMenuFragmentPresenter>() }
            bind<UseListView.Source<ResultMenuItem>>() with provider { instance<ListViewPresenter<ResultMenuItem>>() }
            bind<ListViewPresenter<ResultMenuItem>>() with singleton { ListViewPresenter<ResultMenuItem>(find<ListView>(ResultFragmentUi.listViewId)) }
            bind<ResultMenuFragmentPresenter>() with singleton { ResultMenuFragmentPresenter(this@ResultMenuFragment) }
            bind<ResultOptionItemListAdapter>() with singleton { ResultOptionItemListAdapter(activity.baseContext) }
        }
    }


    val presenter: ResultMenuFragmentPresenter by kodein.instance()
    private val interactions: List<Interaction<*>> by kodein.instance()

    val listViewPresenter: ListViewPresenter<ResultMenuItem> by kodein.instance()
    val resultOptionItemListAdapter: ResultOptionItemListAdapter by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultOptionItemListAdapter.addAll(ResultMenuItem.values().toList())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.d("on create view")
        return ResultFragmentUi().createView(AnkoContext.create(activity.baseContext, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view ?: return
        find<ListView>(ResultFragmentUi.listViewId).let {
            it.adapter = resultOptionItemListAdapter
            listViewPresenter.set(it)
        }

    }

    override fun onStart() {
        Timber.d("onStart")
        super.onStart()
        Interaction.Builder(this, FragmentEvent.STOP).buildAll(interactions)
    }

    override fun getFragmentRequest(): Observable<ResultFragmentType> = presenter.getFragmentRequest()
}