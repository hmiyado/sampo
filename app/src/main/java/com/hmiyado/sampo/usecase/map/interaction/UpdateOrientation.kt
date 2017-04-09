package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.Orientation
import com.hmiyado.sampo.repository.compass.CompassSensor
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 現在向いている方角から状態へのインタラクション
 */
class UpdateOrientation(
        private val compassSensor: CompassSensor,
        private val store: MapStore
) : Interaction<Orientation>() {
    override fun buildProducer(): Observable<Orientation> {
        return compassSensor.getCompassService()
    }

    override fun buildConsumer(): Observer<Orientation> {
        return DefaultObserver(store::setOrientation)
    }

}