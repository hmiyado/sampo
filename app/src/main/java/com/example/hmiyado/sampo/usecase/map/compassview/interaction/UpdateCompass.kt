package com.example.hmiyado.sampo.usecase.map.compassview.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.compassview.UseCompassViewOutput
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 状態の変化に応じて方位磁針に出力する
 */
class UpdateCompass(
        private val store: MapStore,
        private val useCompassViewOutput: UseCompassViewOutput
) : Interaction() {
    init {
        subscriptions += orientationInteraction()
    }

    private fun orientationInteraction(): Subscription =
            useCompassViewOutput.setOnOrientationSignal(store.getOrientation())

}