package com.example.hmiyado.sampo.presenter.result

import com.example.hmiyado.sampo.domain.math.Measurement
import com.example.hmiyado.sampo.repository.location.LocationRepository
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.result.UseTotalDistanceViewer
import com.example.hmiyado.sampo.usecase.result.interaction.CalculateTotalDistance
import com.example.hmiyado.sampo.view.result.ResultSummaryFragment
import com.github.salomonbrys.kodein.instance
import com.trello.rxlifecycle.android.FragmentEvent
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
        val builder = Interaction.Builder(fragment, FragmentEvent.STOP)
        listOf(
                CalculateTotalDistance(locationRepository, measurement, useTotalDistanceViewer)
        ).forEach {
            builder.build(it)
        }
    }

    fun onStop() {
        subscriptions.unsubscribe()
    }
}