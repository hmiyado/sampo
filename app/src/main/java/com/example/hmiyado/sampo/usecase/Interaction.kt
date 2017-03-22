package com.example.hmiyado.sampo.usecase

import com.trello.rxlifecycle.LifecycleProvider
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.schedulers.Schedulers

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
        var observeThread = Schedulers.newThread()
        var subscribeThread = Schedulers.newThread()

        fun <T> build(interaction: Interaction<T>): Subscription {
            return interaction.buildProducer()
                    .subscribeOn(subscribeThread)
                    .observeOn(observeThread)
                    .compose(lifecycleProvider.bindUntilEvent(event))
                    .subscribe(interaction.buildConsumer())
        }
    }
}