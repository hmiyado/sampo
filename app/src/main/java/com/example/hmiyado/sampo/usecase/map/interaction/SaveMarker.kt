package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.domain.model.Marker
import com.example.hmiyado.sampo.repository.marker.MarkerRepository
import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.MarkerProducer
import io.reactivex.Observable
import io.reactivex.Observer
import timber.log.Timber

/**
 * Created by hmiyado on 2017/04/08.
 */
class SaveMarker(
        private val markerProducer: MarkerProducer,
        private val markerRepository: MarkerRepository
) : Interaction<Marker>() {
    override fun buildProducer(): Observable<Marker> {
        return markerProducer.product
    }

    override fun buildConsumer(): Observer<Marker> {
        return DefaultObserver({
            Timber.d(it.toString())
            markerRepository.saveMarker(it)
        })
    }

}