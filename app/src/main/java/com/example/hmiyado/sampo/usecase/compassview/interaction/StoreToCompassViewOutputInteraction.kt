package com.example.hmiyado.sampo.usecase.compassview.interaction

import com.example.hmiyado.sampo.domain.model.Map
import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.compassview.UseCompassViewOutput
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 状態の変化に応じて方位磁針に出力する
 */
class StoreToCompassViewOutputInteraction(
        private val store: Store,
        private val useCompassViewOutput: UseCompassViewOutput
) : Interaction() {
    init {
        subscriptions += orientationInteraction()
    }

    private fun orientationInteraction(): Subscription =
            useCompassViewOutput.setOnOrientationSignal(store.getMapSignal().map(Map::orientation))

}