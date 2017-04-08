package com.example.hmiyado.sampo.view.result.repository.marker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.hmiyado.sampo.repository.marker.MarkerRepository
import com.example.hmiyado.sampo.view.result.ui.ListFragmentUi
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Created by hmiyado on 2017/04/08.
 */
class ResultMarkerRepositoryFragment : RxFragment(), LazyKodeinAware {
    companion object {
        fun newInstance(): ResultMarkerRepositoryFragment {
            return ResultMarkerRepositoryFragment()
        }
    }

    override val kodein: LazyKodein = LazyKodein {
        appKodein()
    }

    val markerRepository: MarkerRepository by kodein.instance()

    val ui = ListFragmentUi()
    val listViewAdapter by lazy { ResultMarkerListAdapter(activity.baseContext) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ui.createView(AnkoContext.create(activity.baseContext, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        find<ListView>(ui.listViewId).adapter = listViewAdapter
    }

    override fun onStart() {
        super.onStart()
        listViewAdapter.markers.clear()
        listViewAdapter.markers.addAll(markerRepository.loadMarkers().sortedBy { it.validityPeriod.begin })
        listViewAdapter.notifyDataSetChanged()
    }
}