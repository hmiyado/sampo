package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.math.Radian
import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseMapView
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import rx.Observable
import rx.Observer

/**
 * Created by hmiyado on 2017/03/22.
 */
class UpdateRotateAngle(
        private val useMapViewSource: UseMapView.Source,
        private val store: MapStore

) : Interaction<Degree>() {
    override fun buildProducer(): Observable<Degree> {
        return useMapViewSource.getOnRotateSignal().map(Radian::toDegree)
    }

    override fun buildConsumer(): Observer<Degree> {
        return DefaultObserver(store::setRotateAngle)
    }
}