package com.hmiyado.sampo.view.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.appKodein
import com.hmiyado.sampo.domain.result.ResultMenuItem
import com.hmiyado.sampo.presenter.common.ListViewPresenter
import com.hmiyado.sampo.presenter.result.ResultMenuFragmentPresenter
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.common.UseListView
import com.hmiyado.sampo.usecase.result.UseMenuRequester
import com.hmiyado.sampo.usecase.result.interaction.resultMenuUseCaseModule
import com.hmiyado.sampo.view.common.FragmentRequester
import com.hmiyado.sampo.view.result.ui.ListFragmentUi
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.RxFragment
import io.reactivex.Observable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
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
            bind<ListViewPresenter<ResultMenuItem>>() with singleton { ListViewPresenter<ResultMenuItem>(find<ListView>(ui.listViewId)) }
            bind<ResultMenuFragmentPresenter>() with singleton { ResultMenuFragmentPresenter(this@ResultMenuFragment) }
            bind<ResultOptionItemListAdapter>() with singleton { ResultOptionItemListAdapter(activity.baseContext) }
        }
    }


    val presenter: ResultMenuFragmentPresenter by kodein.instance()
    private val interactions: List<Interaction<*>> by kodein.instance()

    val listViewPresenter: ListViewPresenter<ResultMenuItem> by kodein.instance()
    val resultOptionItemListAdapter: ResultOptionItemListAdapter by kodein.instance()
    val ui = ListFragmentUi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultOptionItemListAdapter.addAll(ResultMenuItem.values().toList())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.d("on create view")
        return ui.createView(AnkoContext.create(activity.baseContext, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view ?: return
        find<ListView>(ui.listViewId).let {
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