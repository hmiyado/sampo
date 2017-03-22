package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.domain.model.Orientation
import com.example.hmiyado.sampo.usecase.DefaultObserver
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseCompassView
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import rx.Observable
import rx.Observer

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 状態と方位磁針の入力から，方位磁針へ出力する
 */
class DrawCompass(
        private val store: MapStore,
        private val useCompassViewSink: UseCompassView.Sink
) : Interaction<Orientation>() {
    override fun buildProducer(): Observable<Orientation> {
        return store.getOrientation()
    }

    override fun buildConsumer(): Observer<Orientation> {
        return DefaultObserver(useCompassViewSink::draw)
    }

}