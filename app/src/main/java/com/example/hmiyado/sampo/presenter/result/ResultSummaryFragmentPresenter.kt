package com.example.hmiyado.sampo.presenter.result

import com.example.hmiyado.sampo.domain.math.Measurement
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.location.LocationRepository
import com.example.hmiyado.sampo.usecase.result.UseTotalDistanceViewer
import com.example.hmiyado.sampo.usecase.result.interaction.CalculateTotalDistance
import com.example.hmiyado.sampo.view.result.ResultSummaryFragment
import com.github.salomonbrys.kodein.instance
import rx.Observable
import rx.subscriptions.CompositeSubscription

/**
 * Created by hmiyado on 2017/03/01.
 */
class ResultSummaryFragmentPresenter(
        private val fragment: ResultSummaryFragment
) {
    private var subscriptions = CompositeSubscription()

    private val useTotalDistanceViewer: UseTotalDistanceViewer.Sink by lazy { fragment.totalDistanceViewController }

    private val locationRepository by fragment.injector.instance<LocationRepository>()
    private val measurement by fragment.injector.instance<Measurement>()

    fun onStart() {
        if (subscriptions.isUnsubscribed) {
            subscriptions = CompositeSubscription()
        }

        Observable.from(
                listOf(
                        CalculateTotalDistance(locationRepository, measurement, useTotalDistanceViewer)
                )
        ).forEach {
            subscriptions += it.subscriptions
        }
    }

    fun onStop() {
        subscriptions.unsubscribe()
    }
}