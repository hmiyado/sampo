package com.example.hmiyado.sampo.presenter.result

import com.example.hmiyado.sampo.presenter.FragmentPresenter
import com.example.hmiyado.sampo.repository.location.LocationRepository
import com.example.hmiyado.sampo.view.result.repository.location.ResultLocationRepositoryFragment
import com.github.salomonbrys.kodein.instance

/**
 * Created by hmiyado on 2017/02/19.
 */
class ResultRepositoryFragmentPresenter(
        val resultLocationRepositoryFragment: ResultLocationRepositoryFragment
) : FragmentPresenter {
    private val locationRepository by resultLocationRepositoryFragment.injector.instance<LocationRepository>()

    fun onStart() {
        refreshLocationList()
    }

    private fun refreshLocationList() {
        resultLocationRepositoryFragment.listViewAdapter.locations.clear()
        resultLocationRepositoryFragment.listViewAdapter.locations.addAll(locationRepository.loadLocationList().sortedBy { it.timeStamp })
        resultLocationRepositoryFragment.listViewAdapter.notifyDataSetChanged()
    }

    fun onStop() {
    }
}