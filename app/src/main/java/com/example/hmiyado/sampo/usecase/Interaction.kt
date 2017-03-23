package com.example.hmiyado.sampo.usecase

import com.example.hmiyado.sampo.libs.plusAssign
import com.trello.rxlifecycle.LifecycleProvider
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created by hmiyado on 2017/03/22.
 */
abstract class Interaction<T> {
    protected abstract fun buildProducer(): Observable<T>
    protected abstract fun buildConsumer(): Observer<T>

    class Builder<T>(
            var lifecycleProvider: LifecycleProvider<T>,
            var event: T
    ) {
        var observeThread = AndroidSchedulers.mainThread()
        var subscribeThread = Schedulers.newThread()

        fun <T> build(interaction: Interaction<T>): Subscription {
            return interaction.buildProducer()
                    .subscribeOn(subscribeThread)
                    .observeOn(observeThread)
                    .compose(lifecycleProvider.bindUntilEvent(event))
                    .subscribe(interaction.buildConsumer())
        }

        fun buildAll(interactions: List<Interaction<*>>): CompositeSubscription {
            return interactions.fold(CompositeSubscription(), { subscriptions, interaction ->
                subscriptions += build(interaction)
                subscriptions
            })
        }
    }
}