package com.hmiyado.sampo.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.appKodein
import com.hmiyado.sampo.R
import com.hmiyado.sampo.domain.setting.SettingMenu
import com.hmiyado.sampo.presenter.common.ListViewPresenter
import com.hmiyado.sampo.presenter.result.SettingMenuFragmentPresenter
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.common.UseListView
import com.hmiyado.sampo.usecase.result.UseMenuRequester
import com.hmiyado.sampo.usecase.setting.interaction.settingMenuUseCaseModule
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
class SettingMenuFragment : RxFragment(), LazyKodeinAware, SettingActivity.SettingMenuRequester {
    companion object {
        fun newInstance() = SettingMenuFragment()
    }

    override val kodein: LazyKodein = LazyKodein {
        Kodein.Companion {
            extend(appKodein())
            import(settingMenuUseCaseModule)
            bind<UseMenuRequester<SettingMenu>>() with provider { instance<SettingMenuFragmentPresenter>() }
            bind<UseListView.Source<SettingMenu>>() with provider { instance<ListViewPresenter<SettingMenu>>() }
            bind<ListViewPresenter<SettingMenu>>() with singleton { ListViewPresenter<SettingMenu>(find<ListView>(ui.listViewId)) }
            bind<SettingMenuFragmentPresenter>() with singleton { SettingMenuFragmentPresenter() }
            bind<ArrayAdapter<SettingMenu>>() with singleton { ArrayAdapter<SettingMenu>(activity.baseContext, R.layout.result_option_item_layout) }
        }
    }


    val presenter: SettingMenuFragmentPresenter by kodein.instance()
    private val interactions: List<Interaction<*>> by kodein.instance()
    override val fragmentRequest: Observable<SettingMenu>
        get() = presenter.fragmentRequest

    val listViewPresenter: ListViewPresenter<SettingMenu> by kodein.instance()
    val itemListAdapter: ArrayAdapter<SettingMenu> by kodein.instance()
    val ui = ListFragmentUi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemListAdapter.addAll(SettingMenu.values().toList())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.d("on create view")
        return ui.createView(AnkoContext.create(activity.baseContext, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view ?: return
        find<ListView>(ui.listViewId).let {
            it.adapter = itemListAdapter
            listViewPresenter.set(it)
        }

    }

    override fun onStart() {
        Timber.d("onStart")
        super.onStart()
        Interaction.Builder(this, FragmentEvent.STOP).buildAll(interactions)
    }
}