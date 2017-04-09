package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.math.Degree
import com.hmiyado.sampo.domain.math.Radian
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.UseMapView
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2017/03/22.
 */
class UpdateRotateAngle(
        private val useMapViewSource: UseMapView.Source,
        private val store: MapStore

) : Interaction<Degree>() {
    override fun buildProducer(): Observable<Degree> = useMapViewSource.getOnRotateSignal().map(Radian::toDegree)

    override fun buildConsumer(): Observer<Degree> {
        return DefaultObserver(store::setRotateAngle)
    }
}