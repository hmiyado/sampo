package com.example.hmiyado.sampo.usecase.mapview.interaction

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.mapview.UseMapViewOutput
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/15.
 * ストアから地図出力へのインタラクション
 */
class StoreToMapViewOutputInteraction(
        private val store: Store,
        private val useMapViewOutput: UseMapViewOutput
) : Interaction() {

    init {
        subscriptions += mapInteraction()
    }

    private fun mapInteraction(): Subscription {
        return useMapViewOutput.setOnUpdateMapSignal(store.getMapSignal())
    }
}