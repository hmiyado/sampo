package com.example.hmiyado.sampo.usecase

import rx.subscriptions.CompositeSubscription

/**
 * Created by hmiyado on 2017/01/05.
 *
 * Input から Output への Interaction ．
 *
 */
abstract class Interaction {
    /**
     * Input から Output へのデータの流れの集合
     */
    val subscriptions: CompositeSubscription = CompositeSubscription()
}