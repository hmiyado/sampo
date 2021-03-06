package com.hmiyado.sampo.usecase.result.interaction

import com.hmiyado.sampo.domain.math.Measurement
import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.repository.location.LocationRepository
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.result.UseTotalDistanceViewer
import io.reactivex.Observable
import io.reactivex.Observer
import timber.log.Timber

/**
 * Created by hmiyado on 2017/03/02.
 */
class CalculateTotalDistance(
        private val locationRepository: LocationRepository,
        private val measurement: Measurement,
        private val useTotalDistanceViewer: UseTotalDistanceViewer.Sink
) : Interaction<Double>() {
    override fun buildProducer(): Observable<Double> {
        return Observable.fromIterable(locationRepository.loadLocationList().sortedBy { it.timeStamp })
                // 距離を測るため，２個ずつペアにする
                .scan(Pair(Location.empty(), Location.empty())) { locationPair, location ->
                    Pair(locationPair.second, location)
                }
                // ２地点の距離を計算する
                .map {
                    if (it.first == Location.empty() || it.second == Location.empty()) {
                        0.0
                    } else {
                        val distance = measurement.determinePathwayDistance(it.first, it.second)
                        Timber.d(it.toString() + " = " + distance.toString())
                        distance
                    }
                }
                // すべての距離を足す
                .reduce { distance1, distance2 ->
                    distance1 + distance2
                }
                .toObservable()
    }

    override fun buildConsumer(): Observer<Double> {
        return DefaultObserver(useTotalDistanceViewer::show)
    }
}