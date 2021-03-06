package com.hmiyado.sampo.usecase.map.interaction

import com.hmiyado.sampo.domain.model.Orientation
import com.hmiyado.sampo.usecase.DefaultObserver
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.UseCompassView
import com.hmiyado.sampo.usecase.map.store.MapStore
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 状態と方位磁針の入力から，方位磁針へ出力する
 */
class DrawCompass(
        private val store: MapStore,
        private val useCompassViewSink: UseCompassView.Sink
) : Interaction<Orientation>() {
    override fun buildProducer(): Observable<Orientation> = store.getOrientation()

    override fun buildConsumer(): Observer<Orientation> {
        return DefaultObserver(useCompassViewSink::draw)
    }

}