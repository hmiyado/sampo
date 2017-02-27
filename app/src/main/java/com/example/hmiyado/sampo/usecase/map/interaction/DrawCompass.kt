package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseCompassView
import com.example.hmiyado.sampo.usecase.map.store.MapStore

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 状態と方位磁針の入力から，方位磁針へ出力する
 */
class DrawCompass(
        private val store: MapStore,
        private val useCompassViewSink: UseCompassView.Sink
) : Interaction() {
    init {
        subscriptions += drawInteraction()
    }

    private fun drawInteraction() = store.getOrientation().subscribe { useCompassViewSink.draw(it) }
}