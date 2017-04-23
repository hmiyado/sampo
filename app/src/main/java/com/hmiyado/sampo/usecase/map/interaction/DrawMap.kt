package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.DrawableMap
import com.hmiyado.sampo.domain.model.SampoScorer
import com.hmiyado.sampo.domain.model.Territory
import com.hmiyado.sampo.domain.model.ValidityPeriod
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.UseMapView
import com.hmiyado.sampo.usecase.map.UseMapView.Sink.DrawableTerritories
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Function3
import org.threeten.bp.Instant
import org.threeten.bp.Period

/**
 * Created by hmiyado on 2016/12/15.
 * ストアと地図ビューの入力から，地図ビューの出力する
 */
class DrawMap(
        private val store: MapStore,
        val scorer: SampoScorer,
        private val useMapViewSink: UseMapView.Sink
) : Interaction<DrawableTerritories>() {
    override fun buildProducer(): Observable<DrawableTerritories> {
        return Observable
                .combineLatest(
                        store.drawableMapSignal,
                        store.getTerritories(),
                        store.updatedValidityPeriodEndSignal,
                        // http://stackoverflow.com/questions/42725749/observable-combinelatest-type-inference-in-kotlin
                        Function3(this::toDrawableTarget))
    }

    private fun toDrawableTarget(drawableMap: DrawableMap, territories: List<Territory>, validityPeriodEnd: Instant): DrawableTerritories {
        val validityPeriod = ValidityPeriod.create(Period.ofDays(1), validityPeriodEnd)
        val coordinates = territories.map {
            val coordinate = drawableMap.determineVectorFromOrigin(it.area.center)
            val score = scorer.run {
                it.calcScore(validityPeriod)
            }
            val locationCoordinates = it.locations.map {
                drawableMap.determineVectorFromOrigin(it)
            }
            Triple(coordinate, score, locationCoordinates)
        }

        return DrawableTerritories(drawableMap, coordinates, scorer.maxTerritoryScore)
    }

    override fun buildConsumer(): Observer<DrawableTerritories> {
        return DefaultObserver(useMapViewSink::draw)
    }
}