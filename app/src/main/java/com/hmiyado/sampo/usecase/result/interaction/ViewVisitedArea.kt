package com.hmiyado.sampo.usecase.result.interaction

import com.hmiyado.sampo.domain.model.Area
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.store.MapStore
import com.hmiyado.sampo.usecase.result.UseVisitedAreaViewer
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2017/04/23.
 */
class ViewVisitedArea(
        private val mapStore: MapStore,
        private val useVisitedAreaViewer: UseVisitedAreaViewer.Sink
) : Interaction<Set<Area>>() {
    override fun buildProducer(): Observable<Set<Area>> {
        return mapStore.getTerritories()
                .map {
                    it.map { it.area }.toSet()
                }
    }

    override fun buildConsumer(): Observer<Set<Area>> {
        return DefaultObserver({
            useVisitedAreaViewer.view(it)
        })
    }
}