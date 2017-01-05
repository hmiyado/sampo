package com.example.hmiyado.sampo.libs

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by hmiyado on 2017/01/05.
 *
 * ライブラリの拡張関数をおく
 */

operator fun CompositeSubscription.plusAssign(subscription: Subscription) {
    this.add(subscription)
}