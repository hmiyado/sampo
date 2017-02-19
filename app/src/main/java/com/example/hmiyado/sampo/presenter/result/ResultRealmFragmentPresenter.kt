package com.example.hmiyado.sampo.presenter.result

import com.example.hmiyado.sampo.presenter.FragmentPresenter
import com.example.hmiyado.sampo.repository.location.LocationRepository
import com.example.hmiyado.sampo.view.result.ResultRealmFragment
import com.github.salomonbrys.kodein.instance
import rx.subscriptions.CompositeSubscription

/**
 * Created by hmiyado on 2017/02/19.
 */
class ResultRealmFragmentPresenter(
        val resultRealmFragment: ResultRealmFragment
) : FragmentPresenter {

    private var subscriptions = CompositeSubscription()

    private val locationRepository by resultRealmFragment.injector.instance<LocationRepository>()

    fun onStart() {
        refreshLocationList()

        if (subscriptions.isUnsubscribed) {
            subscriptions = CompositeSubscription()
        }

    }

    private fun refreshLocationList() {
        resultRealmFragment.listViewAdapter.locations.clear()
        resultRealmFragment.listViewAdapter.locations.addAll(locationRepository.loadLocationList())
        resultRealmFragment.listViewAdapter.notifyDataSetChanged()
    }

    fun onStop() {
        subscriptions.unsubscribe()
    }
}