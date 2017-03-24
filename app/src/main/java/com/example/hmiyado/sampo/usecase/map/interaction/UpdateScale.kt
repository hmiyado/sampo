package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseMapView
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2017/03/22.
 */
class UpdateScale(
        private val useMapViewSource: UseMapView.Source,
        private val store: MapStore
) : Interaction<Float>() {
    override fun buildProducer(): Observable<Float> = useMapViewSource.getOnScaleSignal().throttleLast(100, TimeUnit.MILLISECONDS)

    override fun buildConsumer(): Observer<Float> {
        return DefaultObserver(store::setScaleFactor)
    }
}