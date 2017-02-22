package com.example.hmiyado.sampo.presenter.result

import com.example.hmiyado.sampo.presenter.FragmentPresenter
import com.example.hmiyado.sampo.repository.location.LocationRepository
import com.example.hmiyado.sampo.view.result.ResultRepositoryFragment
import com.github.salomonbrys.kodein.instance
import rx.subscriptions.CompositeSubscription

/**
 * Created by hmiyado on 2017/02/19.
 */
class ResultRepositoryFragmentPresenter(
        val resultRepositoryFragment: ResultRepositoryFragment
) : FragmentPresenter {

    private var subscriptions = CompositeSubscription()

    private val locationRepository by resultRepositoryFragment.injector.instance<LocationRepository>()

    fun onStart() {
        refreshLocationList()

        if (subscriptions.isUnsubscribed) {
            subscriptions = CompositeSubscription()
        }

    }

    private fun refreshLocationList() {
        resultRepositoryFragment.listViewAdapter.locations.clear()
        resultRepositoryFragment.listViewAdapter.locations.addAll(locationRepository.loadLocationList().sortedBy { it.localDateTime })
        resultRepositoryFragment.listViewAdapter.notifyDataSetChanged()
    }

    fun onStop() {
        subscriptions.unsubscribe()
    }
}